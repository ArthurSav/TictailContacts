package io.c0nnector.github.tictailcontacts.ui.contact;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.Bind;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.ApiService;
import io.c0nnector.github.tictailcontacts.api.RetroSubscriber;
import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.misc.Constants;
import io.c0nnector.github.tictailcontacts.misc.Dagger;
import io.c0nnector.github.tictailcontacts.util.Message;
import io.c0nnector.github.tictailcontacts.util.UI;
import io.c0nnector.github.tictailcontacts.views.BaseRelativeLayout;
import retrofit.RetrofitError;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Main contact view. Handles view states
 *
 * @see ViewContactInfo
 * @see ViewContactEdit
 */
public class ViewContact extends BaseRelativeLayout implements OnDoneListener,EditButtonListener {

    Activity activity;

    Contact contact;

    /**
     * Display mode view
     */
    ViewContactInfo viewContactDisplay;

    /**
     * Edit mode view
     */
    ViewContactEdit viewContactEdit;

    /**
     * Display mode, transition scene
     */
    Scene sceneDisplay;

    /**
     * Edit mode, transition scene
     */
    Scene sceneEdit;

    @Inject
    ApiService apiService;

    @Bind(R.id.frame)
    FrameLayout frameLayout;


    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public ViewContact(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {

            Dagger.inject(this);

            setupViews();
        }
    }

    private void setupViews(){

        //display
        viewContactDisplay = new ViewContactInfo(getContext(), this);

        //edit
        viewContactEdit = new ViewContactEdit(getContext(), this);
    }


    /**
     * Bind contact to view
     * @param contact
     */
    public void bind(Contact contact, Activity activity){
        this.contact = contact;
        this.activity = activity;

        showDisplayMode();
    }

    /*****************************************************
     * ---------------- * Listeners * --------------------
     *
     *
     *
     ****************************************************/

    @Override
    public void onEditClick() {
        showEditMode();
    }

    @Override
    public void onDoneClick(Contact tmpContact) {

        UI.hideKeyboard(activity);

        if (viewContactEdit.hasChangedInfo()) {

            //add tmp changes to the contact object
            this.contact = tmpContact;

            updateContact(contact);
        }

        showDisplayMode();
    }

    /*****************************************************
     * ---------------- * Scenes * --------------------
     *
     *
     *
     ****************************************************/


    //todo - support previous apis
    private void showEditMode(){

        viewContactEdit.bind(contact);
        sceneEdit = new Scene(frameLayout, viewContactEdit);

        TransitionManager.go(sceneEdit, getBoundsTransition());
    }

    private void showDisplayMode(){

        viewContactDisplay.bind(contact);
        sceneDisplay = new Scene(frameLayout, viewContactDisplay);

        TransitionManager.go(sceneDisplay, getBoundsTransition());
    }

    private Transition getBoundsTransition(){

        Transition transition = new ChangeBounds();
        transition.setDuration(500);
        transition.setInterpolator(new AccelerateDecelerateInterpolator());

        return transition;
    }

    /*****************************************************
     * ---------------- * Api * --------------------
     *
     *
     *
     ****************************************************/

    protected void updateContact(Contact contact){

        apiService.updateContact(contact.getId(), contact.getAsMap())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(contact1 -> Snackbar.make(this,"Profile Updated", Snackbar.LENGTH_SHORT).show())
                .doOnError(throwable -> showRetryMessage(contact))

                .subscribe(new RetroSubscriber<Contact>() {

                    @Override
                    public void onNetworkError(RetrofitError error) {
                        super.onNetworkError(error);
                        Message.show(getContext(), Constants.ERROR_NETWORK);
                    }
                });
    }

    /**
     * Snackbar message to retry updating the user after a failure
     * @param contact
     */
    private void showRetryMessage(Contact contact){
        Snackbar.make(ViewContact.this, "Could not update user", Snackbar.LENGTH_LONG)
                .setAction("RETRY", v -> {
                    updateContact(contact);
                }).show();
    }
}
