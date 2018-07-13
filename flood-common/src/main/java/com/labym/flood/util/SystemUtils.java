package com.labym.flood.util;

import java.awt.*;

public class SystemUtils {
    public static String[] fontNames() {
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return e.getAvailableFontFamilyNames();
    }
}
