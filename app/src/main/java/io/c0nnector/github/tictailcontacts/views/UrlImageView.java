package io.c0nnector.github.tictailcontacts.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.misc.Constants;
import io.c0nnector.github.tictailcontacts.misc.Dagger;
import io.c0nnector.github.tictailcontacts.util.Measure;
import io.c0nnector.github.tictailcontacts.util.Strings;
import io.c0nnector.github.tictailcontacts.util.picasso.CircleTransform;


/**
 * Loads image from urls with picasso
 */
public class UrlImageView extends ImageView {

    private ColorDrawable defaultBackground;

    @Inject
    Picasso picasso;


    public UrlImageView(Context context) {
        super(context);

        init();
    }

    public UrlImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public UrlImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }


    private void init() {

        if (!isInEditMode()) {

            //default image background
            this.defaultBackground = new ColorDrawable(Color.LTGRAY);

            //inject dagger
            Dagger.inject(this);

            setAdjustViewBounds(true);
        }

    }

    public void loadDefault(String url) {

        picasso.load(getFilteredUrl(url))
                .placeholder(defaultBackground)
                .into(this);
    }



    public Picasso getPicasso() {
        return picasso;
    }

    /**
     * To avoid exceptions when the url is empty
     * @param url
     */
    private String getFilteredUrl(String url) {
        return Strings.isBlank(url) ? Constants.URL_DUMMY_PHOTO : url;
    }

    /*****************************************************
     * ---------------- * Contact * --------------------
     *
     *
     *
     ****************************************************/


    /**
     * Loads user avatar
     * @param contact contact object
     */
    public void loadContact(Contact contact){

        //image dimensions
        int dimens = Measure.dpToPx(Constants.DIMEN_IMAGE_CONTACT);

        picasso.load(getFilteredUrl(contact.getImage()))
                .resize(dimens, dimens)
                .transform(new CircleTransform())
                .into(this);
    }

}
