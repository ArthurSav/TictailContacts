package io.c0nnector.github.tictailcontacts.ui.contact;


import io.c0nnector.github.tictailcontacts.api.model.Contact;

public interface OnDoneListener {

    /**
     * /**
     * Called when the user clicks to save his profile changes
     * @param tmpContact contact used to saved tmp changes
     */
    void onDoneClick(Contact tmpContact);
}
