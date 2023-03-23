package org.bertvn.gui.components;

import org.bertvn.business.GameHandler;
import org.bertvn.dto.GameCellDto;
import org.bertvn.gui.events.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel implements IObserver {

    private static final Color[] COLORS = new Color[]{
            Color.BLACK, new Color(0, 102, 0), new Color(102, 0, 0),
            new Color(0, 0, 102), new Color(0, 102, 51), new Color(102, 0, 51),
            new Color(0, 51, 102), new Color(0, 51, 0), new Color(51, 0, 0)
    };

    private GameHandler gameHandler;
    private int squareSize;
    private boolean started = false;
    private boolean active = true;
    private boolean renderBombs = false;

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        gameHandler.reset();
        Notifier.getInstance().notify(new BombSetEvent(gameHandler.getBombs()));
        repaint();

        MouseAdapter mouseListener = new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                if(!active) {
                    return;
                }
                int x = e.getX();
                int y = e.getY();
                int xCell = x / squareSize;
                int yCell = y / squareSize;
                if(e.getButton() == MouseEvent.BUTTON1) {
                    if(!started) {
                        started = true;
                        Notifier.getInstance().notify(new GameStartEvent());
                    }
                    if(e.getClickCount() == 2) {
                        boolean safe = gameHandler.clearSurrounding(yCell, xCell);
                        if(!safe) {
                            Notifier.getInstance().notify(new GameFinishEvent(false));
                        }
                    }
                    else {
                        boolean safe = gameHandler.clearCell(yCell, xCell);
                        if(!safe) {
                            Notifier.getInstance().notify(new GameFinishEvent(false));
                        }
                    }

                }
                else if(e.getButton() == MouseEvent.BUTTON3) {
                    if(!started) {
                        return;
                    }
                    boolean modified = gameHandler.flagCell(yCell, xCell);
                    if(modified) {
                        Notifier.getInstance().notify(new BombFlaggedEvent(gameHandler.isFlagged(yCell, xCell)));
                    }
                }
                repaint();

                if(gameHandler.isCompleted()) {
                    Notifier.getInstance().notify(new GameFinishEvent(true));
                }

            }
        };
        addMouseListener(mouseListener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int rows = gameHandler.getRows();
        int columns = gameHandler.getColumns();
        Dimension size = getSize();
        double width = size.getWidth();
        double height = size.getHeight();
        double heightFactor = height / rows;
        double widthFactor = width / columns;
        squareSize = (int) Math.round(Math.min(heightFactor, widthFactor));
        super.paintComponent(g);
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                g.setColor(Color.BLACK);
                g.drawRect(j * squareSize, i * squareSize, squareSize, squareSize);
                GameCellDto cell = gameHandler.getCell(i, j);
                String value = "";
                switch(cell.cellState()) {
                    case CLEAN -> {
                        if(renderBombs && cell.isBomb()) {
                            value = "⦻";
                        }
                    }
                    case DIRTY -> {
                        if(cell.isBomb()) {
                            value = "⦻";
                        }
                        else {
                            int bombCount = cell.bombCount();
                            g.setColor(COLORS[bombCount]);
                            value = "" + bombCount;
                        }
                    }
                    case FLAGGED -> {
                        value = "⚑";
                        g.setColor(Color.RED);
                    }
                }
                g.drawString(value, j * squareSize + 3, (i + 1) * squareSize - 2);
            }
        }
    }

    @Override
    public void notify(IGameEvent gameEvent) {
        if(gameEvent instanceof GameFinishEvent finishEvent) {
            if(!finishEvent.isWon()) {
                renderBombs = true;
            }
            repaint();
            active = false;
        }
        if(gameEvent instanceof GameChangeEvent gameChange) {
            gameHandler.changeGameBoard(gameChange.getColumns(), gameChange.getRows(), gameChange.getBombs());
        }
        if(gameEvent instanceof GameResetEvent) {
            gameHandler.reset();
            started = false;
            renderBombs = false;
            repaint();
            active = true;
        }
    }
}
