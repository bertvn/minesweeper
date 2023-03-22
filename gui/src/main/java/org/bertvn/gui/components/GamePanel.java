package org.bertvn.gui.components;

import org.bertvn.business.GameHandler;
import org.bertvn.gui.events.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel implements IObserver{

    private GameHandler gameHandler;
    private int squareSize;
    private boolean started = false;
    private MouseListener mouseListener;
    private boolean active = true;

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        gameHandler.reset();
        Notifier.getInstance().notify(new BombSetEvent(gameHandler.getBombs()));
        repaint();

        mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!active){
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
                    gameHandler.flagCell(yCell, xCell);
                    Notifier.getInstance().notify(new BombFlaggedEvent(gameHandler.isFlagged(yCell, xCell)));
                }
                repaint();

                if(gameHandler.isCompleted()) {
                    Notifier.getInstance().notify(new GameFinishEvent(true));
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                //do nothing
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //do nothing
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //do nothing
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //do nothing
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
                g.drawString(gameHandler.getChar(i, j), j * squareSize + 3, (i + 1) * squareSize - 2);
            }
        }
    }

    @Override
    public void notify(IGameEvent gameEvent) {
        if(gameEvent instanceof GameFinishEvent){
            active = false;
        }
    }
}
