package org.scify.talkandplay.gui.grid.games;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.scify.talkandplay.gui.grid.BaseGridPanel;
import org.scify.talkandplay.gui.grid.GridFrame;
import org.scify.talkandplay.gui.grid.tiles.TileAction;
import org.scify.talkandplay.models.User;
import org.scify.talkandplay.models.games.GameImage;
import org.scify.talkandplay.models.games.GameType;
import org.scify.talkandplay.models.games.StimulusReactionGame;

public class StimulusReactionGamePanel extends BaseGridPanel {
    
    private StimulusReactionGame game;
    private int selected;
    private JPanel gamePanel, controlsPanel;
    
    public StimulusReactionGamePanel(User user, GridFrame parent) {
        super(user, parent);
        this.selected = 0;
        
        initComponents();
        initCustomComponents();
    }
    
    public StimulusReactionGamePanel(User user, GridFrame parent, StimulusReactionGame game) {
        super(user, parent);
        this.selected = 0;
        this.game = game;
        
        initComponents();
        initCustomComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initCustomComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        gamePanel = new JPanel();
        gamePanel.setBackground(Color.white);
        controlsPanel = new JPanel();
        controlsPanel.setBackground(Color.white);
        add(gamePanel);
        add(controlsPanel);
        
        gamePanel.setLayout(new FlowLayout());
        
        panelList = new ArrayList();
        
        if (game == null) {
            //select a random game
            Random randomGenerator = new Random();
            for (GameType gameType : user.getGameModule().getGameTypes()) {
                if ("stimulusReactionGame".equals(gameType.getType())) {
                    for (int j = 0; j < gameType.getGames().size(); j++) {
                        int i = randomGenerator.nextInt(gameType.getGames().size());
                        if (gameType.getGames().get(i).isEnabled()) {
                            game = (StimulusReactionGame) gameType.getGames().get(i);
                            break;
                        }
                    }
                }
            }
        }
        
        if (game == null) {
            //TODO fix
            gamePanel.add(new JLabel("No games"));
        } else {
            JPanel gameImage = createGameItem(game.getEnabledImages().get(0));
            gamePanel.add(gameImage);
            panelList.add(gameImage);
            
        }
        
        gamePanel.revalidate();
        gamePanel.repaint();
        parent.clearGrid();
        parent.addGrid(this);
        parent.revalidate();
        parent.repaint();
        
        selector.setList(panelList);
        selector.start();
    }
    
    private JPanel createGameItem(GameImage image) {
        
        final StimulusReactionGamePanel currentPanel = this;
        panelList.clear();
        
        JPanel panel = tileCreator.create("",
                image.getImage(),
                image.getSound(),
                new TileAction() {
                    @Override
                    public void act() {
                        selector.cancel();
                        selected++;
                        if (selected == game.getEnabledImages().size() - 1) {
                            congratulate(game.getEnabledImages().get(selected));
                        } else {
                            gamePanel.removeAll();
                            gamePanel.add(createGameItem(game.getEnabledImages().get(selected)), c);
                            gamePanel.revalidate();
                            gamePanel.repaint();
                            parent.clearGrid();
                            parent.addGrid(currentPanel);
                            parent.revalidate();
                            parent.repaint();
                        }
                    }
                    
                    @Override
                    public void audioFinished() {
                        
                    }
                    
                    @Override
                    public boolean mute() {
                        return true;
                    }
                });
        
        panelList.add(panel);
        selector.setList(panelList);
        selector.start();
        
        return panel;
    }
    
    private void congratulate(GameImage image) {
        
        tileCreator.playAudio(getWinSound());
        
        JPanel finalImage = tileCreator.create("",
                image.getImage(),
                null,
                new TileAction() {
                    @Override
                    public void act() {
                    }
                    
                    @Override
                    public void audioFinished() {
                    }
                });
        
        gamePanel.removeAll();
        gamePanel.add(finalImage);
        gamePanel.revalidate();
        gamePanel.repaint();
        
        ControlsPanel controls = new ControlsPanel(user, this);
        
        controlsPanel.add(controls);
        
        controls.getSelector().addListeners(controls.getControls());
        controls.getSelector().setList(controls.getControls());
        controls.getSelector().start();
        
        parent.clearGrid();
        parent.addGrid(this);
        parent.revalidate();
        parent.repaint();
    }
    
    public void setGame(StimulusReactionGame game) {
        this.game = game;
    }
    
    public void newGame() {
        tileCreator.freePlayerResources();
        StimulusReactionGamePanel gamePanel = new StimulusReactionGamePanel(user, parent);
        parent.clearGrid();
        parent.addGrid(gamePanel);
    }
    
    public void playAgain() {
        tileCreator.freePlayerResources();
        StimulusReactionGamePanel gamePanel = new StimulusReactionGamePanel(user, parent, game);
        parent.clearGrid();
        parent.addGrid(gamePanel);
    }
    
    public void exit() {
        tileCreator.freePlayerResources();
        GamesPanel gamesPanel = new GamesPanel(user, parent);
        parent.clearGrid();
        parent.addGrid(gamesPanel);
    }
    
    protected String getWinSound() {
        String sound = null;
        
        if (game.getWinSound() != null && !game.getWinSound().isEmpty()) {
            sound = game.getWinSound();
        } else {
            for (GameType gameType : user.getGameModule().getGameTypes()) {
                if ("stimulusReactionGame".equals(gameType.getType()) && gameType.getWinSound() != null && !gameType.getWinSound().isEmpty()) {
                    sound = gameType.getWinSound();
                }
            }
        }
        if (sound == null) {
            sound = "demo_resources/sounds/games/winSound.mp3";
        }
        return sound;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
