package io.c0nnector.github.tictailcontacts.misc;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import dagger.ObjectGraph;

/**
 * Dagger injection helper
 */
public final class Dagger {

    private static final String SERVICE_NAME = "application.tictailcontacts.dagger";

    private Dagger() {
    }

    @SuppressWarnings("ResourceType")
    public static ObjectGraph obtain(Context context) {
        return (ObjectGraph) context.getSystemService(SERVICE_NAME);
    }

    public static boolean matchesService(String name) {
        return SERVICE_NAME.equals(name);
    }

    /**
     * Inject activity
     * @param activity
     */
    public static void inject(Activity activity){
        obtain(activity.getApplicationContext()).inject(activity);
    }

    /**
     * Inject fragment
     * @param fragment
     */
    public static void inject(Fragment fragment){
        obtain(fragment.getContext()).inject(fragment);
    }

    /**
     * Inject view
     * @param view
     */
    public static void inject(View view){
        obtain(view.getContext()).inject(view);
    }
}
