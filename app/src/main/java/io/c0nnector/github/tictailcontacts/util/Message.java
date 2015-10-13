package io.c0nnector.github.tictailcontacts.util;


import android.content.Context;
import android.widget.Toast;

/**
 * Toast wrapper
 */
public final class Message {


    public static void show(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
