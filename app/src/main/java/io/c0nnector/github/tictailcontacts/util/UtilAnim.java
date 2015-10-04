package io.c0nnector.github.tictailcontacts.util;

import android.animation.Animator;
import android.view.View;
import android.view.ViewAnimationUtils;

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
}
