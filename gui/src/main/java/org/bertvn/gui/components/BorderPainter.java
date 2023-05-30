package org.bertvn.gui.components;

import java.awt.*;

public class BorderPainter {

    public enum Direction {
        HORIZONTAL,
        VERTICAL,
    }

    public static final int BORDER_WIDTH = 18;

    public void drawBorderLine(Graphics g, Direction direction, int x, int y, int length) {
        switch (direction) {
            case HORIZONTAL -> drawHorizontalBorderLine(g, x, y, length);
            case VERTICAL -> drawVerticalBorderLine(g, x, y, length);
        }
    }

    private void drawHorizontalBorderLine(Graphics g, int x, int y, int length) {
        int yCoord = y;
        g.setColor(GUIConstants.LIGHT_BORDER);
        g.fillRect(x, yCoord, length, 4);
        yCoord += 4;
        g.setColor(GUIConstants.MIDDLE_BORDER);
        g.fillRect(x, yCoord, length, 1);
        g.setColor(GUIConstants.MAIN_COLOR);
        yCoord += 1;
        g.fillRect(x, yCoord, length, 8);
        yCoord += 8;
        g.setColor(GUIConstants.SECOND_MIDDLE_BORDER);
        g.fillRect(x, yCoord, length, 1);
        yCoord += 1;
        g.setColor(GUIConstants.DARK_BORDER);
        g.fillRect(x, yCoord, length, 4);
    }

    private void drawVerticalBorderLine(Graphics g, int x, int y, int length) {
        int xCoord = x;
        g.setColor(GUIConstants.LIGHT_BORDER);
        g.fillRect(xCoord, y, 4, length);
        xCoord += 4;
        g.setColor(GUIConstants.MIDDLE_BORDER);
        g.fillRect(xCoord, y, 1, length);
        g.setColor(GUIConstants.MAIN_COLOR);
        xCoord += 1;
        g.fillRect(xCoord, y, 8, length);
        xCoord += 8;
        g.setColor(GUIConstants.SECOND_MIDDLE_BORDER);
        g.fillRect(xCoord, y, 1, length);
        xCoord += 1;
        g.setColor(GUIConstants.DARK_BORDER);
        g.fillRect(xCoord, y, 4, length);
    }

    public void drawTopLeftCorner(Graphics g, int x, int y) {
        g.setColor(GUIConstants.LIGHT_BORDER);
        g.fillRect(x, y, BORDER_WIDTH, BORDER_WIDTH);

        g.setColor(new Color(226, 226, 226));
        g.fillRect(x + 4, y + 4, 14, 14);

        g.setColor(new Color(246, 246, 246));
        g.fillRect(x + 4, y + 4, 1, 1);


        g.setColor(new Color(198, 198, 198));
        g.fillRect(x + 5, y + 5, 13, 13);


        g.setColor(new Color(163, 163, 163));
        g.fillRect(x + 13, y + 13, 5, 5);

        g.setColor(new Color(176, 176, 176));
        g.fillRect(x + 13, y + 13, 1, 1);


        g.setColor(new Color(128, 128, 128));
        g.fillRect(x + 14, y + 14, 4, 4);
    }

    public void drawBottomLeftCorner(Graphics g, int x, int y) {

        g.setColor(GUIConstants.DARK_BORDER);
        g.fillRect(x + 2, y + BORDER_WIDTH - 1, 11, 1);
        g.fillRect(x + 3, y + BORDER_WIDTH - 3, 10, 2);
        g.fillRect(x + 5, y + BORDER_WIDTH - 4, 8, 1);

        g.setColor(GUIConstants.SECOND_MIDDLE_BORDER);
        g.fillRect(x + 5, y + BORDER_WIDTH - 5, 8, 1);

        g.setColor(new Color(195, 195, 195));
        g.fillRect(x, y + BORDER_WIDTH - 1, 1, 1);

        g.setColor(new Color(191, 191, 191));
        g.fillRect(x + 1, y + BORDER_WIDTH - 2, 1, 1);

        g.setColor(new Color(251, 251, 251));
        g.fillRect(x, y + BORDER_WIDTH - 2, 1, 1);

        g.setColor(new Color(134, 134, 134));
        g.fillRect(x + 1, y + BORDER_WIDTH - 1, 1, 1);
        g.fillRect(x + 2, y + BORDER_WIDTH - 2, 1, 1);

        g.setColor(new Color(187, 187, 187));
        g.fillRect(x + 2, y + BORDER_WIDTH - 3, 1, 1);
        g.fillRect(x + 3, y + BORDER_WIDTH - 4, 1, 1);

        g.setColor(new Color(247, 247, 247));
        g.fillRect(x + 1, y + BORDER_WIDTH - 3, 1, 1);

        g.setColor(new Color(130, 130, 130));
        g.fillRect(x + 4, y + BORDER_WIDTH - 4, 1, 1);

        g.setColor(new Color(184, 184, 184));
        g.fillRect(x + 4, y + BORDER_WIDTH - 5, 1, 1);

        g.setColor(new Color(250, 250, 250));
        g.fillRect(x + 3, y + BORDER_WIDTH - 5, 1, 1);
    }

    public void drawTopRightCorner(Graphics g, int x, int y) {
        g.setColor(GUIConstants.LIGHT_BORDER);
        g.fillRect(x, y, 16, 1);
        g.fillRect(x, y + 1, 15, 2);
        g.fillRect(x, y + 3, 14, 1);

        g.setColor(GUIConstants.MIDDLE_BORDER);
        g.fillRect(x, y + 4, 13, 1);

        g.setColor(new Color(186, 186, 186));
        g.fillRect(x + 17, y, 1, 1);

        g.setColor(new Color(189, 189, 189));
        g.fillRect(x + 16, y + 1, 1, 1);

        g.setColor(new Color(195, 195, 195));
        g.fillRect(x + 15, y + 2, 1, 1);

        g.setColor(new Color(193, 193, 193));
        g.fillRect(x + 14, y + 3, 1, 1);

        g.setColor(new Color(200, 200, 200));
        g.fillRect(x + 13, y + 4, 1, 1);

        g.setColor(new Color(134, 134, 134));
        g.fillRect(x + 14, y + 4, 1, 1);

        g.setColor(new Color(133, 133, 133));
        g.fillRect(x + 17, y + 1, 1, 1);

        g.setColor(new Color(135, 135, 135));
        g.fillRect(x + 16, y + 2, 1, 1);

        g.setColor(new Color(250, 250, 250));
        g.fillRect(x + 16, y, 1, 1);
        g.fillRect(x + 15, y + 1, 1, 1);

    }

    public void drawBottomRightCorner(Graphics g, int x, int y) {
        //first the 5 squares
        g.setColor(new Color(128, 128, 128));
        g.fillRect(x, y, BORDER_WIDTH, BORDER_WIDTH);

        g.setColor(new Color(163, 163, 163));
        g.fillRect(x, y, 14, 14);

        g.setColor(new Color(198, 198, 198));
        g.fillRect(x, y, 13, 13);

        g.setColor(new Color(226, 226, 226));
        g.fillRect(x, y, 5, 5);

        g.setColor(GUIConstants.LIGHT_BORDER);
        g.fillRect(x, y, 4, 4);

        //2 individual pixels
        g.setColor(new Color(217, 217, 217));
        g.fillRect(x + 4, y + 4, 1, 1);

        g.setColor(new Color(141, 141, 141));
        g.fillRect(x + 13, y + 13, 1, 1);
    }
}
