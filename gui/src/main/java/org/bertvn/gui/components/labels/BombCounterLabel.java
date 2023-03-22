package org.bertvn.gui.components.labels;

import org.bertvn.gui.events.BombFlaggedEvent;
import org.bertvn.gui.events.BombSetEvent;
import org.bertvn.gui.events.IGameEvent;
import org.bertvn.gui.events.IObserver;

import javax.swing.*;

public class BombCounterLabel extends JLabel implements IObserver {

    private int count = 0;

    public BombCounterLabel() {
        updateLabel();
    }

    private void updateLabel() {
        setText(String.format("%03d", count));
    }

    @Override
    public void notify(IGameEvent gameEvent) {
        if(gameEvent instanceof BombSetEvent bombSetEvent) {
            count = bombSetEvent.getBombCount();
            updateLabel();
        }
        if(gameEvent instanceof BombFlaggedEvent flaggedEvent) {
            if(flaggedEvent.isFlagged()) {
                count--;
            }
            else {
                count++;
            }
            updateLabel();
        }
    }
}
