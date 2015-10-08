package io.c0nnector.github.tictailcontacts.api.imgur;

import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import rx.Observable;


/**
 * Image upload API
 */
public interface ImgurService {

    /**
     * Upload
     *
     * @param file image
     */
    @Multipart
    @POST("/3/image")
    Observable<ImageResponse> postImage(@Part("image") TypedFile file);
}
