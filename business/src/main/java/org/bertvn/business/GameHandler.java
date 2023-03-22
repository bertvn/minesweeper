package org.bertvn.business;

import org.bertvn.domain.GameBoard;
import org.bertvn.domain.GameCell;
import org.bertvn.domain.GameCellState;
import org.bertvn.domain.GameRow;

import java.util.List;
import java.util.stream.IntStream;

public class GameHandler {

    private final GameBoard gameBoard;
    private final GameGenerator gameGenerator;

    public GameHandler() {
        this(null);
    }

    public GameHandler(Long mapSeed) {
        this.gameBoard = new GameBoard();
        this.gameGenerator = new GameGenerator(gameBoard, mapSeed);
    }

    public void changeGameBoard(int width, int height, int bombs) {
        gameGenerator.changeGameBoard(width, height, bombs);
    }

    public boolean clearCell(int row, int column) {
        if(!gameBoard.isInitialized()) {
            gameGenerator.generateMap(row, column);
        }
        GameCell cell = gameBoard.getCell(row, column);
        boolean safe = clearCell(cell);

        if(!cell.isBomb() && cell.getBombCount() == 0) {
            clearSurrounding(cell);
        }

        return safe;
    }

    public boolean clearCell(GameCell cell) {
        if(cell.getCellState() != GameCellState.CLEAN) {
            return true;
        }
        if(cell.isBomb()) {
            return false;
        }
        cell.setCellState(GameCellState.DIRTY);
        return true;
    }

    public boolean clearSurrounding(int row, int column) {
        return clearSurrounding(gameBoard.getCell(row, column));
    }

    private boolean clearSurrounding(GameCell cell) {
        int bombCount = cell.getBombCount();
        List<GameCell> surrounding = gameBoard.findSurrounding(cell);
        long flaggedBombs = surrounding.stream().filter(x -> x.getCellState() == GameCellState.FLAGGED).count();
        if(bombCount == flaggedBombs) {
            for(GameCell gameCell : surrounding) {
                GameCellState cellState = gameCell.getCellState();
                if(cellState == GameCellState.DIRTY || cellState == GameCellState.FLAGGED) {
                    continue;
                }
                boolean safe = clearCell(gameCell);
                if(!safe) {
                    return false;
                }
                if(gameCell.getBombCount() == 0) {
                    clearSurrounding(gameCell);
                }
            }
        }
        return true;
    }

    public void flagCell(int row, int column) {
        gameBoard.getCell(row, column).toggleFlag();
    }

    public boolean isCleared(int row, int column) {
        return gameBoard.getCell(row, column).isCleared();
    }

    public boolean isFlagged(int row, int column) {
        return gameBoard.getCell(row, column).isFlagged();
    }

    public String getChar(int row, int column){
        GameCell cell = gameBoard.getCell(row, column);
        if(cell.isFlagged()) {
            return "⚑";
        }
        if(cell.isCleared()){
            return "" + cell.getBombCount();
        }
        return "";
    }

    public boolean isCompleted() {
        return IntStream.range(0, gameBoard.getRows())
                .mapToObj(gameBoard::getRow)
                .flatMap(row -> row.getCells().stream())
                .allMatch(cell -> cell.isBomb() || cell.isCleared());
    }

    public void reset() {
        gameBoard.reset();
    }

    public int getRows() {
        return gameBoard.getRows();
    }

    public int getColumns() {
        return gameBoard.getColumns();
    }

    public int getBombs() {
        return gameBoard.getBombs();
    }


    //#region print board
    public void printBoard() {
        printBorder();
        for(int i = 0; i < gameBoard.getRows(); i++) {
            printRow(gameBoard.getRow(i), i);
        }
        printBorder();
    }


    private void printBorder() {
        System.out.print("+");
        for(int i = 0; i < gameBoard.getColumns(); i++) {
            System.out.print(" " + i);
        }
        System.out.println(" +");
    }

    private void printRow(GameRow gameRow, int rowIndex) {
        System.out.print(rowIndex);
        for(int i = 0; i < gameBoard.getColumns(); i++) {
            GameCell cell = gameRow.getColumn(i);
            GameCellState cellState = cell.getCellState();
            if(cellState == GameCellState.CLEAN) {
                System.out.print(" □");
            }
            else if(cellState == GameCellState.FLAGGED) {
                System.out.print(" ⚑");
            }
            else {
                System.out.print(" " + cell.getBombCount());
            }
        }
        System.out.println(" " + rowIndex);
    }
    //#endregion
}
