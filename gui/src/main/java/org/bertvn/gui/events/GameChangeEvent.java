package org.bertvn.gui.events;

import org.bertvn.common.GameModes;

public class GameChangeEvent extends GameResetEvent {

    private final int rows;
    private final int columns;
    private final int bombs;

    public GameChangeEvent(GameModes gameMode) {
        this(gameMode.getRows(), gameMode.getColumns(), gameMode.getBombs());
    }

    public GameChangeEvent(int rows, int columns, int bombs) {
        this.rows = rows;
        this.columns = columns;
        this.bombs = bombs;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getBombs() {
        return bombs;
    }
}
