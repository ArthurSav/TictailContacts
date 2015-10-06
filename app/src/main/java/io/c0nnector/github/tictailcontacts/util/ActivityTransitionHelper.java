package io.c0nnector.github.tictailcontacts.util;

import android.annotation.SuppressLint;
import android.app.Activity;

import io.c0nnector.github.tictailcontacts.R;


/**
 * Activity transition animations helper
 */
@SuppressLint("PrivateResource")
public class ActivityTransitionHelper {


    /*****************************************************
     * ---------------- * Down up animations * --------------------
     *
     *
     *
     ****************************************************/

    public static void setDownUpEnterAnimation(Activity activity){
        activity. overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_fade_out);
    }

    public static void setDownUpExitAnimation(Activity activity){
        activity. overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_bottom);
    }

    /*****************************************************
     * ---------------- * Fade animations * --------------------
     *
     *
     *
     ****************************************************/

    public static void setFadeEnterAnimation(Activity activity){
        activity. overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
    }

    public static void setFadeExit(Activity activity){
        activity. overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
    }

}
