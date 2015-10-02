package io.c0nnector.github.tictailcontacts.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.c0nnector.github.tictailcontacts.App;
import io.c0nnector.github.tictailcontacts.MainActivity;
import io.c0nnector.github.tictailcontacts.ui.contacts.MainView;
import io.c0nnector.github.tictailcontacts.views.UrlImageView;

/**
 * Main module
 */
@Module(
        includes = {
                ApiModule.class,
                DataModule.class
        }
        ,
        injects = {
                MainActivity.class,

                MainView.class,
                UrlImageView.class
        }
)
public class AppModule {

    App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }
}
