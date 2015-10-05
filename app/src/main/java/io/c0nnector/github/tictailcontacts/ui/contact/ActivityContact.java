package io.c0nnector.github.tictailcontacts.ui.contact;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.ImageView;

import com.f2prateek.dart.InjectExtra;

import butterknife.Bind;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.ui.BaseActivity;
import io.c0nnector.github.tictailcontacts.util.Intents;
import io.c0nnector.github.tictailcontacts.util.Val;

public class ActivityContact extends BaseActivity {

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

            vContact.bind(contact);
        }
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
     * @param imgAvatar image to animate across activities
     */
    public static void start(Activity activity, Contact contact, ImageView imgAvatar) {

        String imageTransition = activity.getResources().getString(R.string.transitionImage);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imgAvatar, imageTransition);

        Intents.with(activity, ActivityContact.class)
                .extraParcel("contact", contact)
                .open(options.toBundle());
    }

}
