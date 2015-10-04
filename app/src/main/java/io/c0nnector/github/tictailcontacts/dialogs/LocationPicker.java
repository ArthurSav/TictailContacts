package io.c0nnector.github.tictailcontacts.dialogs;

import android.app.AlertDialog;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.util.Strings;
import io.c0nnector.github.tictailcontacts.util.Val;
import timber.log.Timber;


public final class LocationPicker {

    /**
     * Holds the selected position in the list
     */
    private int lastSelected = -1;

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
    public void show(LocationChangeListener locationListener) {

        //last selected list position
        lastSelected = lastSelected == -1? convertStringToTimezonePosition(contact.getLocation()): lastSelected;

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setSingleChoiceItems(timezoneIds, lastSelected, (dialog, which) -> {
                    lastSelected = which;
                })
                .setPositiveButton("OK", (dialog, which) -> {
                    if (Val.notNull(locationListener)) locationListener.onLocationChange(timezoneIds[lastSelected], lastSelected);
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


    /**
     * Called when the user changes his profile location
     */
    public interface LocationChangeListener {
        void onLocationChange(String location, int position);
    }
}
