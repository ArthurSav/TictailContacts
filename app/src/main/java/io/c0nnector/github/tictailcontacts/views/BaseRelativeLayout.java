package io.c0nnector.github.tictailcontacts.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;

/**
 * Base relativeLayout that injects it's views with butterknife
 */
public class BaseRelativeLayout extends RelativeLayout {

    public BaseRelativeLayout(Context context) {
        super(context);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //inject butterknife
        ButterKnife.bind(this);
    }
}
