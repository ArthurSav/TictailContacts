package io.c0nnector.github.tictailcontacts.api.imgur;

/**
 *
 * Response from imgur when uploading to the server.
 */
public class ImageResponse {

  public boolean success;
  public int status;
  public UploadedImage data;

  public boolean isSuccess() {
    return success;
  }

  public int getStatus() {
    return status;
  }

  public UploadedImage getData() {
    return data;
  }
}
