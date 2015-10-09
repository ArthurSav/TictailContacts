package io.c0nnector.github.tictailcontacts.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.Iterator;

import butterknife.ButterKnife;
import io.c0nnector.easyoverlay.RelativeOverlay;

/**
 * Base relative overlay view
 */
public class BaseRelativeOverlay extends RelativeOverlay {

    public BaseRelativeOverlay(Context context) {
        super(context);
    }

    public BaseRelativeOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //inject butterknife
        ButterKnife.bind(this);
    }


    public void showLoader(String message){
        addOverlay(new ViewLoader(getContext()).setMessage(message));
    }

    public void hideLoader(){
        removeOverlays();
    }
}
