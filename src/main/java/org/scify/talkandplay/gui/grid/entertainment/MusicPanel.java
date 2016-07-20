package org.scify.talkandplay.gui.grid.entertainment;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import org.scify.talkandplay.gui.grid.BaseMediaPanel;
import org.scify.talkandplay.gui.grid.GridFrame;
import org.scify.talkandplay.gui.helpers.UIConstants;
import org.scify.talkandplay.models.User;
import org.scify.talkandplay.models.sensors.KeyboardSensor;
import org.scify.talkandplay.models.sensors.MouseSensor;
import org.scify.talkandplay.models.sensors.Sensor;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class MusicPanel extends BaseMediaPanel {

    private JLabel playingNow;
    private JPanel playerPanel, prevPanel, playPanel, nextPanel, listPanel, exitPanel;

    public MusicPanel(User user, GridFrame parent) {
        super(user, parent, (new File(user.getEntertainmentModule().getMusicModule().getFolderPath())).listFiles());
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
            .addGap(0, 517, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 353, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initCustomComponents() {

        initLayout();

        if (isEmpty()) {
            drawEmpty();
        } else {

            playingNow = new JLabel(" ");
            playingNow.setFont(new Font(UIConstants.mainFont, Font.PLAIN, 18));

            initPlayerButtons();

            filesPanel = new FilesPanel(user, files, this);

            add(filesPanel, c);
            c.gridy++;
            add(playingNow, c);
            c.gridy++;
            add(mediaPlayerPanel, c);
            c.gridy++;
            add(playerPanel, c);

            mediaPlayerPanel.getAudioPlayer().getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
                @Override
                public void finished(MediaPlayer mediaPlayer) {
                    setPlayButton();
                    playingNow.setText(" ");
                }

                @Override
                public void playing(MediaPlayer mediaPlayer) {
                    setPauseButton();
                    playingNow.setText("ΠΑΙΖΕΙ ΤΩΡΑ: " + currentFile);
                }

                @Override
                public void paused(MediaPlayer mediaPlayer) {
                    setPlayButton();
                }
            });
        }

        revalidate();
        repaint();
        parent.clearGrid();
        parent.addGrid(this);
        parent.revalidate();
        parent.repaint();
    }

    private void initPlayerButtons() {

        playerPanel = new JPanel();
        playerPanel.setLayout(new GridBagLayout());
        playerPanel.setBackground(Color.white);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;

        prevPanel = drawButton("Προηγούμενο", getClass().getResource("/org/scify/talkandplay/resources/prev-button.png"));
        playPanel = drawButton("Αναπαραγωγή", getClass().getResource("/org/scify/talkandplay/resources/play-button.png"));
        nextPanel = drawButton("Επόμενο", getClass().getResource("/org/scify/talkandplay/resources/next-button.png"));
        listPanel = drawButton("Λίστα", getClass().getResource("/org/scify/talkandplay/resources/up-icon.png"));
        exitPanel = drawButton("Έξοδος", getClass().getResource("/org/scify/talkandplay/resources/exit-icon.png"));

        playerPanel.add(prevPanel, c);
        c.gridx++;
        playerPanel.add(playPanel, c);
        c.gridx++;
        playerPanel.add(nextPanel, c);
        c.gridx++;
        playerPanel.add(listPanel, c);
        c.gridx++;
        playerPanel.add(exitPanel, c);

        controlsList.add(prevPanel);
        controlsList.add(playPanel);
        controlsList.add(nextPanel);
        controlsList.add(listPanel);
        controlsList.add(exitPanel);

        addListeners();
    }

    private JPanel drawButton(String text, URL imageIcon) {
        JLabel label = new JLabel(text);
        label.setBorder(new EmptyBorder(5, 5, 5, 5));
        label.setFont(new Font(UIConstants.mainFont, Font.PLAIN, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel icon = new JLabel(new ImageIcon(new ImageIcon(imageIcon).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        icon.setBorder(new EmptyBorder(5, 5, 5, 5));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Color.decode(UIConstants.grey));
        panel.setPreferredSize(new Dimension(180, 100));
        panel.setMaximumSize(new Dimension(180, 100));
        panel.setMinimumSize(new Dimension(180, 100));
        panel.setBorder((new LineBorder(Color.white, 5)));

        panel.add(label);
        panel.add(icon);
        return panel;
    }

    @Override
    public void playFile(String fileName) {
        currentFile = fileName;
        timer.cancel();
        mediaPlayerPanel.playMedia(getFilePath(fileName));
        setPauseButton();
        timer.setList(controlsList);
        timer.start();
    }

    public String getFilePath(String fileName) {
        return user.getEntertainmentModule().getMusicModule().getFolderPath() + File.separator + fileName;
    }

    private void addListeners() {

        prevPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor) && currentFile != null && !currentFile.isEmpty()) {
                    getPrevious();
                }
            }
        });
        prevPanel.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
                if (sensorService.shouldSelect(sensor) && currentFile != null && !currentFile.isEmpty()) {
                    getPrevious();
                }
            }
        });

        nextPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor) && currentFile != null && !currentFile.isEmpty()) {
                    getNext();
                }
            }
        });
        nextPanel.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
                if (sensorService.shouldSelect(sensor) && currentFile != null && !currentFile.isEmpty()) {
                    getNext();
                }
            }
        });

        playPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor) && mediaPlayerPanel.isPlaying()) {
                    mediaPlayerPanel.getAudioPlayer().getMediaPlayer().pause();
                } else {
                    mediaPlayerPanel.getAudioPlayer().getMediaPlayer().play();
                }
            }
        });
        playPanel.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
                if (sensorService.shouldSelect(sensor) && mediaPlayerPanel.isPlaying()) {
                    mediaPlayerPanel.getAudioPlayer().getMediaPlayer().pause();
                } else {
                    mediaPlayerPanel.getAudioPlayer().getMediaPlayer().play();
                }
            }
        });

        listPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor)) {
                    timer.cancel();
                    timer.unselect();
                    timer.setList(filesPanel.getPanelList());
                    timer.start();
                }
            }
        });
        listPanel.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
                if (sensorService.shouldSelect(sensor)) {
                    timer.cancel();
                    timer.unselect();
                    timer.setList(filesPanel.getPanelList());
                    timer.start();
                }
            }
        });

        exitPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor)) {
                    timer.cancel();
                    mediaPlayerPanel.stop();
                    parent.clearGrid();
                    EntertainmentPanel entPanel = new EntertainmentPanel(user, parent);
                }
            }
        });
        exitPanel.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
                if (sensorService.shouldSelect(sensor)) {
                    timer.cancel();
                    mediaPlayerPanel.stop();
                    parent.clearGrid();
                    EntertainmentPanel entPanel = new EntertainmentPanel(user, parent);
                }
            }
        });

    }

    private void getPrevious() {
        int selected = filesPanel.getSelected();
        if (selected != -1) {
            if (selected == 0) {
                selected = filesPanel.getFileList().size() - 1;
            } else {
                selected--;
            }

            filesPanel.setSelected(selected);
            currentFile = filesPanel.getFileList().get(selected);
            mediaPlayerPanel.getAudioPlayer().getMediaPlayer().stop();
            mediaPlayerPanel.getAudioPlayer().getMediaPlayer().playMedia(getFilePath(filesPanel.getFileList().get(selected)));
        }
    }

    private void getNext() {
        int selected = filesPanel.getSelected();
        if (selected != -1) {
            if (selected == filesPanel.getFileList().size() - 1) {
                selected = 0;
            } else {
                selected++;
            }

            filesPanel.setSelected(selected);
            currentFile = filesPanel.getFileList().get(selected);
            mediaPlayerPanel.getAudioPlayer().getMediaPlayer().stop();
            mediaPlayerPanel.getAudioPlayer().getMediaPlayer().playMedia(getFilePath(filesPanel.getFileList().get(selected)));
        }
    }

    private void setPlayButton() {
        ((JLabel) playPanel.getComponent(0)).setText("Αναπαραγωγή");
        ((JLabel) playPanel.getComponent(1)).setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/org/scify/talkandplay/resources/play-button.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));

    }

    private void setPauseButton() {
        ((JLabel) playPanel.getComponent(0)).setText("Παύση");
        ((JLabel) playPanel.getComponent(1)).setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/org/scify/talkandplay/resources/pause-button.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
