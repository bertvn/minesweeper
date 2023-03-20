package org.bertvn.business;

import org.bertvn.domain.GameBoard;
import org.bertvn.domain.GameCell;
import org.bertvn.domain.GameLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameGenerator {

    private final GameBoard gameBoard;
    private Long randomSeed;

    public GameGenerator() {
        gameBoard = new GameBoard();
    }

    public GameGenerator(Long randomSeed) {
        this();
        this.randomSeed = randomSeed;
    }

    public void resetGameBoard() {
        gameBoard.reset();
    }

    public void changeGameBoard(int width, int height, int bombs) {
        gameBoard.modify(width, height, bombs);
        gameBoard.reset();
    }

    public GameHandler generateMap(int row, int column) {
        int bombs = gameBoard.getBombs();
        Random random = createRNGGenerator();
        List<GameLocation> bombLocations = new ArrayList<>();
        while(bombs > 0) {
            int bombRow = random.nextInt(gameBoard.getRows());
            int bombColumn = random.nextInt(gameBoard.getColumns());
            if(bombRow == row && bombColumn == column) {
                continue;
            }
            GameLocation gameLocation = new GameLocation(bombRow, bombColumn);
            GameCell bombCell = gameBoard.getCell(gameLocation);
            if(bombCell.isBomb()) {
                continue;
            }
            bombCell.setBomb(true);
            bombs--;
            bombLocations.add(gameLocation);
        }
        for(GameLocation bombLocation : bombLocations) {
            for(GameCell gameCell : gameBoard.findSurrounding(bombLocation)) {
                gameCell.addBombCount();
            }
        }
        GameHandler gameHandler = new GameHandler(gameBoard);
        gameHandler.clearCell(row, column);

        return gameHandler;
    }

    private Random createRNGGenerator() {
        if(randomSeed != null) {
            return new Random(randomSeed);
        }
        return new Random();
    }
}
