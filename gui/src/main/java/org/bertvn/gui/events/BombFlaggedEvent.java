package org.bertvn.gui.events;

public class BombFlaggedEvent implements IGameEvent {

    private final boolean flagged;

    public BombFlaggedEvent(boolean flagged){
        this.flagged = flagged;
    }

    public boolean isFlagged() {
        return flagged;
    }
}
