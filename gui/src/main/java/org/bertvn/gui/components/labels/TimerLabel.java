package org.bertvn.gui.components.labels;

import org.bertvn.gui.events.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerLabel extends JLabel implements IObserver {

    private final Timer timer;
    private long startTime = -1;

    public TimerLabel() {
        setText("000");
        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(startTime < 0) {
                    startTime = System.currentTimeMillis();
                }
                long now = System.currentTimeMillis();
                long clockTime = now - startTime;

                setText(String.format("%03d", clockTime / 1000));
            }
        });
    }

    @Override
    public void notify(IGameEvent gameEvent) {
        if(gameEvent instanceof GameStartEvent) {
            startTime = -1;
            timer.restart();
        }
        if(gameEvent instanceof IGameStopEvent) {
            timer.stop();
        }
        if(gameEvent instanceof GameResetEvent) {
            startTime = -1;
            setText("000");
        }
    }
}
