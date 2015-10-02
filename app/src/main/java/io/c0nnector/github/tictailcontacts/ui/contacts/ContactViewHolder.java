package io.c0nnector.github.tictailcontacts.ui.contacts;

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import io.c0nnector.github.least.BaseViewHolder;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.views.UrlImageView;

/**
 * Contact view holder
 */
public class ContactViewHolder extends BaseViewHolder {

    @Bind(R.id.imgAvatar)
    UrlImageView imgAvatar;

    @Bind(R.id.txtName)
    TextView txtName;

    public ContactViewHolder(View itemView) {
        super(itemView);
    }
}
