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

    private boolean bomb;
    private GameCellState cellState = GameCellState.CLEAN;
    private int bombCount = 0;

    public void addBombCount() {
        bombCount++;
    }

    public void toggleFlag() {
        switch(cellState) {
            case CLEAN -> cellState = GameCellState.FLAGGED;
            case FLAGGED -> cellState = GameCellState.CLEAN;
            case DIRTY -> {
                //do nothing or log?
            }
        }
    }

    public boolean isFlagged() {
        return cellState == GameCellState.FLAGGED;
    }

    public boolean isCleared() {
        return cellState == GameCellState.DIRTY;
    }
}
