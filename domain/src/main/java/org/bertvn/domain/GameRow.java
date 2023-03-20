package org.bertvn.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameRow {

    private final Map<Integer, GameCell> gameCells;

    public GameRow(int rowIndex, int columnCount) {
        gameCells = new HashMap<>();
        for (int i = 0; i < columnCount; i++) {
            gameCells.put(i, new GameCell(rowIndex, i));
        }
    }

    public GameCell getColumn(int bombColumn) {
        return gameCells.get(bombColumn);
    }

    public Collection<GameCell> getCells() {
        return gameCells.values();
    }
}
