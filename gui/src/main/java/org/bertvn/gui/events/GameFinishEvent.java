package org.bertvn.gui.events;

public class GameFinishEvent implements IGameEvent {

    private final boolean won;

    public GameFinishEvent(boolean won){
        this.won = won;
    }

    public boolean isWon() {
        return won;
    }
}
