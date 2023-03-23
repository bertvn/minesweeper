package org.bertvn.common;

public enum GameModes {
    BEGINNER(9, 9, 10),
    INTERMEDIATE(16, 16, 40),
    EXPERT(16, 30, 99),
    ;

    private final int rows;
    private final int columns;
    private final int bombs;

    private GameModes(int rows, int columns, int bombs) {
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
