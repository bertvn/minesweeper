package org.bertvn.gui.components;

import org.bertvn.business.GameHandler;
import org.bertvn.common.GameModes;
import org.bertvn.gui.components.labels.BombCounterLabel;
import org.bertvn.gui.components.labels.TimerLabel;
import org.bertvn.gui.events.*;

import javax.swing.*;
import java.awt.*;

public class MinesweeperFrame extends JFrame implements IObserver {

    private TimerLabel timer;
    private BombCounterLabel bombsLabel;
    private GamePanel gamePanel;
    private JButton smiley;
    private JMenuBar gameMenuBar;

    public MinesweeperFrame() {
        super("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 350);

        initializeComponents();
        addListeners();
        placeComponents();

        Notifier.getInstance().notify(new GameChangeEvent(GameModes.BEGINNER));
    }

    private void initializeComponents() {
        timer = new TimerLabel();
        smiley = new JButton(":D");
        bombsLabel = new BombCounterLabel();
        gamePanel = new GamePanel();
        gamePanel.setGameHandler(new GameHandler());

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
        notifier.subscribe(timer);
        notifier.subscribe(timer);
        notifier.subscribe(bombsLabel);
        notifier.subscribe(gamePanel);

        smiley.addActionListener(e -> Notifier.getInstance().notify(new GameResetEvent()));
    }

    private void placeComponents() {
        setJMenuBar(gameMenuBar);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(10, 20, 10, 10);
        add(timer, constraints);
        constraints.gridx = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        add(smiley, constraints);
        constraints.gridx = 2;
        constraints.insets = new Insets(10, 10, 10, 20);
        add(bombsLabel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        constraints.gridheight = 3;
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.insets = new Insets(0, 20, 20, 20);
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        add(gamePanel, constraints);
    }

    @Override
    public void notify(IGameEvent gameEvent) {
        if(gameEvent instanceof GameFinishEvent finishEvent) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, String.format("you %s", finishEvent.isWon() ? "won" : "lost")));
        }
    }
}
