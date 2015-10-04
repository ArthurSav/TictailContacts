package io.c0nnector.github.tictailcontacts.views.color_picker;

import io.c0nnector.github.least.Binder;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.util.UtilView;

/**
 * List item color binder
 */
public class ColorBinder extends Binder<ColorViewHolder, ColorItem> {


    public ColorBinder(Class<ColorItem> colorItemClass, Class<ColorViewHolder> cls, int layoutId) {
        super(colorItemClass, cls, layoutId);
    }

    @Override
    public void onBindViewHolder(ColorViewHolder colorViewHolder, ColorItem colorItem, int i) {

        UtilView.setShapeColor(colorViewHolder.vColorBox, colorItem.getColor());
        UtilView.show(colorViewHolder.vDot, colorItem.isSelected());
    }

    public static ColorBinder instance(){
        return new ColorBinder(ColorItem.class, ColorViewHolder.class, R.layout.list_item_color);
    }
}
