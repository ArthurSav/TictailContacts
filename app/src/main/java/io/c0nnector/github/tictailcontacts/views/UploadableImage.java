package io.c0nnector.github.tictailcontacts.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.c0nnector.github.tictailcontacts.util.IntentData;
import io.c0nnector.github.tictailcontacts.util.Message;
import io.c0nnector.github.tictailcontacts.util.Val;
import retrofit.mime.TypedFile;

/**
 * Imageview that handles upload to imgur
 */
public class UploadableImage extends UrlImageView {

    private UploadableImageListener listener;


    public UploadableImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getPicasso().cancelRequest(target);
    }

    public void openPickIntent(Activity activity, UploadableImageListener listener){
        this.listener = listener;
        IntentData.chooseFileIntent(activity);
    }

    /**
     * Call it in your activity's onActivityResult. Handles image pick
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onImageResult(int requestCode, int resultCode, Intent data){

        Uri returnUri;

        if (requestCode != IntentData.CODE_REQUEST_IMAGE) {
            return;
        }

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        returnUri = data.getData();

        String filePath = UtilIntentImage.getPath(getContext(), returnUri);

        //Safety check to prevent null pointer exception
        if (filePath == null || filePath.isEmpty()) {

            Message.show(getContext(), "Could not load image");

            return;
        }

        //let picasso load and resize the image
        getPicasso().load(new File(filePath))
                .resize(250, 250)
                .centerCrop()
                .into(target);
    }

    /**
     * Handles bitmap load from picasso
     */
    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

             File resizedFile = saveImageToFile(bitmap);

            if (Val.notNull(resizedFile)) listener.onImageFile(new TypedFile("image/jpeg", resizedFile));
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    /**
     * Save bitmap to local storage
     * @param bitmap
     * @return
     */
    private File saveImageToFile(Bitmap bitmap){

        File tmp;

        try {

            //try to save in cache dir
             tmp = File.createTempFile("tmp_"+System.currentTimeMillis(), ".png", getContext().getCacheDir());

        } catch (IOException e) {
            e.printStackTrace();

            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            tmp = new File(dir, System.currentTimeMillis()+ "_resize.png");
        }

        FileOutputStream fOut;

        try {

            fOut = new FileOutputStream(tmp);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

            fOut.flush();
            fOut.close();

            return tmp;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public interface UploadableImageListener {


        /**
         * Called after we get a file path of the resized image the user picked
         * @param typedFile
         */
        void onImageFile(TypedFile typedFile);
    }
}
