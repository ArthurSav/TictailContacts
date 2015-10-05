package io.c0nnector.github.tictailcontacts.views.color_picker;


import android.support.annotation.ColorInt;

public class ColorItem {

    int color;

    boolean selected;

    public ColorItem(@ColorInt int color, boolean selected) {
        this.color = color;
        this.selected = selected;
    }

    public int getColor() {
        return color;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
