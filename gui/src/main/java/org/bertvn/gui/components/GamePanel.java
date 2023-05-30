package org.bertvn.gui.components;

import org.bertvn.business.GameHandler;
import org.bertvn.dto.GameCellDto;
import org.bertvn.gui.GameState;
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
    private final CellPainter cellPainter = new CellPainter();

    private GameHandler gameHandler;
    private boolean started = false;
    private boolean active = true;


    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        gameHandler.reset();
        Notifier.getInstance().notify(new BombSetEvent(gameHandler.getBombs()));
        repaint();

        MouseAdapter mouseListener = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if(!active) {
                    return;
                }
                if(e.getButton() == MouseEvent.BUTTON1) {
                    Notifier.getInstance().notify(new GameMouseEvent(GameMouseEvent.Type.HOLD));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(!active) {
                    return;
                }
                int x = e.getX();
                int y = e.getY();
                int xCell = x / GUIConstants.CELL_SIZE;
                int yCell = y / GUIConstants.CELL_SIZE;
                if(!gameHandler.isValidCell(yCell, xCell)){
                    return;
                }
                if(e.getButton() == MouseEvent.BUTTON1) {
                    if(!started) {
                        started = true;
                        Notifier.getInstance().notify(new GameStartEvent());
                    }
                    Notifier.getInstance().notify(new GameMouseEvent(GameMouseEvent.Type.RELEASE));
                    if(e.getClickCount() == 2) {
                        boolean safe = gameHandler.clearSurrounding(yCell, xCell);
                        if(!safe) {
                            Notifier.getInstance().notify(new GameFinishEvent(GameState.LOST));
                        }
                    }
                    else {
                        boolean safe = gameHandler.clearCell(yCell, xCell);
                        if(!safe) {
                            Notifier.getInstance().notify(new GameFinishEvent(GameState.LOST, yCell, xCell));
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
                    Notifier.getInstance().notify(new GameFinishEvent(GameState.WON));
                }
//                gameHandler.printBoard();
            }
        };
        addMouseListener(mouseListener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int rows = gameHandler.getRows();
        int columns = gameHandler.getColumns();
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                GameCellDto cell = gameHandler.getCell(i, j);
                cellPainter.drawCell(g, cell, i, j);
            }
        }
    }

    @Override
    public void notify(IGameEvent gameEvent) {
        if(gameEvent instanceof GameFinishEvent finishEvent) {
            cellPainter.updateState(finishEvent.getState(), finishEvent.getRow(), finishEvent.getColumn());
            repaint();
            active = false;
        }
        if(gameEvent instanceof GameChangeEvent gameChange) {
            gameHandler.changeGameBoard(gameChange.getColumns(), gameChange.getRows(), gameChange.getBombs());
        }
        if(gameEvent instanceof GameResetEvent) {
            gameHandler.reset();
            started = false;
            cellPainter.reset();
            repaint();
            active = true;
        }
        if(gameEvent instanceof GameChangeEvent gameChanger) {
            int gamePanelWidth = gameChanger.getColumns() * GUIConstants.CELL_SIZE;
            int gamePanelHeight = gameChanger.getRows() * GUIConstants.CELL_SIZE;
            setSize(gamePanelWidth, gamePanelHeight);
            setPreferredSize(new Dimension(gamePanelWidth, gamePanelHeight));
        }
    }
}
