package org.bertvn.gui.components;

import org.bertvn.business.GameHandler;
import org.bertvn.gui.components.labels.BombCounterLabel;
import org.bertvn.gui.components.labels.TimerLabel;
import org.bertvn.gui.events.GameFinishEvent;
import org.bertvn.gui.events.IGameEvent;
import org.bertvn.gui.events.IObserver;
import org.bertvn.gui.events.Notifier;

import javax.swing.*;
import java.awt.*;

public class MinesweeperFrame extends JFrame implements IObserver {

    public MinesweeperFrame() {
        super("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 350);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        Notifier notifier = Notifier.getInstance();
        notifier.subscribe(this);
        TimerLabel timer = new TimerLabel();
        notifier.subscribe(timer);
        JButton smiley = new JButton(":D");
        BombCounterLabel bombsLabel = new BombCounterLabel();
        notifier.subscribe(bombsLabel);

        GamePanel gamePanel = new GamePanel();
        gamePanel.setGameHandler(new GameHandler());

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 20, 10, 10);
        mainPanel.add(timer, constraints);
        constraints.gridx = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(smiley, constraints);
        constraints.gridx = 2;
        constraints.insets = new Insets(10, 10, 10, 20);
        mainPanel.add(bombsLabel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        constraints.gridheight = 3;
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        mainPanel.add(gamePanel, constraints);

        add(mainPanel);
//        pack();
    }

    @Override
    public void notify(IGameEvent gameEvent) {
        if(gameEvent instanceof GameFinishEvent finishEvent) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, String.format("you %s", finishEvent.isWon() ? "won" : "lost")));
        }
    }
}
