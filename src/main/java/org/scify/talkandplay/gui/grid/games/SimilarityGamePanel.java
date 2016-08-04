package org.scify.talkandplay.gui.grid.games;

import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import org.scify.talkandplay.gui.grid.GridFrame;
import org.scify.talkandplay.gui.grid.tiles.TileAction;
import org.scify.talkandplay.models.User;
import org.scify.talkandplay.models.games.Game;
import org.scify.talkandplay.models.games.GameImage;
import org.scify.talkandplay.models.games.SimilarityGame;

public class SimilarityGamePanel extends BaseGamePanel {

    private boolean endGame = false;
    private String correctImage;

    public SimilarityGamePanel(User user, GridFrame parent) {
        super(user, parent, "similarityGame", null);

        initComponents();
        initCustomComponents();
    }

    public SimilarityGamePanel(User user, GridFrame parent, Game game) {
        super(user, parent, "similarityGame", game);

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
            .addGap(0, 518, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initCustomComponents() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setTopMessage("Πάτα το κουμπί πάνω στο όμοιο!");
        setBottomMessage("");

        int i = randomGenerator.nextInt(game.getEnabledImages().size());
        correctImage = game.getEnabledImages().get(i).getImage();
        bottomPanel.add(createGameItem(game.getEnabledImages().get(i)));

        List<GameImage> tmpImages = new ArrayList(game.getEnabledImages());
        while (!tmpImages.isEmpty()) {
            i = randomGenerator.nextInt(tmpImages.size());
            randomImages.add(tmpImages.get(i));
            tmpImages.remove(i);
        }

        for (GameImage image : randomImages) {
            JPanel panel = createGameItem(image);
            topPanel.add(panel, c1);
            panelList.add(panel);
            c1.gridx++;
        }

        c1.gridx = 0;
        for (int j = 0; j < game.getEnabledImages().size(); j++) {
            bottomPanel.add(tileCreator.createEmpty(), c1);
            c1.gridx++;
        }

        selector.setList(panelList);
        selector.start();

        topPanel.revalidate();
        topPanel.repaint();
        bottomPanel.revalidate();
        bottomPanel.repaint();
        revalidate();
        repaint();
        parent.clearGrid();
        parent.addGrid(this);
        parent.revalidate();
        parent.repaint();
    }

    private JPanel createGameItem(final GameImage image) {

        JPanel panel = tileCreator.create("",
                image.getImage(),
                null,
                new TileAction() {
                    @Override
                    public void act() {
                        for (int i = 0; i < game.getEnabledImages().size(); i++) {
                            if (game.getEnabledImages().get(i).getImage().equals(image.getImage()) && game.getEnabledImages().get(i).getImage().equals(correctImage)) {
                                selector.cancel();
                                congratulate();
                            } else if (game.getEnabledImages().get(i).getImage().equals(image.getImage()) && !game.getEnabledImages().get(i).getImage().equals(correctImage)) {
                                selector.cancel();
                                setBottomMessage(Message.getRandomError());
                                selector.cancel();
                                tileCreator.playAudio(getErrorSound(), new TileAction() {
                                    @Override
                                    public void act() {
                                        return;
                                    }

                                    @Override
                                    public void audioFinished() {
                                        selector.start();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void audioFinished() {
                        if (!endGame) {
                            selector.setList(panelList);
                            selector.start();
                        }
                    }

                    @Override
                    public boolean mute() {
                        return true;
                    }
                });

        return panel;
    }

    private void congratulate() {
        tileCreator.playAudio(getWinSound());
        setBottomMessage("");
        setTopMessage("");

        ControlsPanel controls = new ControlsPanel(user, this);
        topPanel.removeAll();
        topPanel.add(controls);
        topPanel.revalidate();
        topPanel.repaint();

        controls.getSelector().setList(controls.getControls());
        controls.getSelector().start();

        parent.clearGrid();
        parent.addGrid(this);
        parent.revalidate();
        parent.repaint();
    }

    public void newGame() {
        tileCreator.freePlayerResources();
        SimilarityGamePanel gamePanel = new SimilarityGamePanel(user, parent);
        parent.clearGrid();
        parent.addGrid(gamePanel);
    }

    public void playAgain() {
        tileCreator.freePlayerResources();
        SimilarityGamePanel gamePanel = new SimilarityGamePanel(user, parent, (SimilarityGame) game);
        parent.clearGrid();
        parent.addGrid(gamePanel);

    }

    public void exit() {
        tileCreator.freePlayerResources();
        GamesPanel gamesPanel = new GamesPanel(user, parent);
        parent.clearGrid();
        parent.addGrid(gamesPanel);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
