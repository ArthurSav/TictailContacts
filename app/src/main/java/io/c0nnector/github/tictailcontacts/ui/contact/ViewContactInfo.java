package io.c0nnector.github.tictailcontacts.ui.contact;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.util.Strings;
import io.c0nnector.github.tictailcontacts.util.UtilView;
import io.c0nnector.github.tictailcontacts.views.UrlImageView;

/**
 * Contact view, display user info
 */
public class ViewContactInfo extends RelativeLayout {


    @Bind(R.id.txtName)
    TextView txtName;

    @Bind(R.id.txtTitle)
    TextView txtTitle;

    @Bind(R.id.txtTeam)
    TextView txtTeam;

    @Bind(R.id.txtLocation)
    TextView txtLocation;

    @Bind(R.id.vTeam)
    View vTeam;

    @Bind(R.id.vTitle)
    View vTitle;

    @Bind(R.id.vLocation)
    View vLocation;

    @Bind(R.id.imgAvatar)
    UrlImageView imgAvatar;

    @Bind(R.id.vHeader)
    View vHeader;

    @Bind(R.id.btnChange)
    FloatingActionButton btnOnEdit;



    /**
     * Constructor
     * @param context
     */
    public ViewContactInfo(Context context, OnClickListener onEditButtonListener) {
        super(context);

        if (!isInEditMode()) {
            View v = inflate(getContext(), R.layout.content_activity_contact, this);
            ButterKnife.bind(this, v);

            this.btnOnEdit.setOnClickListener(onEditButtonListener);
        }
    }


    /**
     * Bind tmpContact to show the info
     * @param contact
     */
    public void bind(Contact contact){

        //name
        txtName.setText(contact.getName());

        //avatar
        imgAvatar.loadContact(contact);

        //header color
        vHeader.setBackgroundColor(contact.getColorInt());

        //title
        validateInfo(contact.getTitle(), vTitle, txtTitle);

        //team
        validateInfo(contact.getTeam(), vTeam, txtTeam);

        //location
        validateInfo(contact.getLocation(), vLocation, txtLocation);
    }

    /**
     * Set text into a textview. Hide the view if it's empty
     *
     * @param info text to show
     * @param wrapper  view to hide if the info is empty
     * @param txtInfo textview where the info will be displayed
     */
    private void validateInfo(String info, View wrapper, TextView txtInfo){

        if (UtilView.show(wrapper, Strings.isNotBlank(info))){
            txtInfo.setText(info);
        }
    }

}
