package io.c0nnector.github.tictailcontacts.views;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import io.c0nnector.github.tictailcontacts.R;

/**
 * Toolbar with back button
 */
public class ToolbarCancel extends Toolbar{


    public ToolbarCancel(Context context) {

        super(context);
    }

    public ToolbarCancel(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public ToolbarCancel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (!isInEditMode()) {
            setNavigationIcon(R.drawable.ic_close_white_24dp);
        }
    }
}
