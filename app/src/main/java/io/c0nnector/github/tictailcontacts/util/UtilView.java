package io.c0nnector.github.tictailcontacts.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import io.c0nnector.github.tictailcontacts.R;

/**
 * Util to handle views
 */
public final class UtilView {


    private UtilView() {
    }


    /**
     * Show a view
     * @param v
     */
    public static void show(View v){
        if (v!=null) v.setVisibility(View.VISIBLE);
    }


    /**
     * Show a view and return the state
     * @param v
     * @param show
     * @return
     */
    public static boolean show(View v, boolean show){

        if (show) show(v);

        else hide(v);

        return show;
    }

    /**
     * Makes a list of views visible
     * @param views
     */
    public static void show(View... views){
        for (View v: views){
             show(v);
        }
    }


    /**
     * Hides a list of views
     * @param views
     */
    public static void hide(View... views){
        for (View v: views){
             hide(v);
        }
    }

    /**
     * Hides a view
     * @param v
     */
    public static void hide(View v){
        if (v!=null) v.setVisibility(View.GONE);
    }

    /**
     * Toggles the visibility of any given number of views
     * @param views
     */
    public static void toggleVisibility(View... views) {

        for (View view : views) {

            if (view !=null) {

                boolean isVisible = view.getVisibility() == View.VISIBLE;
                show(view, !isVisible);
            }
        }
    }

    /**
     * Makes a view invisible
     * @param v
     */
    public static void makeInvisible(View v){v.setVisibility(View.INVISIBLE);}

    /**
     * Makes a view invisible and returns it's state
     * @param v
     * @param show
     * @return
     */
    public static boolean makeInvisible(View v, boolean show){
        if (show) show(v);
        else makeInvisible(v);

        return show;
    }


    /**
     * @param v
     * @return true if a view is visible
     */
    public static boolean isVisible(View v){
        return  v.getVisibility() == View.VISIBLE;
    }

    /**
     * @param v
     * @return true, if a view is invisible
     */
    public static boolean isInvisible(View v) {
        return v.getVisibility() == View.INVISIBLE;
    }


    /**
     * Checks for a specific view class inside a group view
     * @param view
     * @param viewClass
     * @return
     */
    public static boolean containsView(ViewGroup view, Class viewClass) {

        int size = view.getChildCount();

        if(size > 0) {

            for (int i = 0; i < size; i++) {

                View v = view.getChildAt(i);

                if(viewClass.isInstance(v)) {
                    return true;
                }
            }
        }

        return false;
    }

    /*****************************************************
     * ---------------- * Textview * --------------------
     *
     *
     *
     ****************************************************/

    /**
     * Try to set text to a textview and return it's state
     * @param textView
     * @param text
     * @param set
     * @return
     */
    public static boolean setText(TextView textView, String text, boolean set){
        if (set) setText(textView, text);

        return set;
    }

    /**
     * Set text to a textview
     * @param textView
     * @param text
     */
    public static void setText(TextView textView, String text){
        if (textView !=null) textView.setText(text);
    }


    /**
     * Returns true if the textview is ellipsized
     * @param textView
     * @return
     */
    public static boolean isTextEllipsized(TextView textView){

        Layout textViewLayout = textView.getLayout();

        if (textViewLayout != null) {

            int lines = textViewLayout.getLineCount();

            if (lines > 0) {

                if (textViewLayout.getEllipsisCount(lines-1) > 0) return true;
            }
        }

        return false;
    }


    /*****************************************************
     * ---------------- * Drawable color * --------------------
     *
     *
     *
     ****************************************************/

    /**
     * Changes color to a view background drawable
     * @param view
     * @param color
     */
    public static void setShapeColor(View view, int color){

        Drawable background = view.getBackground();

        if (background instanceof ShapeDrawable) {

            ((ShapeDrawable)background).getPaint().setColor(color);
        }

        else if (background instanceof GradientDrawable) {
            ((GradientDrawable)background).setColor(color);
        }
    }
}
