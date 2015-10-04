package io.c0nnector.github.tictailcontacts.dialogs;

import android.app.AlertDialog;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.util.Strings;


public final class LocationPicker {

    /**
     * A list of timezone/location ids
     */
    String[] timezoneIds = TimeZone.getAvailableIDs();


    Context context;

    Contact contact;

    /**
     * Constructor
     * @param context
     * @param contact user associated with the location
     */
    public LocationPicker(Context context, Contact contact) {
        this.contact = contact;
        this.context = context;
    }

    /**
     * Shows a single choice alert dialog with locations
     * Current user location is preselected, if available
     */
    public void show() {

        int currentLocationPositionInTheList = convertStringToTimezonePosition(contact.getLocation());

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setSingleChoiceItems(timezoneIds, currentLocationPositionInTheList, (dialog, which) -> {

                })
                .setPositiveButton("OK", (dialog, which) -> {

                })
                .setNegativeButton("CANCEL", (dialog, which) -> {

                })
                .show();
    }

    /**
     * Will try to convert a string into a position in the timezone list
     * @param location user location as timezone
     * @return position in the list & location string
     */
    private int convertStringToTimezonePosition(String location){

        if (Strings.isNotBlank(location)) {

            for (int i = 0; i < timezoneIds.length; i++) {

                String listLocation = timezoneIds[i];

                if (listLocation.contentEquals(location)) {

                    return i;
                }
            }
        }

        //default position
        return 0;
    }

    /**
     * @param position timezone list position
     * @return
     */
    private String convertPositionToTimezoneString(int position){
        return position <= timezoneIds.length? timezoneIds[position] : "";
    }
}
