package io.c0nnector.github.tictailcontacts.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.c0nnector.github.tictailcontacts.App;
import io.c0nnector.github.tictailcontacts.MainActivity;

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
                MainActivity.class
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
