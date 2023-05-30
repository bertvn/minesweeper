package org.bertvn.gui.components;

import java.awt.*;

public final class GUIConstants {


    public static final int CELL_SIZE = 24;
    static final Color LIGHT_BORDER = Color.WHITE;
    static final Color MIDDLE_BORDER = new Color(226,226,226);
    static final Color SECOND_MIDDLE_BORDER = new Color(163,163,163);

    static final Color LIGHT_TO_MAIN = new Color(250, 250, 250);
    static final Color INNER_CORNER_COLOR = new Color(207, 207, 207);
    static final Color OUTER_CORNER_COLOR = new Color(199, 199, 199);
    static final Color DARK_BORDER = new Color(128, 128, 128);
    static final Color DARK_TO_MAIN = new Color(134, 134, 134);
    static final Color MAIN_COLOR = new Color(198, 198, 198);

    private GUIConstants() {
        throw new IllegalStateException("utility class");
    }
}
