package io.c0nnector.github.tictailcontacts.ui.contact;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.io.File;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.c0nnector.easyoverlay.RelativeOverlay;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.imgur.ImageResponse;
import io.c0nnector.github.tictailcontacts.api.imgur.ImgurService;
import io.c0nnector.github.tictailcontacts.api.imgur.UploadedImage;
import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.dialogs.LocationPicker;
import io.c0nnector.github.tictailcontacts.misc.Dagger;
import io.c0nnector.github.tictailcontacts.util.IntentData;
import io.c0nnector.github.tictailcontacts.util.Message;
import io.c0nnector.github.tictailcontacts.util.Strings;
import io.c0nnector.github.tictailcontacts.util.UtilAnim;
import io.c0nnector.github.tictailcontacts.views.BaseRelativeOverlay;
import io.c0nnector.github.tictailcontacts.views.UploadableImage;
import io.c0nnector.github.tictailcontacts.views.UrlImageView;
import io.c0nnector.github.tictailcontacts.views.UtilIntentImage;
import io.c0nnector.github.tictailcontacts.views.ViewLoader;
import io.c0nnector.github.tictailcontacts.views.color_picker.ColorChangeListener;
import io.c0nnector.github.tictailcontacts.views.color_picker.ColorItem;
import io.c0nnector.github.tictailcontacts.views.color_picker.ColorPicker;
import retrofit.mime.TypedFile;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Contact view, edit user info.
 */
@SuppressLint("ViewConstructor")
public class ViewContactEdit extends BaseRelativeOverlay implements ColorChangeListener, LocationPicker.LocationChangeListener, UploadableImage.UploadableImageListener {

    Activity activity;

    LocationPicker locationPicker;

    /**
     * Stores tmp changes
     */
    Contact tmpContact = new Contact();

    Contact contact;

    Resources resources;

    Observable<Boolean> observableFirstName;

    Observable<Boolean> observableLastName;

    Observable<Boolean> observableLocation;

    @Inject
    ImgurService imgurService;

    @Bind(R.id.imgAvatar)
    UploadableImage imgAvatar;

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

    @OnClick(R.id.imgAvatar)
    public void onImageClick(){
        imgAvatar.openPickIntent(activity, this);
    }

    /**
     * Constructor
     *
     * @param context
     * @param listener save changes listener
     */
    public ViewContactEdit(Context context, OnDoneListener listener, Activity activity) {
        super(context);

        if (!isInEditMode()) {

            Dagger.inject(this);

            View v = inflate(getContext(), R.layout.content_contact_edit, this);
            ButterKnife.bind(this, v);

            this.activity = activity;
            this.resources = getResources();
            this.btnDone.setOnClickListener(v1 -> listener.onDoneClick(tmpContact));

            setupForm();
        }
    }


    /**
     * Bind contact to view
     *
     * @param contact
     */
    public void bind(Contact contact) {

        this.contact = contact;
        this.tmpContact = contact.clone();
        this.locationPicker = new LocationPicker(getContext(), contact);

        imgAvatar.loadContact(tmpContact);

        txtFirst.setText(tmpContact.getFirst_name());
        txtLast.setText(tmpContact.getLast_name());

        txtTitle.setText(tmpContact.getTitle());
        txtTeam.setText(tmpContact.getTeam());
        txtLocation.setText(tmpContact.getLocation());

        setupColorPicker(tmpContact);
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
     * Form is valid when:
     * - Fist name & last name are not empty
     * - Location is defined
     */
    private void setupForm() {

        formValidation(2).subscribe(isValid -> {});

        observeLocationField();

        observeNonValidatedTextChanges();
    }

    /**
     * Form validation for text input
     *
     * @param skipCount number of times to skip before we start validating. Use it to avoid errors showing upon init
     * @return Observable with form validation result
     */
    public Observable<Boolean> formValidation(int skipCount) {

        //first name
         observableFirstName = RxTextView.textChanges(txtFirst)
                 .skip(skipCount)
                .map(charSequence -> onFirstNameChange(charSequence.toString()))
                .map(firstName -> validateFormInput(firstName, resources.getString(R.string.error_form_firstname), inputLayoutFirst))
;
        //last name
         observableLastName = RxTextView.textChanges(txtLast)
                 .skip(skipCount)
                 .map(charSequence -> onLastNameChange(charSequence.toString()))
                 .map(lastName -> validateFormInput(lastName, resources.getString(R.string.error_form_lastname), inputLayoutLast))
;
        //location
         observableLocation = RxTextView.textChanges(txtLocation)
                 .skip(skipCount)
                 .map(location -> validateFormInput(location, resources.getString(R.string.error_form_location), inputLayoutLocation))
;
        return Observable.combineLatest(observableFirstName, observableLastName, observableLocation,
                (hasFirstName, hasLastName, hasLocation) -> hasFirstName && hasLastName && hasLocation);
    }


    /**
     * Observe click & touch events for the location field
     */
    private void observeLocationField() {

        //Invoke location picker when the field is focused/clicked
        Observable<Boolean> locationClickObservable = RxView.clickEvents(txtLocation)
                .map(viewClickEvent -> true);

        Observable.merge(locationClickObservable, RxView.focusChanges(txtLocation))
                .filter(aBoolean -> aBoolean)
                .subscribe(aBoolean -> locationPicker.show(this));
    }

    /**
     * Text change we don't want to validate
     */
    private void observeNonValidatedTextChanges() {

        //team
        RxTextView.textChanges(txtTeam).subscribe(team -> onTeamChange(team.toString()));

        //title
        RxTextView.textChanges(txtTitle).subscribe(title -> onTitleChange(title.toString()));
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
     *
     * @param contact
     */
    private void setupColorPicker(Contact contact) {

        int selectColor = colorPicker.selectColor(contact.getColorInt());
        colorPicker.setColorChangeListener(this);

        setHeaderColor(selectColor);
    }

    /**
     * Header background color
     *
     * @param color
     */
    private void setHeaderColor(int color) {

        //header color
        setBackgroundColor(color);
    }

    /*****************************************************
     * ---------------- * Changes * --------------------
     *
     *
     *
     ****************************************************/

    /**
     * @return true if there are modifications to the user data
     */
    public boolean hasChangedInfo() {
        return !contact.equals(tmpContact);
    }

    public String onFirstNameChange(String firstName){
        tmpContact.setFirst_name(firstName);

        return firstName;
    }

    public String onLastNameChange(String lastName){
        tmpContact.setLast_name(lastName);

        return lastName;
    }

    public void onTitleChange(String title) {
        tmpContact.setTitle(title);
    }

    public void onTeamChange(String team) {
        tmpContact.setTeam(team);
    }

    /**
     * User location changed
     *
     * @param location new location selected
     */
    @Override
    public String onLocationChange(String location) {
        this.txtLocation.setText(location);

        //save tmp location
        tmpContact.setLocation(location);

        return location;
    }

    /**
     * Theme color change event
     *
     * @param currentItem  current color item
     * @param previousItem previously selected color item
     * @param position     current item position in the list
     */
    @Override
    public void onColorChange(ColorItem currentItem, ColorItem previousItem, int position) {
        UtilAnim.animateBackgroundColorChange(this, previousItem.getColor(), currentItem.getColor());

        //save tmp color
        tmpContact.setColor(currentItem.getColor());
    }

    /**
     * User changed his image
     * @param imageResponse
     */
    private void onImageChanged(ImageResponse imageResponse){

        String url = imageResponse.getData().link;

        if (Strings.isNotBlank(url)) {

            tmpContact.setImage(url);
            imgAvatar.loadContact(tmpContact);
        }
    }


    /*****************************************************
     * ---------------- * Image * --------------------
     *
     *
     *
     ****************************************************/

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        imgAvatar.onImageResult(requestCode, resultCode, data);
    }

    @Override
    public void onImageFile(TypedFile typedFile) {

        imgurService.postImage(typedFile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnRequest(aLong -> showLoader(""))
                .subscribe(new Subscriber<ImageResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoader();
                        Message.show(getContext(), "Hm. Check your connection");
                    }

                    @Override
                    public void onNext(ImageResponse imageResponse) {
                        hideLoader();
                        onImageChanged(imageResponse);
                    }
                });
    }


    /*****************************************************
     * ---------------- * Getters * --------------------
     *
     *
     *
     ****************************************************/

    public Contact getTmpContact(){
        return tmpContact;
    }

    /*****************************************************
     * ---------------- * Other * --------------------
     *
     *
     *
     ****************************************************/

    /**
     * Converts the fab into a 'plus' button
     */
    public void showPlusButton(){
        btnDone.setImageResource(R.drawable.ic_add_white_24dp);
        btnDone.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
    }

}
