package io.c0nnector.github.tictailcontacts.modules;


import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.c0nnector.github.tictailcontacts.BuildConfig;
import io.c0nnector.github.tictailcontacts.api.ApiService;
import io.c0nnector.github.tictailcontacts.misc.Constants;
import io.c0nnector.github.tictailcontacts.util.Strings;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

import static java.util.concurrent.TimeUnit.SECONDS;

@Module(
        library = true,
        complete = false
)
public class ApiModule {

    private static final String BASE_URL = "http://192.168.1.4:5000";
    private static final String BASE_URL2 = "http://192.168.10.74:5000";

    @Provides
    @Singleton
    Endpoint provideEndpoint(){
        return Endpoints.newFixedEndpoint(BASE_URL);
    }


    @Provides
    @Singleton
    RestAdapter providesRestAdapter(OkHttpClient okHttpClient, Endpoint endpoint, Gson gson) {

        return new RestAdapter.Builder()

                .setClient(new OkClient(okHttpClient))

                .setEndpoint(endpoint)

                .setConverter(new GsonConverter(gson))

                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)

                .build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(RestAdapter restAdapter) {
        return restAdapter.create(ApiService.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return createOkHttpClient();
    }


    /**
     * Creates an http client for our api
     *
     * @return
     */
    static OkHttpClient createOkHttpClient() {

        OkHttpClient client = new OkHttpClient();

        client.setConnectTimeout(Constants.HTTP_CLIENT_TIMEOUT, SECONDS);
        client.setReadTimeout(Constants.HTTP_CLIENT_TIMEOUT, SECONDS);
        client.setWriteTimeout(Constants.HTTP_CLIENT_TIMEOUT, SECONDS);

        return client;
    }
}
