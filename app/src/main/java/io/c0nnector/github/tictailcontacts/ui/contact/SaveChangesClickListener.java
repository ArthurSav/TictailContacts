package io.c0nnector.github.tictailcontacts.ui.contact;


import io.c0nnector.github.tictailcontacts.api.model.Contact;

public interface SaveChangesClickListener {

    /**
     * Called when the user clicks to save his profile changes
     */
    void onSaveChangesClick(Contact tmpContact);
}
