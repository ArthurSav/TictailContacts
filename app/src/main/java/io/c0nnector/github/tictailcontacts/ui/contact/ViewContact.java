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
 * Contact view handler
 */
public class ViewContact extends BaseRelativeLayout {

    Contact contact;

    /**
     * View in display mode
     */
    ViewContactInfo viewContactDisplay;

    /**
     * View in edit mode
     */
    ViewContactEdit viewContactEdit;

    /**
     * Transition scene to show the 'display info' view
     */
    Scene sceneDisplay;

    /**
     * Transition scene to show the 'edit info' view
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


    /**
     * Bind tmpContact to view
     * @param contact
     */
    public void bind(Contact contact){
        this.contact = contact;

        showDisplayMode();
    }


    /**
     * Init info & edit edit view
     */
    private void setupViews(){

        //display
        viewContactDisplay = new ViewContactInfo(getContext(), onEditClickListener);

        //edit
        viewContactEdit = new ViewContactEdit(getContext(), onSaveChangesListener);
    }


    /**
     * OnClick listener for the 'edit' button
     */
    private OnClickListener onEditClickListener = v -> showEditMode();

    /**
     * Listener for the 'save changes' button
     */
    private SaveChangesClickListener onSaveChangesListener = tmpContact -> {

        //add tmp changes to the contact object
        this.contact = tmpContact;

        showDisplayMode();
    };



    //todo - support transitions for previous apis
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
