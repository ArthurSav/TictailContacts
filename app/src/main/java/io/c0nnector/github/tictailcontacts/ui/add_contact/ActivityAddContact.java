package io.c0nnector.github.tictailcontacts.ui.add_contact;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import butterknife.Bind;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.ui.BaseActivity;
import io.c0nnector.github.tictailcontacts.ui.contact.OnDoneListener;
import io.c0nnector.github.tictailcontacts.ui.contact.ViewContactEdit;
import io.c0nnector.github.tictailcontacts.util.Intents;
import io.c0nnector.github.tictailcontacts.util.Message;
import rx.functions.Action1;

public class ActivityAddContact extends BaseActivity implements OnDoneListener {

    ViewContactEdit viewContactEdit;

    @Bind(R.id.frame)
    FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

    }

    @Override
    protected void initVariables() {
        super.initVariables();

        viewContactEdit = new ViewContactEdit(this, this);
    }

    @Override
    protected void validate() {
        super.validate();

        viewContactEdit.bind(new Contact());
    }

    @Override
    protected void afterViews() {
        super.afterViews();

        frame.addView(viewContactEdit);
    }

    @Override
    public void onDoneClick(Contact tmpContact) {
        viewContactEdit.formValidation(0)
                .distinctUntilChanged()
                .subscribe(isValid -> Message.show(ActivityAddContact.this, isValid? "VALID": "INVALID"));
    }

    /*****************************************************
     * ---------------- * Intents * --------------------
     *
     *
     *
     ****************************************************/

    public static void start(Activity activity) {

        Intents.with(activity, ActivityAddContact.class)
                .open();
    }
}
