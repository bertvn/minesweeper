package org.bertvn.gui.events;

import org.bertvn.common.GameModes;

public class GameChangeEvent extends GameResetEvent {

    private final int rows;
    private final int columns;
    private final int bombs;
    private int width;
    private int height;

    public GameChangeEvent(GameModes gameMode) {
        this(gameMode.getRows(), gameMode.getColumns(), gameMode.getBombs(), gameMode.getWidth(), gameMode.getHeight());
    }

    public GameChangeEvent(int rows, int columns, int bombs, int width, int height) {
        this.rows = rows;
        this.columns = columns;
        this.bombs = bombs;
        this.width = width;
        this.height = height;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
