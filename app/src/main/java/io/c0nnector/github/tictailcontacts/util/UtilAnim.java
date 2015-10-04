package io.c0nnector.github.tictailcontacts.util;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;

/**
 * Animation util
 */
public final class UtilAnim {


    /**
     * Reveal from the center of a view
     * @param viewRoot
     * @param color
     */
    public static void reveal(View viewRoot, int color){

        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = viewRoot.getTop();
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        viewRoot.setBackgroundColor(color);
        anim.start();
    }

    public static void animateBackgroundColorChange(View view, int currentColor, int nextColor){

        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(currentColor, nextColor);
        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(valueAnimator -> view.setBackgroundColor((Integer)valueAnimator.getAnimatedValue()));

        anim.setDuration(300);
        anim.start();
    }
}
