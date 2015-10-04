package io.c0nnector.github.tictailcontacts.views.color_picker;

import android.view.View;
import android.widget.RelativeLayout;

import butterknife.Bind;
import io.c0nnector.github.least.BaseViewHolder;
import io.c0nnector.github.tictailcontacts.R;


public class ColorViewHolder extends BaseViewHolder {

    @Bind(R.id.vColorBox)
    RelativeLayout vColorBox;

    @Bind(R.id.vDot)
    RelativeLayout vDot;

    public ColorViewHolder(View itemView) {
        super(itemView);
    }
}
