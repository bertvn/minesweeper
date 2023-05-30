package org.bertvn.gui.components.labels;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public abstract class NumberPainter extends JPanel {

    private final Font font;

    protected NumberPainter() {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("fonts/7-segfault.ttf");
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
        font = foundFont.deriveFont(Font.PLAIN, 12f);
        setSize(64, 40);
        setPreferredSize(new Dimension(70, 40));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 70, 40);
        g.setColor(new Color(64, 0, 0));
        g.setFont(font);
        g.drawString("888", 3, 40 - 3);
        g.setColor(Color.RED);
        g.drawString(getText(), 3, 40 - 3);
    }

    protected abstract String getText();
}
