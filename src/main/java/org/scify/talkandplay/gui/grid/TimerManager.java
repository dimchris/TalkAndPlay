package org.scify.talkandplay.gui.grid;

import java.awt.Color;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class TimerManager {
    
    private Timer timer;
    private List<JPanel> panelList;
    private int selected;
    private long nextExecutionTime;
    private long period;
    
    private static final int BORDER_SIZE = 10;
    private static final String BORDER_COLOR = "#2258FF";
    private static final String BACKGROUND_COLOR = "#E9E6FF";
    
    public TimerManager(List<JPanel> panelList, long nextExecutionTime, long period) {
        this.panelList = panelList;
        this.nextExecutionTime = nextExecutionTime;
        this.period = period;
        this.selected = 0;
    }
    
    public void start() {
        this.timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                panelList.get(selected).requestFocusInWindow();
                if (selected == 0) {
                    panelList.get(panelList.size() - 1).setBorder(BorderFactory.createLineBorder(Color.white, BORDER_SIZE));
                    panelList.get(panelList.size() - 1).setBackground(Color.white);
                    panelList.get(selected).setBorder(BorderFactory.createLineBorder(Color.decode(BORDER_COLOR), BORDER_SIZE));
                    panelList.get(selected).setBackground(Color.decode(BACKGROUND_COLOR));
                    
                    for (int i = 0; i < panelList.get(selected).getComponentCount(); i++) {
                        panelList.get(selected).getComponent(i).setBackground(Color.yellow);
                    }
                    
                    selected++;
                } else if (selected == 0 && selected == panelList.size() - 1) {
                    panelList.get(selected).setBorder(BorderFactory.createLineBorder(Color.decode(BORDER_COLOR), BORDER_SIZE));
                    panelList.get(selected).setBackground(Color.decode(BACKGROUND_COLOR));
                } else if (selected == panelList.size() - 1) {
                    panelList.get(selected - 1).setBorder(BorderFactory.createLineBorder(Color.white, BORDER_SIZE));
                    panelList.get(selected - 1).setBackground(Color.white);
                    panelList.get(selected).setBorder(BorderFactory.createLineBorder(Color.decode(BORDER_COLOR), BORDER_SIZE));
                    panelList.get(selected).setBackground(Color.decode(BACKGROUND_COLOR));
                    selected = 0;
                } else if (selected < panelList.size() - 1 && selected > 0) {
                    panelList.get(selected - 1).setBorder(BorderFactory.createLineBorder(Color.white, BORDER_SIZE));
                    panelList.get(selected - 1).setBackground(Color.white);
                    panelList.get(selected).setBorder(BorderFactory.createLineBorder(Color.decode(BORDER_COLOR), BORDER_SIZE));
                    panelList.get(selected).setBackground(Color.decode(BACKGROUND_COLOR));
                    selected++;
                }
            }
        }, nextExecutionTime, period);
    }
    
    public void cancel() {
        timer.cancel();
    }
    
    public void setSelected(int selected) {
        this.selected = selected;
    }
    
    public void setList(List<JPanel> panelList) {
        this.panelList = panelList;
    }
}
