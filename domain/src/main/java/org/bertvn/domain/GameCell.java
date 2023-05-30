package org.bertvn.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class GameCell {
    private final int row;
    private final int column;

    private boolean isBomb;
    private GameCellState cellState = GameCellState.CLEAN;
    private int bombCount = 0;

    public void addBombCount() {
        bombCount++;
    }

    public boolean toggleFlag() {
        switch(cellState) {
            case CLEAN -> cellState = GameCellState.FLAGGED;
            case FLAGGED -> cellState = GameCellState.CLEAN;
            case DIRTY -> {
                return false;
            }
        }
        return true;
    }

    public boolean isFlagged() {
        return cellState == GameCellState.FLAGGED;
    }

    public boolean isCleared() {
        return cellState == GameCellState.DIRTY;
    }
}
