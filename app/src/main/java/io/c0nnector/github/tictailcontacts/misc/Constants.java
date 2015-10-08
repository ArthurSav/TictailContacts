package io.c0nnector.github.tictailcontacts.misc;


public class Constants {

    /**
     * OkHttp client timeout
     */
    public static final int HTTP_CLIENT_TIMEOUT = 20; //seconds

    /**
     * User avatar dimensions - small
     */
    public static final int DIMEN_IMAGE_CONTACT_SMALL = 70; //dp
    public static final int DIMEN_IMAGE_CONTACT = 120; //dp


    /*****************************************************
     * ---------------- * Messages * --------------------
     *
     *
     *
     ****************************************************/

    public static final String ERROR_NETWORK = "Network error";


    /*****************************************************
     * ---------------- * Server * --------------------
     *
     *
     *
     ****************************************************/

    private static final String BASE_URL = "http://192.168.1.4:5000";
    private static final String BASE_URL2 = "http://192.168.10.74:5000";

    public static String getServerUrl(){
        return BASE_URL;
    }

}
