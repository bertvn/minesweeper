package org.bertvn.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.*;

class GameGeneratorTest {

    private static final long MAP_SEED = 4815162342L;

    @Test
    @Timeout(3)
    void testGeneration() {
        GameHandler gameHandler = new GameHandler();
        gameHandler.reset();
        gameHandler.clearCell(5, 5);
        assertTrue(true);
    }

    //#region test map
    // 0 1 ⚑ 1 1 ⚑ 1 0 0 0
    // 0 2 2 2 1 1 1 0 0 0
    // 0 1 ⚑ 1 0 0 1 1 1 0
    // 2 3 3 2 1 1 3 ⚑ 2 0
    // ⚑ ⚑ 3 ⚑ 1 1 ⚑ ⚑ 2 0
    // ⚑ ⚑ 4 1 2 2 4 3 2 0
    // ⚑ ⚑ 2 0 2 ⚑ 4 ⚑ 2 0
    // 3 3 2 0 2 ⚑ 5 ⚑ 2 0
    // 1 ⚑ 1 1 2 3 ⚑ 2 1 0
    // 1 1 1 1 ⚑ 2 1 1 0 0
    //#endregion


    @Test
    void testClear() {
        GameHandler gameHandler = generateGame();
        assertFalse(gameHandler.isCleared(0, 1));
        assertTrue(gameHandler.clearCell(0, 1));
        assertTrue(gameHandler.isCleared(0, 1));
    }

    @Test
    @Timeout(3)
    void testOpenSurrounding() {
        GameHandler gameHandler = generateGame();
        markAllFlags(gameHandler);
        assertTrue(gameHandler.clearCell(1, 2));
        assertFalse(gameHandler.isCleared(0, 1));
        assertFalse(gameHandler.isCleared(1, 1));
        assertFalse(gameHandler.isCleared(2, 1));
        assertFalse(gameHandler.isCleared(0, 3));
        assertFalse(gameHandler.isCleared(1, 3));
        assertFalse(gameHandler.isCleared(2, 3));

        assertTrue(gameHandler.clearSurrounding(1, 2));
        assertTrue(gameHandler.isCleared(0, 1));
        assertTrue(gameHandler.isCleared(1, 1));
        assertTrue(gameHandler.isCleared(2, 1));
        assertTrue(gameHandler.isCleared(0, 3));
        assertTrue(gameHandler.isCleared(1, 3));
        assertTrue(gameHandler.isCleared(2, 3));
    }

    @Test
    void testFlag() {
        GameHandler gameHandler = generateGame();
        assertFalse(gameHandler.isFlagged(0, 2));
        gameHandler.flagCell(0, 2);
        assertTrue(gameHandler.isFlagged(0, 2));
    }

    @Test
    void testSuccessFullClear() {
        GameHandler gameHandler = generateGame();

        //flag everything for easy clear
        markAllFlags(gameHandler);

        clearCellSuccessfully(gameHandler, 0, 0);
        clearSurroundingSuccessfully(gameHandler, 1, 1);
        clearSurroundingSuccessfully(gameHandler, 1, 2);
        clearSurroundingSuccessfully(gameHandler, 1, 3);
        clearCellSuccessfully(gameHandler, 0, 9);
        clearSurroundingSuccessfully(gameHandler, 3, 1);
        clearCellSuccessfully(gameHandler, 6, 3);
        clearSurroundingSuccessfully(gameHandler, 5, 4);
        clearSurroundingSuccessfully(gameHandler, 8, 2);
        clearSurroundingSuccessfully(gameHandler, 8, 4);
        clearSurroundingSuccessfully(gameHandler, 8, 5);
        clearSurroundingSuccessfully(gameHandler, 5, 5);
        clearSurroundingSuccessfully(gameHandler, 5, 5);
        clearSurroundingSuccessfully(gameHandler, 6, 6);
        clearCellSuccessfully(gameHandler, 7, 0);
        clearCellSuccessfully(gameHandler, 8, 0);

        assertFalse(gameHandler.isCompleted());
        assertTrue(gameHandler.clearCell(9, 0));
        assertTrue(gameHandler.isCompleted());
    }

    @Test
    void testFailedClear() {
        GameHandler gameHandler = generateGame();
        assertFalse(gameHandler.clearCell(0, 2));
    }

    private static GameHandler generateGame() {
        GameHandler gameHandler = new GameHandler(MAP_SEED);
        gameHandler.changeGameBoard(10, 10, 20);
        gameHandler.reset();
        gameHandler.clearCell(5, 5);
        return gameHandler;
    }

    private static void clearCellSuccessfully(GameHandler gameHandler, int row, int column) {
        assertTrue(gameHandler.clearCell(row, column));
        assertFalse(gameHandler.isCompleted());
    }

    private static void clearSurroundingSuccessfully(GameHandler gameHandler, int row, int column) {
        assertTrue(gameHandler.clearSurrounding(row, column));
        assertFalse(gameHandler.isCompleted());
    }


    private static void markAllFlags(GameHandler gameHandler) {
        gameHandler.flagCell(0, 2);
        gameHandler.flagCell(0, 5);
        gameHandler.flagCell(2, 2);
        gameHandler.flagCell(3, 7);
        gameHandler.flagCell(4, 0);
        gameHandler.flagCell(4, 1);
        gameHandler.flagCell(4, 3);
        gameHandler.flagCell(4, 6);
        gameHandler.flagCell(4, 7);
        gameHandler.flagCell(5, 0);
        gameHandler.flagCell(5, 1);
        gameHandler.flagCell(6, 0);
        gameHandler.flagCell(6, 1);
        gameHandler.flagCell(6, 5);
        gameHandler.flagCell(6, 7);
        gameHandler.flagCell(7, 5);
        gameHandler.flagCell(7, 7);
        gameHandler.flagCell(8, 1);
        gameHandler.flagCell(8, 6);
        gameHandler.flagCell(9, 4);
    }
}