package io.c0nnector.github.tictailcontacts.ui.contact;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.transition.TransitionManager;
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

    @Bind(R.id.vTeam)
    View vTeam;

    @Bind(R.id.vTitle)
    View vTitle;

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
    public ViewContactInfo(Context context) {
        super(context);

        if (!isInEditMode()) {
            View v = inflate(getContext(), R.layout.content_activity_contact, this);
            ButterKnife.bind(this, v);
        }
    }


    @SuppressLint("SetTextI18n")
    public void bind(Contact contact){

        //details
        txtName.setText(contact.getName());

        //avatar
        imgAvatar.loadContact(contact);

        //header color
        vHeader.setBackgroundColor(contact.getColorInt());

        setTitleInfo(contact.getTitle());

        setTeamInfo(contact.getTeam());
    }

    /**
     * Sets user title info. Hides view if empty
     * @param title
     */
    private void setTitleInfo(String title){

        if (UtilView.show(vTitle, Strings.isNotBlank(title))){
            txtTitle.setText(title);
        }
    }

    /**
     * Sets user team info. Hides view if empty
     * @param team
     */
    private void setTeamInfo(String team){

        if (UtilView.show(vTeam, Strings.isNotBlank(team))){
            txtTeam.setText(team);
        }
    }
}
