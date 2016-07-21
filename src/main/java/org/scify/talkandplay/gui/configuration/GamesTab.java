package org.scify.talkandplay.gui.configuration;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;
import org.scify.talkandplay.gui.helpers.GuiHelper;
import org.scify.talkandplay.gui.helpers.UIConstants;
import org.scify.talkandplay.models.User;
import org.scify.talkandplay.models.games.Game;
import org.scify.talkandplay.models.games.GameType;
import org.scify.talkandplay.services.ModuleService;

public class GamesTab extends javax.swing.JPanel {

    private User user;
    private GuiHelper guiHelper;
    private ModuleService moduleService;
    private ConfigurationPanel parent;
    private List<GamePanel> gamePanels;

    public GamesTab(User user, ConfigurationPanel parent) {
        this.user = user;
        this.guiHelper = new GuiHelper();
        this.moduleService = new ModuleService();
        this.parent = parent;

        initComponents();
        initCustomComponents();
    }

    private void initCustomComponents() {
        gamePanels = new ArrayList();
        guiHelper.drawButton(saveButton);
        Font font = new Font(UIConstants.mainFont, Font.BOLD, 16);
        step1Label.setFont(font);
        step2Label.setFont(font);

        gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.Y_AXIS));
        gamesComboBox.setBorder(new LineBorder(Color.decode(UIConstants.green), 1));
        gamesComboBox.setFont(new Font(UIConstants.green, Font.PLAIN, 12));

        gamesComboBox.addItem("[-- Επίλεξε παιχνίδι --]");
        gamesComboBox.addItem("Ερέθισμα - Αντίδραση");
        gamesComboBox.addItem("Χρονική αλληλουχία");
        gamesComboBox.addItem("Βρες το όμοιο");

        gamesComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                System.out.println(ie.getItem());
                if ("Ερέθισμα - Αντίδραση".equals(ie.getItem())) {
                    showGamesPerType("stimulusReactionGame");
                } else if ("Χρονική αλληλουχία".equals(ie.getItem())) {
                    showGamesPerType("sequenceGame");
                } else if ("Βρες το όμοιο".equals(ie.getItem())) {
                    showGamesPerType("similarityGame");
                }
            }
        });
    }

    private void showGamesPerType(String type) {
        for (GameType gameType : user.getGameModule().getGameTypes()) {
            if (type.equals(gameType.getType())) {
                for (Game game : gameType.getGames()) {
                    GamePanel gamePanel = new GamePanel(game);
                    gamesPanel.add(gamePanel);
                    gamePanels.add(gamePanel);

                }
            }
        }
        gamesPanel.revalidate();
        gamesPanel.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        step1Label = new javax.swing.JLabel();
        gamesComboBox = new javax.swing.JComboBox();
        step2Label = new javax.swing.JLabel();
        gamesPanel = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        step1Label.setText("1. Επίλεξε κατηγορία παιχνιδιού");

        gamesComboBox.setBackground(new java.awt.Color(255, 255, 255));

        step2Label.setText("2. Διαχειρίσου τις διαθέσιμες ομάδες εικόνων");

        gamesPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout gamesPanelLayout = new javax.swing.GroupLayout(gamesPanel);
        gamesPanel.setLayout(gamesPanelLayout);
        gamesPanelLayout.setHorizontalGroup(
            gamesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        gamesPanelLayout.setVerticalGroup(
            gamesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 194, Short.MAX_VALUE)
        );

        saveButton.setBackground(new java.awt.Color(75, 161, 69));
        saveButton.setFont(saveButton.getFont());
        saveButton.setForeground(new java.awt.Color(255, 255, 255));
        saveButton.setText("Αποθήκευση");
        saveButton.setBorder(null);
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gamesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(step1Label)
                            .addComponent(step2Label))
                        .addGap(0, 149, Short.MAX_VALUE))
                    .addComponent(gamesComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveButton)
                .addGap(182, 182, 182))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(step1Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gamesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(step2Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gamesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(saveButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveButtonMouseClicked
       
        
        
        
    }//GEN-LAST:event_saveButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox gamesComboBox;
    private javax.swing.JPanel gamesPanel;
    private javax.swing.JButton saveButton;
    private javax.swing.JLabel step1Label;
    private javax.swing.JLabel step2Label;
    // End of variables declaration//GEN-END:variables
}
