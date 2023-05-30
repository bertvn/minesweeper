package org.bertvn.gui.components;

import org.bertvn.gui.components.labels.BombCounterLabel;
import org.bertvn.gui.components.labels.TimerLabel;
import org.bertvn.gui.events.Notifier;

import javax.swing.*;
import java.awt.*;

public class TopBar extends JPanel {

    private BombCounterLabel bombCounterLabel;
    private SmileyPanel smileyPanel;
    private TimerLabel timerLabel;
    private Box firstFiller;
    private Box secondFiller;

    public TopBar() {
        initializeComponents();
        addListeners();
        placeComponents();

        setBackground(new Color(192,192,192));
    }

    private void initializeComponents() {
        bombCounterLabel = new BombCounterLabel();
        firstFiller = Box.createHorizontalBox();
        smileyPanel = new SmileyPanel();
        secondFiller = Box.createHorizontalBox();
        timerLabel = new TimerLabel();
    }

    private void addListeners() {
        Notifier notifier = Notifier.getInstance();
        notifier.subscribe(bombCounterLabel);
        notifier.subscribe(smileyPanel);
        notifier.subscribe(timerLabel);
    }

    private void placeComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridx = 0;
        constraints.weightx = 0.0;
        constraints.weighty = 1.0;
        add(bombCounterLabel, constraints);

        constraints.gridx = 1;
        constraints.weightx = 1.0;
        add(firstFiller, constraints);

        constraints.gridx = 2;
        constraints.weightx = 0.0;
        add(smileyPanel, constraints);

        constraints.gridx = 3;
        constraints.weightx = 1.0;
        add(secondFiller, constraints);

        constraints.gridx = 4;
        constraints.weightx = 0.0;
        add(timerLabel, constraints);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Dimension size = getSize();
        g.fillRect(0, 0, size.width, size.height);
        super.paintComponent(g);
    }
}
