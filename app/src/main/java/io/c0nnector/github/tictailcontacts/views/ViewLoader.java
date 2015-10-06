package io.c0nnector.github.tictailcontacts.views;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.c0nnector.github.tictailcontacts.R;

/**
 * View that shows a progress loader & a message
 */
public class ViewLoader extends RelativeLayout {

    @Bind(R.id.txtMessage)
    TextView txtMessage;

    public ViewLoader(Context context) {
        super(context);

        if (!isInEditMode()) {

            View v = inflate(getContext(), R.layout.layout_loading_contact, this);
            ButterKnife.bind(this, v);
        }
    }

    public ViewLoader setMessage(String text){
        txtMessage.setText(text);
        return this;
    }

}
