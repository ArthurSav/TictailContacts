package io.c0nnector.github.tictailcontacts.api;

import io.c0nnector.github.tictailcontacts.util.Val;
import retrofit.RetrofitError;
import rx.Subscriber;
import timber.log.Timber;

/**
 *  Custom retrofit subscriber.
 *  Use it to subscribe all your retrofit calls
 */
public abstract class RetroSubscriber<T> extends Subscriber<T> {

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Override
    public void onError(Throwable e) {

        //retrofit error
        RetrofitError error = convertToRetrofitError(e);

        if (Val.notNull(error)) onRetrofitError(error);
    }

    /**
     * Called when there's a valid retrofit error
     * @param error
     */
    public  void onRetrofitError(RetrofitError error){}

    /**
     * Retrofit error - Kind network
     * @param error
     */
    public void onNetworkError(RetrofitError error){}

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onCompleted() {

    }


    /*****************************************************
     * ---------------- * Converters * --------------------
     *
     *
     *
     ****************************************************/

    /**
     * Converts the exception into a retrofit error
     * @param e
     */
    private RetrofitError convertToRetrofitError(Throwable e){

        if (e instanceof RetrofitError) {

            RetrofitError error = (RetrofitError) e;

            return error;
        }

        return null;
    }


    /*****************************************************
     * ---------------- * Filters * --------------------
     *
     *
     *
     ****************************************************/

    /**
     * Decides what to do with a retrofit error
     * @param error
     */
    private void filterError(RetrofitError error){

        Timber.e("retrofit error: " + error.getMessage());

        switch (error.getKind()) {

            //no network
            case NETWORK: {

                Timber.e("NETWORK ERROR");

                break;
            }

            //server error
            case HTTP: {

                Timber.e("SERVER ERROR");

                break;
            }

            case UNEXPECTED: {

                Timber.e("UNEXPECTED ERROR");

                break;
            }

            case CONVERSION: {

                Timber.e("CONVERSION ERROR");

                break;
            }
        }
    }


    /*****************************************************
     * ---------------- * Bus * --------------------
     *
     *
     *
     ****************************************************/

    /**
     * Post events to bus
     * @param error
     */
    private void postError(Object error){
        //todo - add bus
    }
}
