package io.c0nnector.github.tictailcontacts.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.c0nnector.github.tictailcontacts.App;
import io.c0nnector.github.tictailcontacts.ui.add_contact.ActivityAddContact;
import io.c0nnector.github.tictailcontacts.ui.contact.ViewContact;
import io.c0nnector.github.tictailcontacts.ui.contacts.ActivityContacts;
import io.c0nnector.github.tictailcontacts.ui.contacts.ViewContacts;
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
                ActivityContacts.class,
                ActivityAddContact.class,

                ViewContacts.class,
                UrlImageView.class,
                ViewContact.class
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
