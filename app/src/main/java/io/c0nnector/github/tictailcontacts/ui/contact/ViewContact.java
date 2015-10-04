package io.c0nnector.github.tictailcontacts.ui.contact;

import android.content.Context;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.Bind;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.views.BaseRelativeLayout;
import rx.Observable;

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
     * Bind contact to view
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
        viewContactDisplay = new ViewContactInfo(getContext());

        //edit
        viewContactEdit = new ViewContactEdit(getContext());


        viewContactEdit.btnDone.setOnClickListener(onDoneClick);
        viewContactDisplay.btnOnEdit.setOnClickListener(onEditClick);
    }


    /**
     * OnClick listener for the 'edit' button
     */
    private OnClickListener onEditClick = v -> {
        showEditMode();
    };

    /**
     * OnClick listener for the 'done' button
     */
    private OnClickListener onDoneClick = v -> {
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
