package io.c0nnector.github.tictailcontacts.util;


import android.graphics.Color;

/**
 * Color parsing
 */
public final class UtilColor {

    /**
     * Converts a hex color string, to int
     * @param color
     * @return
     */
    public static int convert(String color){

        int parsedColor = Color.WHITE;

        if (Strings.isNotBlank(color)) {
            parsedColor = Color.parseColor(color.contains("#")? color: "#"+color);
        }

        return parsedColor;
    }

    /**
     * Converts an int color to hex string
     * @param color
     * @return
     */
    public static String convert(int color){
        return String.format("#%06X", (0xFFFFFF & color));
    }
}
