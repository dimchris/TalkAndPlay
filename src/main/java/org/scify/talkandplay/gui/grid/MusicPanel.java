/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scify.talkandplay.gui.grid;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import org.scify.talkandplay.models.User;

/**
 *
 * @author christina
 */
public class MusicPanel extends javax.swing.JPanel {

    private User user;
    private GridFrame parent;
    private Timer timer;
    private int selectedFile;
    private String currentFile;
    private List<JLabel> fileLabels;

    int currSec = 0;

    /**
     * Creates new form MusicPanel
     */
    public MusicPanel(User user, GridFrame parent) {
        this.user = user;
        this.parent = parent;
        this.fileLabels = new ArrayList<>();

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

        musicPanel = new javax.swing.JPanel();
        filesPanel = new javax.swing.JPanel();
        mediaPlayerPanel = new javax.swing.JPanel();

        javax.swing.GroupLayout filesPanelLayout = new javax.swing.GroupLayout(filesPanel);
        filesPanel.setLayout(filesPanelLayout);
        filesPanelLayout.setHorizontalGroup(
            filesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        filesPanelLayout.setVerticalGroup(
            filesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout musicPanelLayout = new javax.swing.GroupLayout(musicPanel);
        musicPanel.setLayout(musicPanelLayout);
        musicPanelLayout.setHorizontalGroup(
            musicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(musicPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(mediaPlayerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        musicPanelLayout.setVerticalGroup(
            musicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mediaPlayerPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
            .addComponent(filesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(musicPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(musicPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initCustomComponents() {

        final MediaPlayerPanel playerPanel = new MediaPlayerPanel(this);
        mediaPlayerPanel.add(playerPanel);

        File musicFolder = new File(user.getEntertainmentModule().getMusicModule().getFolderPath());

        filesPanel.setLayout(new BoxLayout(filesPanel, BoxLayout.PAGE_AXIS));

        JLabel fileLabel;

        for (final File file : musicFolder.listFiles()) {
            fileLabel = new JLabel(file.getName());
            fileLabels.add(fileLabel);
            filesPanel.add(fileLabel);
            fileLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    timer.cancel();
                    currentFile = file.getName();
                    playerPanel.playMedia(getFilePath(file.getName()));
                }
            });
        }

        if (fileLabels.size() == 0) {
            fileLabels.add(new JLabel("Δεν υπάρχουν αρχεία"));
        } else {
            setTimer();
        }

        //addListeners();
        filesPanel.revalidate();
        filesPanel.repaint();
        mediaPlayerPanel.revalidate();
        mediaPlayerPanel.repaint();
        musicPanel.revalidate();
        musicPanel.repaint();
        parent.add(musicPanel);
        parent.revalidate();
        parent.repaint();
    }

    public void setTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (selectedFile == 0) {
                    fileLabels.get(fileLabels.size() - 1).setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
                    fileLabels.get(selectedFile).setFont(new Font("DejaVu Sans", Font.BOLD, 12));
                    selectedFile++;
                } else if (selectedFile == fileLabels.size() - 1) {
                    fileLabels.get(selectedFile - 1).setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
                    fileLabels.get(selectedFile).setFont(new Font("DejaVu Sans", Font.BOLD, 12));
                    selectedFile = 0;
                } else if (selectedFile < fileLabels.size() - 1 && selectedFile > 0) {
                    fileLabels.get(selectedFile - 1).setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
                    fileLabels.get(selectedFile).setFont(new Font("DejaVu Sans", Font.BOLD, 12));
                    selectedFile++;
                }
            }
        }, user.getConfiguration().getRotationSpeed() * 1000, user.getConfiguration().getRotationSpeed() * 1000);
    }

    public String getFilePath(String fileName) {
        return user.getEntertainmentModule().getMusicModule().getFolderPath() + File.separator + fileName;
    }

    public String getPreviousFile() {
        timer.cancel();
        for (int i = 0; i < fileLabels.size(); i++) {
            if (fileLabels.get(i).getText().equals(currentFile)) {
                if (i == 0) {
                    currentFile = fileLabels.get(fileLabels.size() - 1).getText();
                } else {
                    currentFile = fileLabels.get(i - 1).getText();
                }
                break;
            }
        }

        return currentFile;
    }

    public String getNextFile() {
        timer.cancel();
        for (int i = 0; i < fileLabels.size(); i++) {
            if (fileLabels.get(i).getText().equals(currentFile)) {
                System.out.println(fileLabels.get(i).getText() + "=" + currentFile);
                if (i == fileLabels.size() - 1) {
                    currentFile = fileLabels.get(0).getText();
                } else {
                    currentFile = fileLabels.get(i + 1).getText();
                }
                break;
            }
        }
        return currentFile;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel filesPanel;
    private javax.swing.JPanel mediaPlayerPanel;
    private javax.swing.JPanel musicPanel;
    // End of variables declaration//GEN-END:variables
}
