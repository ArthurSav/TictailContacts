package io.c0nnector.github.tictailcontacts.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Util class to measure stuff
 */
public final class Measure {

    private static int screenWidth;

    private static int screenHeight;


    private Measure(){
        //no instances
    }

    /*****************************************************
     * ---------------- * Pixels * --------------------
     *
     *
     *
     ****************************************************/

    /**
     * Converts dp to pixels
     *
     * @param dp
     * @return
     */
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    /*****************************************************
     * ---------------- * Screen * --------------------
     *
     *
     *
     ****************************************************/

    /**
     * Screen dimensions
     * @param context
     * @return
     */
    private static Point getScreenDimensions(Context context){

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size;
    }

    public static int getScreenHeight(Context c) {

        if (screenHeight == 0) {
            screenHeight = getScreenDimensions(c).y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {

        if (screenWidth == 0) {
            screenWidth = getScreenDimensions(c).x;
        }

        return screenWidth;
    }

}
