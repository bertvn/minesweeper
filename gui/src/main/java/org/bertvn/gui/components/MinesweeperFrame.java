package org.bertvn.gui.components;

import org.bertvn.common.GameModes;
import org.bertvn.gui.events.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MinesweeperFrame extends JFrame implements IObserver {

    private JMenuBar gameMenuBar;
    private MainPanel mainPanel;

    public MinesweeperFrame() {
        super("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        try {
            BufferedImage iconImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("icon.png"));
            setIconImage(iconImage);
        }
        catch(IOException e) {
            // do nothing
        }

        initializeComponents();
        addListeners();
        placeComponents();

        Notifier.getInstance().notify(new GameChangeEvent(GameModes.BEGINNER));
    }

    private void initializeComponents() {
        mainPanel = new MainPanel();

        gameMenuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("game");
        for(GameModes gameMode : GameModes.values()) {
            JMenuItem menuItem = new JMenuItem(gameMode.name().toLowerCase());
            menuItem.addActionListener(e -> Notifier.getInstance().notify(new GameChangeEvent(gameMode)));
            gameMenu.add(menuItem);
        }
        gameMenuBar.add(gameMenu);
    }

    private void addListeners() {
        Notifier notifier = Notifier.getInstance();
        notifier.subscribe(this);
    }

    private void placeComponents() {
        setJMenuBar(gameMenuBar);
        this.add(mainPanel);
    }

    @Override
    public void notify(IGameEvent gameEvent) {
        if(gameEvent instanceof GameFinishEvent finishEvent) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, String.format("you %s", finishEvent.getState().name().toLowerCase())));
        }
        if(gameEvent instanceof GameChangeEvent gameChanger) {
            int hiddenBorder = 8;

            int borderWidth = 2 * BorderPainter.BORDER_WIDTH;
            int gamePanelWidth = gameChanger.getColumns() * GUIConstants.CELL_SIZE;
            int width = borderWidth + gamePanelWidth + hiddenBorder * 2;
            int gamePanelHeight = gameChanger.getRows() * GUIConstants.CELL_SIZE;
            int borderHeight = 3 * BorderPainter.BORDER_WIDTH;
            int topPanelHeight = 48;

            int height = borderHeight + gamePanelHeight + topPanelHeight + +hiddenBorder + 54;
            setSize(width, height);
        }
    }
}
