package io.c0nnector.github.tictailcontacts.ui.contact;

import android.content.Context;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import butterknife.Bind;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.views.BaseRelativeLayout;

/**
 * Main contact view. Handles view states
 *
 * @see ViewContactInfo
 * @see ViewContactEdit
 */
public class ViewContact extends BaseRelativeLayout implements SaveChangesListener,EditButtonListener{

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
    public void bind(Contact contact){
        this.contact = contact;

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
    public void onSaveChangesClick(Contact tmpContact) {

        //add tmp changes to the contact object
        this.contact = tmpContact;

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
}
