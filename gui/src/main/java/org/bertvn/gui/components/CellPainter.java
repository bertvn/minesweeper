package org.bertvn.gui.components;

import org.bertvn.common.GameCellState;
import org.bertvn.dto.GameCellDto;
import org.bertvn.gui.GameState;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class CellPainter {


    private static final Color[] COLORS = new Color[]{
            null, Color.BLUE, new Color(0, 128, 0), Color.RED,
            new Color(0, 0, 128), new Color(128, 0, 0), new Color(0, 128, 128),
            Color.BLACK, GUIConstants.DARK_BORDER
    };

    private final Font font;

    private GameState state;
    private int explodedRow;
    private int explodedColumn;


    public CellPainter() {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("fonts/mine-sweeper-mono.ttf");
        Font foundFont;
        if(resourceAsStream != null) {
            try {
                foundFont = Font.createFont(Font.TRUETYPE_FONT, resourceAsStream);
            }
            catch(FontFormatException | IOException e) {
                foundFont = new Font("Arial", Font.BOLD, 16);
            }
        }
        else {
            foundFont = new Font("Arial", Font.BOLD, 16);
        }
        font = foundFont.deriveFont(Font.PLAIN, 15f);
    }

    public void drawCell(Graphics g, GameCellDto cell, int row, int column) {
        int rowLocation = row * GUIConstants.CELL_SIZE;
        int columnLocation = column * GUIConstants.CELL_SIZE;
        GameCellState gameCellState = cell.cellState();
        g.setColor(GUIConstants.MAIN_COLOR);
        g.fillRect(columnLocation, rowLocation, GUIConstants.CELL_SIZE, GUIConstants.CELL_SIZE);
        if(state == GameState.LOST && cell.isBomb() && gameCellState != GameCellState.FLAGGED) {
            if(row == explodedRow && column == explodedColumn) {
                drawExplodedCell(g, rowLocation, columnLocation);
            }
            drawClearedCell(g, rowLocation, columnLocation);
            drawReflectionOnBomb(g, rowLocation, columnLocation);
        }
        else if(gameCellState == GameCellState.DIRTY) {
            drawClearedCell(g, rowLocation, columnLocation);
        }
        else {
            drawUnclearedCell(g, rowLocation, columnLocation);
        }
        drawGlyph(g, cell, rowLocation, columnLocation);
    }

    /**
     * fill in white spot on the bomb, so it doesn't show as red or grey
     */
    private void drawReflectionOnBomb(Graphics g, int rowLocation, int columnLocation) {
        g.setColor(Color.WHITE);
        g.fillRect(columnLocation + 8, rowLocation + 8, 8, 8);
    }

    private static void drawClearedCell(Graphics g, int rowLocation, int columnLocation) {
        g.setColor(GUIConstants.DARK_BORDER);
        g.fillRect(columnLocation, rowLocation, GUIConstants.CELL_SIZE, 1);
        g.fillRect(columnLocation, rowLocation, 1, GUIConstants.CELL_SIZE);
    }

    private static void drawExplodedCell(Graphics g, int rowLocation, int columnLocation) {
        g.setColor(Color.RED);
        g.fillRect(columnLocation, rowLocation, GUIConstants.CELL_SIZE, GUIConstants.CELL_SIZE);
    }

    private static void drawUnclearedCell(Graphics g, int rowLocation, int columnLocation) {
        g.setColor(GUIConstants.LIGHT_BORDER);
        g.fillRect(columnLocation, rowLocation, GUIConstants.CELL_SIZE - 1, 1);
        g.fillRect(columnLocation, rowLocation + 1, GUIConstants.CELL_SIZE - 2, 1);
        g.fillRect(columnLocation, rowLocation, 1, GUIConstants.CELL_SIZE - 1);
        g.fillRect(columnLocation + 1, rowLocation, 1, GUIConstants.CELL_SIZE - 2);
        g.fillRect(columnLocation, rowLocation, 3, 3);

        g.setColor(GUIConstants.LIGHT_TO_MAIN);
        g.fillRect(columnLocation + 2, rowLocation + 3, 1, GUIConstants.CELL_SIZE - 6);
        g.fillRect(columnLocation + 3, rowLocation + 2, GUIConstants.CELL_SIZE - 6, 1);

        g.setColor(GUIConstants.DARK_BORDER);
        g.fillRect(columnLocation + 1, rowLocation + GUIConstants.CELL_SIZE - 1, GUIConstants.CELL_SIZE - 2, 1);
        g.fillRect(columnLocation + 2, rowLocation + GUIConstants.CELL_SIZE - 2, GUIConstants.CELL_SIZE - 1, 1);
        g.fillRect(columnLocation + GUIConstants.CELL_SIZE - 1, rowLocation + 1, 1, GUIConstants.CELL_SIZE - 1);
        g.fillRect(columnLocation + GUIConstants.CELL_SIZE - 2, rowLocation + 2, 1, GUIConstants.CELL_SIZE - 2);
        g.fillRect(columnLocation + GUIConstants.CELL_SIZE - 3, rowLocation + GUIConstants.CELL_SIZE - 3, 3, 3);

        g.setColor(GUIConstants.DARK_TO_MAIN);
        g.fillRect(columnLocation + GUIConstants.CELL_SIZE - 3, rowLocation + 3, 1, GUIConstants.CELL_SIZE - 6);
        g.fillRect(columnLocation + 3, rowLocation + GUIConstants.CELL_SIZE - 3, GUIConstants.CELL_SIZE - 6, 1);

        //2 corners
        g.setColor(GUIConstants.OUTER_CORNER_COLOR);
        g.fillRect(columnLocation + GUIConstants.CELL_SIZE - 1, rowLocation, 1, 1);
        g.fillRect(columnLocation, rowLocation + GUIConstants.CELL_SIZE - 1, 1, 1);

        //4 inner corners
        g.setColor(GUIConstants.INNER_CORNER_COLOR);
        g.fillRect(columnLocation + GUIConstants.CELL_SIZE - 2, rowLocation + 1, 1, 1);
        g.fillRect(columnLocation + GUIConstants.CELL_SIZE - 3, rowLocation + 2, 1, 1);
        g.fillRect(columnLocation + 1, rowLocation + GUIConstants.CELL_SIZE - 2, 1, 1);
        g.fillRect(columnLocation + 2, rowLocation + GUIConstants.CELL_SIZE - 3, 1, 1);
    }

    private void drawGlyph(Graphics g, GameCellDto cell, int rowLocation, int columnLocation) {
        g.setFont(font);
        String value = "";
        switch(cell.cellState()) {
            case CLEAN -> {
                if(state != GameState.ONGOING && cell.isBomb()) {
                    if(state == GameState.WON) {
                        value = "`";
                        g.setColor(Color.BLACK);
                        g.drawString(value, columnLocation + 2, rowLocation + 20);
                        g.setColor(Color.RED);
                        value = "~";
                    }
                    else {
                        g.setColor(Color.BLACK);
                        value = "*";
                    }
                }
            }
            case DIRTY -> {
                if(cell.isBomb()) {
                    g.setColor(Color.BLACK);
                    value = "*";
                }
                else {
                    int bombCount = cell.bombCount();
                    if(bombCount != 0) {
                        g.setColor(COLORS[bombCount]);
                        value = "" + bombCount;
                    }
                }
            }
            case FLAGGED -> {
                value = "`";
                g.setColor(Color.BLACK);
                g.drawString(value, columnLocation + 2, rowLocation + 20);
                g.setColor(Color.RED);
                value = "~";
            }
        }
        g.drawString(value, columnLocation + 2, rowLocation + 20);
    }


    public void updateState(GameState state, int row, int column) {
        this.state = state;
        this.explodedRow = row;
        this.explodedColumn = column;
    }

    public void reset() {
        state = GameState.ONGOING;
    }
}
