package io.c0nnector.github.tictailcontacts.ui.add_contact;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Window;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.Bind;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.ApiService;
import io.c0nnector.github.tictailcontacts.api.RetroSubscriber;
import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.misc.Dagger;
import io.c0nnector.github.tictailcontacts.ui.BaseActivity;
import io.c0nnector.github.tictailcontacts.ui.contact.OnDoneListener;
import io.c0nnector.github.tictailcontacts.ui.contact.ViewContactEdit;
import io.c0nnector.github.tictailcontacts.util.ActivityTransitionHelper;
import io.c0nnector.github.tictailcontacts.util.Intents;
import io.c0nnector.github.tictailcontacts.util.Message;
import io.c0nnector.github.tictailcontacts.util.UI;
import io.c0nnector.github.tictailcontacts.views.color_picker.Colors;
import retrofit.RetrofitError;
import rx.android.schedulers.AndroidSchedulers;

public class ActivityAddContact extends BaseActivity implements OnDoneListener {


    ViewContactEdit viewContactEdit;

    @Inject
    ApiService apiService;

    @Bind(R.id.frame)
    FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Dagger.inject(this);
    }

    @Override
    protected void initVariables() {
        super.initVariables();

        viewContactEdit = new ViewContactEdit(this, this);
        viewContactEdit.showPlusButton();
    }

    @Override
    protected void afterViews() {
        super.afterViews();

        Contact contact = new Contact();
        contact.setColor(Colors.BLUE); //default theme color

        viewContactEdit.bind(contact);

        frame.addView(viewContactEdit);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ActivityTransitionHelper.setDownUpEnterAnimation(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        ActivityTransitionHelper.setDownUpExitAnimation(this);
    }

    @Override
    public void onDoneClick(Contact tmpContact) {

        UI.hideKeyboard(this);

        viewContactEdit.formValidation(0).distinctUntilChanged()
                .takeUntil(isValid -> !isValid) //stop observing if the form is invalid
                .filter(isValid -> isValid) //add user only when the form is valid

                        //show loader
                .doOnNext(aBoolean -> viewContactEdit.showLoader("Adding contact..."))

                        //add user
                .flatMap(isValid -> apiService.addContact(viewContactEdit.getTmpContact().getAsMap()))
                .observeOn(AndroidSchedulers.mainThread())

                .doOnNext(contact -> {

                    Message.show(this, "Contact added");

                    viewContactEdit.hideLoader();

                    //close activity
                    supportFinishAfterTransition();
                })
                .doOnError(throwable -> viewContactEdit.hideLoader())

                .subscribe(new RetroSubscriber<Contact>() {
                    @Override
                    public void onRetrofitError(RetrofitError error) {
                        super.onRetrofitError(error);
                    }
                });
    }

    /*****************************************************
     * ---------------- * Intents * --------------------
     ****************************************************/

    public static void start(Activity activity) {

        Intents.with(activity, ActivityAddContact.class)
                .open();
    }
}
