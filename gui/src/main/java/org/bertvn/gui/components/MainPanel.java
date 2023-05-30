package org.bertvn.gui.components;

import org.bertvn.business.GameHandler;
import org.bertvn.gui.events.Notifier;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    private TopBar topBar;
    private BorderPainter borderPainter;
    private GamePanel gamePanel;

    public MainPanel() {
        initializeComponents();
        addListeners();
        placeComponents();

        repaint();
    }

    private void initializeComponents() {
        topBar = new TopBar();
        topBar.setSize(5, 48);
        topBar.setPreferredSize(new Dimension(5, 48));
        gamePanel = new GamePanel();
        gamePanel.setGameHandler(new GameHandler());

        borderPainter = new BorderPainter();
    }

    private void addListeners() {
        Notifier notifier = Notifier.getInstance();
        notifier.subscribe(gamePanel);
    }

    private void placeComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.0;
        constraints.insets = new Insets(BorderPainter.BORDER_WIDTH, BorderPainter.BORDER_WIDTH + 5, 5, BorderPainter.BORDER_WIDTH + 5);
        add(topBar, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = 3;
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.insets = new Insets(BorderPainter.BORDER_WIDTH-5, BorderPainter.BORDER_WIDTH, BorderPainter.BORDER_WIDTH, BorderPainter.BORDER_WIDTH);
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        add(gamePanel, constraints);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension size = getSize();
        borderPainter.drawBorderLine(g, BorderPainter.Direction.VERTICAL, 0, 0, size.height);
        borderPainter.drawBorderLine(g, BorderPainter.Direction.VERTICAL, size.width - BorderPainter.BORDER_WIDTH, 0, size.height);

        borderPainter.drawBorderLine(g, BorderPainter.Direction.HORIZONTAL, 13, 0, size.width - 26);
        borderPainter.drawBorderLine(g, BorderPainter.Direction.HORIZONTAL, 13, 48 + BorderPainter.BORDER_WIDTH, size.width - 26);
        borderPainter.drawBorderLine(g, BorderPainter.Direction.HORIZONTAL, 13, size.height - BorderPainter.BORDER_WIDTH, size.width - 26);

        borderPainter.drawTopLeftCorner(g, 0, 0);
        borderPainter.drawTopRightCorner(g, size.width - BorderPainter.BORDER_WIDTH, 0);
        borderPainter.drawBottomLeftCorner(g, 0, size.height - BorderPainter.BORDER_WIDTH);
        borderPainter.drawBottomRightCorner(g, size.width - BorderPainter.BORDER_WIDTH, size.height - BorderPainter.BORDER_WIDTH);
    }
}
