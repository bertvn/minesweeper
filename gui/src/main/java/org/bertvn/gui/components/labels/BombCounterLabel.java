package org.bertvn.gui.components.labels;

import org.bertvn.gui.GameState;
import org.bertvn.gui.events.*;

public class BombCounterLabel extends NumberPainter implements IObserver {

    private int count = 0;
    private int initialCount;

    public BombCounterLabel() {
        //do nothing
    }

    @Override
    protected String getText() {
        return (String.format("%03d", count));
    }

    @Override
    public void notify(IGameEvent gameEvent) {
        if(gameEvent instanceof BombSetEvent bombSetEvent) {
            initialCount = bombSetEvent.getBombCount();
            count = bombSetEvent.getBombCount();
            repaint();
        }
        if(gameEvent instanceof BombFlaggedEvent flaggedEvent) {
            if(flaggedEvent.isFlagged()) {
                count--;
            }
            else {
                count++;
            }
            repaint();
        }
        if(gameEvent instanceof GameChangeEvent gameChanger) {
            initialCount = gameChanger.getBombs();
            repaint();
        }
        if(gameEvent instanceof GameFinishEvent finishEvent){
            if(finishEvent.getState() == GameState.WON){
                count = 0;
                repaint();
            }
        }
        if(gameEvent instanceof GameResetEvent) {
            count = initialCount;
            repaint();
        }
    }
}
