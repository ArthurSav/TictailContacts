package io.c0nnector.github.tictailcontacts.ui.contact;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.ImageView;

import com.f2prateek.dart.InjectExtra;

import org.parceler.Parcels;

import butterknife.Bind;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.ui.BaseActivity;
import io.c0nnector.github.tictailcontacts.ui.contacts.ViewContacts;
import io.c0nnector.github.tictailcontacts.util.Intents;
import io.c0nnector.github.tictailcontacts.util.Val;

public class ActivityContact extends BaseActivity {

    @Nullable
    @InjectExtra
    Integer position;

    @InjectExtra
    Contact contact;

    @Bind(R.id.vContact)
    ViewContact vContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact);
    }


    @Override
    protected void validate() {
        super.validate();

        if (Val.notNull(contact)) {

            vContact.bind(contact, this);
        }
    }


    @Override
    public void supportFinishAfterTransition() {

        //return contact
        setResult();

        super.supportFinishAfterTransition();
    }

    /**
     * Set contact as intent result
     */
    private void setResult() {

        //updated contact
        Contact contact = vContact.contact;

        Bundle bundle = new Bundle();

        bundle.putParcelable("contact", Parcels.wrap(contact));
        if (Val.notNull(position)) bundle.putInt("position", position);

        Intents.setResult(this, ViewContacts.RESULT_CONTACT, bundle);
    }

    /*****************************************************
     * ---------------- * Intents * --------------------
     ****************************************************/

    public static void start(Activity activity, Contact contact) {
        Intents.with(activity, ActivityContact.class)
                .extraParcel("contact", contact)
                .open();
    }

    /**
     * Activity transition
     *
     * @param activity
     * @param contact   contact to display
     * @param position  position in the list
     * @param imgAvatar image to animate across activities
     */
    public static void start(Activity activity, Contact contact, int position, ImageView imgAvatar) {

        String imageTransition = activity.getResources().getString(R.string.transitionImage);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imgAvatar, imageTransition);

        Intents.with(activity, ActivityContact.class)
                .extraParcel("contact", contact)
                .extra("position", position)

                        //startForResult
                .openWithResult(activity, options.toBundle());
    }

}
