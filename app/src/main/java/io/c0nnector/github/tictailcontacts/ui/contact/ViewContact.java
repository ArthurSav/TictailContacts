package io.c0nnector.github.tictailcontacts.ui.contact;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Scene;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;

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
import io.c0nnector.github.tictailcontacts.util.UtilView;
import io.c0nnector.github.tictailcontacts.views.BaseRelativeOverlay;
import retrofit.RetrofitError;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Main contact view. Handles view states
 *
 * @see ViewContactInfo
 * @see ViewContactEdit
 */
public class ViewContact extends BaseRelativeOverlay implements OnDoneListener,EditButtonListener {

    AppCompatActivity activity;

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
        }
    }


    /**
     * Bind contact to view
     * @param contact
     */
    public void bind(Contact contact, AppCompatActivity activity){
        this.contact = contact;
        this.activity = activity;

        setupViews();

        showDisplayMode();
    }

    private void setupViews(){

        //display
        viewContactDisplay = new ViewContactInfo(getContext(), this);

        //edit
        viewContactEdit = new ViewContactEdit(getContext(), this, activity);
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

    /**
     * Called when the user selects to delete the contact from options
     * @param contact
     */
    public void onContactDelete(Contact contact){

        //confirm
        new AlertDialog.Builder(getContext())
                .setMessage("Are you sure you want to delete this contact?")
                .setPositiveButton("YES", (dialog, which) -> deleteContact(contact))
                .setNegativeButton("CANCEL", (dialog, which) -> {})
                .show();
    }

    /*****************************************************
     * ---------------- * Scenes * --------------------
     *
     *
     *
     ****************************************************/

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

    public boolean isEditView(){
        return UtilView.containsView(frameLayout, ViewContactEdit.class);
    }

    private Transition getBoundsTransition(){

        Transition transition = new ChangeBounds();
        transition.setDuration(500);
        transition.setInterpolator(new AccelerateDecelerateInterpolator());

        return transition;
    }

    /**
     * Don't exit if in edit mode, return to display mode first
     * @return
     */
    public boolean exitOnBackPressed(){

        if (isEditView()) {

            showDisplayMode();

            return false;
        }

        return true;
    }

    /*****************************************************
     * ---------------- * Api * --------------------
     *
     *
     *
     ****************************************************/

    /**
     * Delete contact call
     * @param contact
     */
    private void deleteContact(Contact contact){

        apiService.deleteContact(contact.getId())
                .observeOn(AndroidSchedulers.mainThread())

                .doOnRequest(aLong -> showLoader("Deleting contact..."))

                .doOnNext(contact1 -> {

                    hideLoader();

                    Message.show(getContext(), "Contact Deleted");
                    activity.supportFinishAfterTransition();
                })
                .doOnError(throwable -> hideLoader())

                .subscribe(new RetroSubscriber<Contact>() {
                    @Override
                    public void onRetrofitError(RetrofitError error) {
                        super.onRetrofitError(error);

                        String message;

                        if (error.getKind() == RetrofitError.Kind.NETWORK)
                            message = "Network error";
                        else message = "Something went wrong";

                        Message.show(getContext(), message);
                    }
                });

    }

    /**
     * Update contact call
     * @param contact
     */
    private void updateContact(Contact contact){

        apiService.updateContact(contact.getId(), contact.getAsMap())
                .observeOn(AndroidSchedulers.mainThread())

                .doOnNext(contact1 -> Snackbar.make(this, "Profile Updated", Snackbar.LENGTH_SHORT).show())
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
     * Snackbar message to retry profile update
     * @param contact
     */
    private void showRetryMessage(Contact contact){
        Snackbar.make(ViewContact.this, "Could not update user", Snackbar.LENGTH_LONG)
                .setAction("RETRY", v -> updateContact(contact))
                .show();
    }
}
