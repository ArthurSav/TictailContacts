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

        int parsedColor = Color.BLACK;

        if (Strings.isNotBlank(color)) {
            parsedColor = Color.parseColor(color.contains("#")? color: "#"+color);
        }

        return parsedColor;
    }
}
