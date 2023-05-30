package org.bertvn.gui.events;

import org.bertvn.gui.GameState;

public class GameFinishEvent implements IGameEvent, IGameStopEvent {

    private final GameState state;
    private final int row;
    private final int column;

    public GameFinishEvent(GameState state){
        this(state, -1, -1);
    }

    public GameFinishEvent(GameState state, int row, int column) {
        this.state = state;
        this.row = row;
        this.column = column;
    }

    public GameState getState() {
        return state;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
