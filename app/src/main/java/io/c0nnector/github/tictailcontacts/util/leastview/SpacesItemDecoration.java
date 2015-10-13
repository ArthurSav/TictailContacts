package io.c0nnector.github.tictailcontacts.util.leastview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.c0nnector.github.tictailcontacts.util.Measure;

/**
 * Adds spacing to recyclerview items
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {


    private int space;

    /**
     * @param spaceInDp dp space to apply
     */
    public SpacesItemDecoration(int spaceInDp) {

        //convert dp to px
        this.space = Measure.dpToPx(spaceInDp);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0)
            outRect.top = space;
    }
}