package org.bertvn.gui.components.labels;

import org.bertvn.gui.events.*;

import javax.swing.*;

public class BombCounterLabel extends JLabel implements IObserver {

    private int count = 0;
    private int initialCount;

    public BombCounterLabel() {
        updateLabel();
    }

    private void updateLabel() {
        setText(String.format("%03d", count));
    }

    @Override
    public void notify(IGameEvent gameEvent) {
        if(gameEvent instanceof BombSetEvent bombSetEvent) {
            initialCount = bombSetEvent.getBombCount();
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
        if(gameEvent instanceof GameChangeEvent gameChanger) {
            initialCount = gameChanger.getBombs();
        }
        if(gameEvent instanceof GameResetEvent) {
            count = initialCount;
            updateLabel();
        }
    }
}
