package org.bertvn.common;

public enum GameModes {
    BEGINNER(9, 9, 10, 252, 314),
    INTERMEDIATE(16, 16, 40, 482, 482),
    EXPERT(16, 30, 99, 756, 482),
    ;

    private final int rows;
    private final int columns;
    private final int bombs;
    private final int width;
    private final int height;

    private GameModes(int rows, int columns, int bombs, int width, int height) {
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
