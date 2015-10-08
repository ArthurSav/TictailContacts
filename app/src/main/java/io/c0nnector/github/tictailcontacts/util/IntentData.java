package io.c0nnector.github.tictailcontacts.util;

import android.app.Activity;
import android.content.Intent;

/**
 * Intents to get content
 */
public class IntentData {

    public final static int CODE_REQUEST_IMAGE = 101;

    public static void chooseFileIntent(Activity activity){

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, CODE_REQUEST_IMAGE);
    }
}
