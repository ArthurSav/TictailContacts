package io.c0nnector.github.tictailcontacts.views.color_picker;

import android.support.annotation.ColorInt;

/**
 * Color picker colors
 */
public final class Colors {


    @ColorInt
    public static final int RED = 0xFFF44336;

    @ColorInt
    public static final int PINK = 0xFFFF4081;

    @ColorInt
    public static final int PURPLE = 0xFF9C27B0;

    @ColorInt
    public static final int CYAN = 0xFF00BCD4;

    @ColorInt
    public static final int BLUE = 0xFF03A9F4;

    @ColorInt
    public static final int AMBER = 0xFFFFC107;

    @ColorInt
    public static final int LIME = 0xFFCDDC39;

    @ColorInt
    public static final int DEEP_ORANGE = 0xFFFF5722;

    @ColorInt
    public static final int BLUE_GRAY = 0xFF607D8B;

    @ColorInt
    public static final int TEAL = 0xFF009688;


    @ColorInt
    public static int[] asList() {

        return new int[]{
                RED,
                PINK,
                PURPLE,
                CYAN,
                BLUE,
                AMBER,
                LIME,
                DEEP_ORANGE,
                BLUE_GRAY,
                TEAL
        };
    }
}
