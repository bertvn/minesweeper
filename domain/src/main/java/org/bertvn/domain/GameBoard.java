package org.bertvn.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard {

    private final Map<Integer, GameRow> gameRowMap = new HashMap<>();

    @Getter
    private int rows;
    @Getter
    private int columns;
    @Getter
    private int bombs;

    public GameBoard() {
        this(10, 10, 20);
    }

    public GameBoard(int rows, int columns, int bombs) {
        assert rows * columns > bombs - 1 : "amount of bombs equal or greater than cells";
        this.rows = rows;
        this.columns = columns;
        this.bombs = bombs;
    }

    public void modify(int width, int height, int bombs) {
        assert width * height > bombs - 1 : "amount of bombs equal or greater than cells";
        this.rows = width;
        this.columns = height;
        this.bombs = bombs;
    }

    public void reset() {
        gameRowMap.clear();
        for(int i = 0; i < rows; i++) {
            gameRowMap.put(i, new GameRow(i, columns));
        }
    }

    public GameCell getCell(int bombRow, int bombColumn) {
        return gameRowMap.get(bombRow).getColumn(bombColumn);
    }

    public GameCell getCell(GameLocation gameLocation) {
        return getCell(gameLocation.row(), gameLocation.column());
    }

    public GameRow getRow(int index) {
        return gameRowMap.get(index);
    }

    public List<GameCell> findSurrounding(GameCell bombLocation) {
        return findSurrounding(bombLocation.getRow(), bombLocation.getColumn());
    }

    public List<GameCell> findSurrounding(GameLocation bombLocation) {
        return findSurrounding(bombLocation.row(), bombLocation.column());
    }

    public List<GameCell> findSurrounding(int row, int column) {
        List<GameCell> surroundingCells = new ArrayList<>();
        for(int i = row - 1; i <= row + 1; i++) {
            if(i < 0 || i >= rows) {
                continue;
            }
            for(int j = column - 1; j <= column + 1; j++) {
                if(j < 0 || j >= columns) {
                    continue;
                }
                if(i == row && j == column) {
                    continue;
                }
                surroundingCells.add(getCell(i, j));
            }
        }
        return surroundingCells;
    }

}
