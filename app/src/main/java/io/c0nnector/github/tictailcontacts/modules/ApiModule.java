package io.c0nnector.github.tictailcontacts.modules;


import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;

import java.util.Collections;

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


    @Provides
    @Singleton
    Endpoint provideEndpoint(){
        return Endpoints.newFixedEndpoint(Constants.getServerUrl());
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

        //todo - see https://github.com/square/okhttp/issues/1844#issuecomment-146457523
        client.setProtocols(Collections.singletonList(Protocol.HTTP_1_1));

        client.setConnectTimeout(Constants.HTTP_CLIENT_TIMEOUT, SECONDS);
        client.setReadTimeout(Constants.HTTP_CLIENT_TIMEOUT, SECONDS);
        client.setWriteTimeout(Constants.HTTP_CLIENT_TIMEOUT, SECONDS);

        return client;
    }
}
