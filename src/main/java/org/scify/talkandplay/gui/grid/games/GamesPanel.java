package org.scify.talkandplay.gui.grid.games;

import java.util.ArrayList;
import javax.swing.JPanel;
import org.scify.talkandplay.gui.grid.BaseGridPanel;
import org.scify.talkandplay.gui.grid.GridFrame;
import org.scify.talkandplay.gui.grid.TileAction;
import org.scify.talkandplay.models.User;
import org.scify.talkandplay.models.games.GameType;

public class GamesPanel extends BaseGridPanel {

    public GamesPanel(User user, GridFrame parent) {
        super(user, parent);

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
            .addGap(0, 535, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initCustomComponents() {
        removeAll();
        initLayout();

        panelList = new ArrayList<>();

        for (GameType gameType : user.getGameModule().getGameTypes()) {
            JPanel gamePanel = createGameItem(gameType);
            add(gamePanel);
            c.gridx++;
            panelList.add(gamePanel);
        }

        JPanel backPanel = createBackItem();
        add(backPanel);
        panelList.add(backPanel);

        fillWithEmpties();

        timer.setList(panelList);
        timer.start();

        revalidate();
        repaint();
        parent.clearGrid();
        parent.addGrid(this);
        parent.revalidate();
        parent.repaint();
    }

    private JPanel createGameItem(final GameType gameType) {

        JPanel panel = tileCreator.create(gameType.getName(),
                gameType.getImage(),
                gameType.getImageURL(),
                gameType.getSound(),
                new TileAction() {
                    @Override
                    public void act() {
                        timer.cancel();
                        if ("stimulusReactionGame".equals(gameType.getType())) {
                            showStimulusReactionGame();
                        } else if ("sequenceGame".equals(gameType.getType())) {
                            showSequenceGame();
                        } else if ("similarityGame".equals(gameType.getType())) {
                            showSimilarityGame();
                        }
                    }

                    @Override
                    public void audioFinished() {
                        if ("stimulusReactionGame".equals(gameType.getType())) {
                            showStimulusReactionGame();
                        } else if ("sequenceGame".equals(gameType.getType())) {
                            showSequenceGame();
                        } else if ("similarityGame".equals(gameType.getType())) {
                            showSimilarityGame();
                        }
                    }
                });

        return panel;
    }

    private JPanel createBackItem() {
        JPanel panel = tileCreator.create("Πίσω",
                null,
                getClass().getResource("/org/scify/talkandplay/resources/back-icon.png"),
                null,
                new TileAction() {
                    @Override
                    public void act() {
                        timer.cancel();
                        showMainMenu();
                    }

                    @Override
                    public void audioFinished() {
                        return;
                    }

                    @Override
                    public boolean mute() {
                        return true;
                    }
                });

        return panel;
    }

    private void showStimulusReactionGame() {
        StimulusReactionGamePanel gamePanel = new StimulusReactionGamePanel(user, parent);
    }

    private void showSequenceGame() {
        SequenceGamePanel gamePanel = new SequenceGamePanel(user, parent);
    }

    private void showSimilarityGame() {
        SimilarityGamePanel gamePanel = new SimilarityGamePanel(user, parent);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
