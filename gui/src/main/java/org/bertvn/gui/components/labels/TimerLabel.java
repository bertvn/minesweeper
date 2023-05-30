package org.bertvn.gui.components.labels;

import org.bertvn.gui.events.*;

import javax.swing.*;

public class TimerLabel extends NumberPainter implements IObserver {

    private final Timer timer;
    private long startTime = -1;

    public TimerLabel() {
        timer = new Timer(0, e -> repaint());
    }

    @Override
    public String getText() {
        if(startTime < 0) {
            startTime = System.currentTimeMillis();
        }
        long now = System.currentTimeMillis();
        long clockTime = now - startTime;

        return (String.format("%03d", clockTime / 1000));
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
        }
    }
}
