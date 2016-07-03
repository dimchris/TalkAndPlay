package org.scify.talkandplay.gui.grid;

import java.net.URL;
import javax.swing.JPanel;
import org.scify.talkandplay.gui.helpers.GuiHelper;
import org.scify.talkandplay.models.User;
import org.scify.talkandplay.models.sensors.KeyboardSensor;
import org.scify.talkandplay.models.sensors.MouseSensor;
import org.scify.talkandplay.models.sensors.Sensor;
import org.scify.talkandplay.services.SensorService;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

/**
 * Creates the panel that holds a name and an image. Adds the mouse and keyboard
 * listeners and plays a sound if it is set.
 *
 * @author christina
 */
public class TileCreator {

    private User user;
    private TileAction tileAction;
    private SensorService sensorService;
    private GuiHelper guiHelper;
    private AudioMediaPlayerComponent audioPlayer;

    public TileCreator(User user) {
        this.sensorService = new SensorService(user);
        this.guiHelper = new GuiHelper();
        this.audioPlayer = new AudioMediaPlayerComponent();

        initAudioPlayer();
    }

    /**
     * Initialize the audio player and set the action that will be performed
     * after the player finishes
     *
     */
    private void initAudioPlayer() {

        audioPlayer.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void playing(MediaPlayer mediaPlayer) {
                audioPlayer.getMediaPlayer().mute(false);
                audioPlayer.getMediaPlayer().setVolume(100);
            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
                tileAction.audioFinished();
            }
        });
    }

    /**
     * Draw the panel with its name and image and add the mouse and key
     * listeners
     *
     * @param name
     * @param image
     * @param sound
     * @param tileAction
     * @return JPanel panel
     */
    public JPanel create(String name, String image, String sound, TileAction tileAction) {
        JPanel panel = guiHelper.createImagePanel(image, name);
        addListeners(panel, sound, tileAction);
        return panel;
    }

    public JPanel create(String name, String image, URL imageURL, String sound, TileAction tileAction) {
        JPanel panel;
        if (image==null || image.isEmpty()) {
            panel = guiHelper.createImagePanel(imageURL, name);
        } else {
            panel = guiHelper.createImagePanel(image, name);
        }

        addListeners(panel, sound, tileAction);
        return panel;
    }

    private void addListeners(JPanel panel, final String sound, final TileAction tileAction) {
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor)) {
                    act(sound, tileAction);
                }
            }
        });

        panel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
                if (sensorService.shouldSelect(sensor)) {
                    act(sound, tileAction);
                }
            }
        });
    }

    /**
     * The action that will be performed after the right key is pressed
     *
     * @param tileAction
     */
    private void act(String sound, TileAction tileAction) {
        tileAction.act();
        if (sound != null && !sound.isEmpty()) {
            this.tileAction = tileAction;

            audioPlayer.getMediaPlayer().playMedia(sound);
        }
    }

    /**
     * Release the media player resources
     */
    public void freePlayerResources() {
        audioPlayer.getMediaPlayer().stop();
        audioPlayer.getMediaPlayer().release();
    }

    /**
     * Play a sound on demand
     *
     * @param sound
     */
    public void playAudio(String sound) {
        audioPlayer.getMediaPlayer().playMedia(sound);
    }
}