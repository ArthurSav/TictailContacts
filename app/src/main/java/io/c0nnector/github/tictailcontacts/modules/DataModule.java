package io.c0nnector.github.tictailcontacts.modules;

import android.app.Application;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module(
        includes = ApiModule.class,

        complete = false,
        library = true
)
public class DataModule {

    @Provides
    @Singleton
    Gson providesGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    Picasso providesPicasso(Application app, OkHttpClient client){

        return new Picasso.Builder(app)
                .downloader(new OkHttpDownloader(client))
                .listener((picasso, uri, exception) -> Timber.e(exception, "Failed to load image: %s", uri))
                .build();
    }
}
