package io.c0nnector.github.tictailcontacts.ui.contacts;


import io.c0nnector.github.least.Binder;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.util.UtilColor;
import io.c0nnector.github.tictailcontacts.util.UtilView;

/**
 * List view binder for contacts
 */
public class ContactBinder extends Binder<ContactViewHolder, Contact> {


    public ContactBinder(Class<Contact> contactClass, Class<ContactViewHolder> cls, int layoutId) {
        super(contactClass, cls, layoutId);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, Contact contact, int i) {

        //user avatar
        contactViewHolder.imgAvatar.loadContact(contact);
        UtilView.setShapeColor(contactViewHolder.imgAvatar, contact.getColorInt());

        //first name
        contactViewHolder.txtName.setText(contact.getFirst_name());

    }


    public static ContactBinder instance(){
        return new ContactBinder(Contact.class, ContactViewHolder.class, R.layout.list_item_contact);
    }
}
