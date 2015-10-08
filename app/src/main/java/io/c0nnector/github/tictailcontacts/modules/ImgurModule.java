package io.c0nnector.github.tictailcontacts.modules;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.c0nnector.github.tictailcontacts.BuildConfig;
import io.c0nnector.github.tictailcontacts.api.imgur.ImgurService;
import io.c0nnector.github.tictailcontacts.misc.Constants;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

@Module(
        library = true,
        complete = false
)
public class ImgurModule {

    @Provides
    @Singleton
    @Named("imgur")
    Endpoint provideEndpoint(){
        return Endpoints.newFixedEndpoint(Constants.IMGUR_PATH);
    }

    @Provides
    @Singleton
    @Named("imgur")
    RequestInterceptor provideInterceptor(){
        return request ->  request.addHeader("Authorization"," Client-ID " + Constants.IMGUR_CLIENT_ID);
    }

    @Provides
    @Singleton
    @Named("imgur")
    RestAdapter providesRestAdapter(@Named("imgur") Endpoint endpoint, @Named("imgur") RequestInterceptor requestInterceptor) {

        return new RestAdapter.Builder()

                .setEndpoint(endpoint)

                .setRequestInterceptor(requestInterceptor)

                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)

                .build();
    }

    @Provides
    @Singleton
    ImgurService provideApiService(@Named("imgur") RestAdapter restAdapter) {
        return restAdapter.create(ImgurService.class);
    }
}
