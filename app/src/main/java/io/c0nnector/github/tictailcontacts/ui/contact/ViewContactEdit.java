package io.c0nnector.github.tictailcontacts.ui.contact;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.dialogs.LocationPicker;
import io.c0nnector.github.tictailcontacts.util.Strings;
import io.c0nnector.github.tictailcontacts.util.UtilAnim;
import io.c0nnector.github.tictailcontacts.views.BaseRelativeLayout;
import io.c0nnector.github.tictailcontacts.views.UrlImageView;
import io.c0nnector.github.tictailcontacts.views.color_picker.ColorChangeListener;
import io.c0nnector.github.tictailcontacts.views.color_picker.ColorItem;
import io.c0nnector.github.tictailcontacts.views.color_picker.ColorPicker;
import rx.Observable;
import rx.functions.Action1;

/**
 * Contact view, edit user info.
 */
public class ViewContactEdit extends BaseRelativeLayout implements ColorChangeListener, LocationPicker.LocationChangeListener{

    LocationPicker locationPicker;

    SaveChangesClickListener saveChangesListener;

    /**
     * Stores tmp changes
     */
    Contact tmpContact = new Contact();

    Contact contact;

    Resources resources;


    @Bind(R.id.imgAvatar)
    UrlImageView imgAvatar;

    @Bind(R.id.vHeader)
    View vHeader;

    @Bind(R.id.layoutTxtFirst)
    TextInputLayout inputLayoutFirst;

    @Bind(R.id.txtFirst)
    EditText txtFirst;

    @Bind(R.id.layoutTxtLast)
    TextInputLayout inputLayoutLast;

    @Bind(R.id.txtLast)
    EditText txtLast;

    @Bind(R.id.txtEditTitle)
    EditText txtTitle;

    @Bind(R.id.txtEditTeam)
    EditText txtTeam;

    @Bind(R.id.layoutTxtEditLocation)
    TextInputLayout inputLayoutLocation;

    @Bind(R.id.txtEditLocation)
    EditText txtLocation;

    @Bind(R.id.vColorPicker)
    ColorPicker colorPicker;


    @Bind(R.id.btnChange)
    FloatingActionButton btnDone;


    @OnClick(R.id.btnChange)
    public void oSaveChangesClick(){
        saveChangesListener.onSaveChangesClick(tmpContact);
    }


    /**
     * Constructor
     *
     * @param context
     */
    public ViewContactEdit(Context context, SaveChangesClickListener listener) {
        super(context);

        if (!isInEditMode()) {

            View v = inflate(getContext(), R.layout.content_activity_contact_edit, this);
            ButterKnife.bind(this, v);

            this.resources = getResources();
            this.saveChangesListener = listener;

            setupForm();
        }
    }


    /**
     * Bind tmpContact to display the info
     *
     * @param contact
     */
    public void bind(Contact contact) {
        this.contact = contact;
        this.tmpContact = contact.clone();
        this.locationPicker = new LocationPicker(getContext(), contact);

        //avatar
        imgAvatar.loadContact(contact);

        txtFirst.setText(contact.getFirst_name());
        txtLast.setText(contact.getLast_name());

        txtTitle.setText(contact.getTitle());
        txtTeam.setText(contact.getTeam());
        txtLocation.setText(contact.getLocation());

        setHeaderColor(contact.getColorInt());

        setupColorPicker(contact);
    }


    /*****************************************************
     * ---------------- * Form * --------------------
     *
     *
     *
     ****************************************************/


    /**
     * Contact edit form. Handles form validation
     * <p>
     * Emmits true/false when the form is valid or not
     */
    private void setupForm() {

        //name
        Observable.combineLatest(RxTextView.textChanges(txtFirst), RxTextView.textChanges(txtLast), (firstName, lastName) -> {

            //check input
            boolean hasFirstName = validateFormInput(firstName, resources.getString(R.string.error_form_contact_firstname_short), inputLayoutFirst);
            boolean hasLastName = validateFormInput(lastName, resources.getString(R.string.error_form_contact_lastname_short), inputLayoutLast);

            onNameChange(firstName.toString(), lastName.toString());

            return hasFirstName && hasLastName;
        })
                //emmit only when the state changes
                .distinctUntilChanged()

                .subscribe(aBoolean -> {

                });

        //location
        Observable<Boolean> locationClickObservable = RxView.clickEvents(txtLocation)
                .map(viewClickEvent -> true);

        Observable.merge(locationClickObservable, RxView.focusChanges(txtLocation))
                .filter(aBoolean -> aBoolean)
                .subscribe(aBoolean -> {
                    locationPicker.show(this);
                });

        //team
        RxTextView.textChanges(txtTeam)
                .subscribe(team -> {
                    onTeamChange(team.toString());
                });

        //title
        RxTextView.textChanges(txtTitle)
                .subscribe(title -> {
                    onTitleChange(title.toString());
                });
    }

    /**
     * Validates form text input. When empty shows an error
     *
     * @param text            input text
     * @param error           message when empty
     * @param textInputLayout displays the error
     *
     * @return true if not empty
     */
    private boolean validateFormInput(CharSequence text, String error, TextInputLayout textInputLayout) {

        boolean hasText = Strings.isNotBlank(text);

        textInputLayout.setError(hasText ? null : error);

        return hasText;
    }


    /*****************************************************
     * ------------ * Theme color * --------------------
     *
     *
     *
     ****************************************************/

    /**
     * User theme color picker setup
     * @param contact
     */
    private void setupColorPicker(Contact contact){

        colorPicker.selectColor(contact.getColorInt());
        colorPicker.setColorChangeListener(this);
    }

    /**
     * Header background color
     * @param color
     */
    private void setHeaderColor(int color){

        //header color
        vHeader.setBackgroundColor(color);
    }

    /*****************************************************
     * ---------------- * Changes * --------------------
     *
     *
     *
     ****************************************************/

    public void onNameChange(String firstName, String lastName){

        //save changes to tmp contact
        tmpContact.setFirst_name(firstName);
        tmpContact.setLast_name(lastName);
    }

    public void onTitleChange(String title){
        tmpContact.setTitle(title);
    }

    public void onTeamChange(String team){
        tmpContact.setTeam(team);
    }

    /**
     * Called when the user changes his location
     * @param location
     * @param position
     */
    @Override
    public void onLocationChange(String location, int position) {
        this.txtLocation.setText(location);

        //save tmp location
        tmpContact.setLocation(location);
    }

    /**
     * Theme color change event
     * @param currentItem
     * @param previousItem
     * @param position
     */
    @Override
    public void onColorChange(ColorItem currentItem, ColorItem previousItem, int position) {
        UtilAnim.animateBackgroundColorChange(vHeader, previousItem.getColor(), currentItem.getColor());

        //save tmp color
        tmpContact.setColor(currentItem.getColor());
    }
}