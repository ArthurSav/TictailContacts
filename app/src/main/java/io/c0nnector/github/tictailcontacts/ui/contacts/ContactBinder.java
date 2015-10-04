package io.c0nnector.github.tictailcontacts.ui.contacts;


import io.c0nnector.github.least.Binder;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.model.Contact;

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

        //first name
        contactViewHolder.txtName.setText(contact.getFirst_name());
    }


    public static ContactBinder instance(){
        return new ContactBinder(Contact.class, ContactViewHolder.class, R.layout.list_item_contact);
    }
}
