package io.c0nnector.github.tictailcontacts.ui.contact;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.util.Strings;
import io.c0nnector.github.tictailcontacts.views.BaseRelativeLayout;
import io.c0nnector.github.tictailcontacts.views.UrlImageView;
import rx.Observable;

/**
 * Contact view, edit user info
 */
public class ViewContactEdit extends BaseRelativeLayout {


    Resources resources;

    Observable<CharSequence> firstNameObservable;

    Observable<CharSequence> lastNameObservable;


    Observable<Boolean> creditCardObservable;


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

    @Bind(R.id.btnChange)
    FloatingActionButton btnDone;

    /**
     * Constructor
     *
     * @param context
     */
    public ViewContactEdit(Context context) {
        super(context);

        if (!isInEditMode()) {

            View v = inflate(getContext(), R.layout.content_activity_contact_edit, this);
            ButterKnife.bind(this, v);

            this.resources = getResources();

            setupForm();
        }
    }


    @SuppressLint("SetTextI18n")
    public void bind(Contact contact) {

        //avatar
        imgAvatar.loadContact(contact);

        txtFirst.setText(contact.getFirst_name());
        txtLast.setText(contact.getLast_name());

        txtTitle.setText(contact.getTitle());
        txtTeam.setText(contact.getTeam());

        //header color
        vHeader.setBackgroundColor(contact.getColorInt());
    }


    /**
     * Form Observable
     *
     * Emmits true/false when the form is valid or not
     */
    private void setupForm() {

        Observable.combineLatest(RxTextView.textChanges(txtFirst), RxTextView.textChanges(txtLast), (firstName, lastName) -> {

            boolean hasFirstName = validateText(firstName, resources.getString(R.string.error_form_contact_firstname_short), inputLayoutFirst);
            boolean hasLastName = validateText(lastName, resources.getString(R.string.error_form_contact_lastname_short), inputLayoutLast);

            return hasFirstName && hasLastName;
        })
                //emmit only when the state changes
                .distinctUntilChanged()

                .subscribe(aBoolean -> {
                    Toast.makeText(getContext(), aBoolean ? "PASS" : "FAIL", Toast.LENGTH_SHORT).show();
                });

    }

    /**
     * Validates text input.
     *
     * When empty shows an error
     * @param text input text
     * @param error error to show when empty
     * @param textInputLayout textInputLayout to be used for the error display
     * @return true if not empty
     */
    private boolean validateText(CharSequence text, String error, TextInputLayout textInputLayout){

        boolean hasText = Strings.isNotBlank(text);

        //first name
        if (!hasText) {
            textInputLayout.setError(error);
        }
        else textInputLayout.setError(null);

        return hasText;
    }
}
