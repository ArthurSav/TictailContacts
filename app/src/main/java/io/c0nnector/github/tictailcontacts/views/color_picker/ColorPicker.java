package io.c0nnector.github.tictailcontacts.views.color_picker;


import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import io.c0nnector.github.least.LeastAdapter;
import io.c0nnector.github.least.LeastView;
import io.c0nnector.github.least.ListItemListener;
import io.c0nnector.github.tictailcontacts.util.Val;
import io.c0nnector.github.tictailcontacts.util.leastview.GridSpacingItemDecoration;

/**
 * A list of colors in grid layout
 */
public class ColorPicker extends LeastView {

    private ColorChangeListener colorChangeListener;

    private int colors[] = Colors.asList();

    private LeastAdapter leastAdapter;

    /**
     * Constructor
     *
     * @param context
     * @param attrs
     */
    public ColorPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setupColorAdapter();
    }


    /*****************************************************
     * ---------------- * List * --------------------
     *
     *
     *
     ****************************************************/

    /**
     * Creates a color adapter & shows the colors in a grid format
     */
    private void setupColorAdapter(){

        ColorBinder binder = ColorBinder.instance();

         leastAdapter = new LeastAdapter.Builder()
                .binder(binder.setListItemClickListener(colorItemClickListener))
                .items(createColorItems())
                .build(getContext());

        //grid view
        setLayoutManager(new GridLayoutManager(getContext(), 5));

        //grid space
        addItemDecoration(new GridSpacingItemDecoration(5, 5, true));

        setAdapter(leastAdapter);
    }

    /**
     * Color list item click listener
     */
    ListItemListener<ColorViewHolder, ColorItem> colorItemClickListener = (colorViewHolder, colorItem, i) -> {

        //unselect previous
        ColorItem previousItem = getSelectedColorItem();
        previousItem.setSelected(false);

        //select current
        colorItem.setSelected(true);

        leastAdapter.notifyDataSetChanged();

        if (Val.notNull(colorChangeListener)) colorChangeListener.onColorChange(colorItem, previousItem, i);
    };

    /*****************************************************
     * ----------- * Getters/Setters * --------------------
     *
     *
     *
     ****************************************************/

    /**
     * Creates a list of color items to display
     * @return
     */
    private List<ColorItem> createColorItems(){

        List<ColorItem> colorItems = new ArrayList<>();

        for (int color : colors) {

            colorItems.add(new ColorItem(color, false));
        }

        return colorItems;
    }


    /**
     * @return selected color from the list
     */
    public ColorItem getSelectedColorItem(){

        return (ColorItem) leastAdapter.getItem(getSelectedPosition());
    }

    /**
     * @return selected position
     */
    private int getSelectedPosition(){

        List<Object> items = leastAdapter.getList();

        int size = items.size();

        for (int i = 0; i <size; i++) {

            ColorItem color = (ColorItem) items.get(i);
            if (color.isSelected()) return i;
        }

        //default
        return 0;
    }



    /**
     * Will replace a color from the list
     *
     * @param color color to replace with
     * @param position list position
     * @param isSelected if true will mark the placed color as selected
     */
    public void replaceColor(@ColorInt int color, int position, boolean isSelected){

        if (position <= leastAdapter.getItemPositions()) {
            leastAdapter.replace(new ColorItem(color, isSelected), position);
        }
    }

    /**
     * Will try to match a color from the list and select it.
     * If no color is found, the first position is replaced by the given color
     * @param color color to replace with
     * @return position in the list we replaced
     */
    public int selectColor(@ColorInt int color){

        //check if color already exists in the list
        for (int i = 0; i < colors.length; i++) {

            if (colors[i] == color) {

                replaceColor(color, i, true);

                return i;
            }
        }

        //color is invalid for some reason, return a default
        if (color == 0) {

            replaceColor(colors[0], 0, true);

            return colors[0];
        }

        //custom color
        replaceColor(color, 0, true);

        return color;
    }


    public void setColorChangeListener(ColorChangeListener listener){
        this.colorChangeListener = listener;
    }
}
