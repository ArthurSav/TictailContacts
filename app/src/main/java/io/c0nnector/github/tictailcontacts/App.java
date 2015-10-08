package io.c0nnector.github.tictailcontacts;

import android.app.Application;
import android.support.annotation.NonNull;

import dagger.ObjectGraph;
import io.c0nnector.github.tictailcontacts.misc.Dagger;
import io.c0nnector.github.tictailcontacts.modules.AppModule;
import io.c0nnector.github.tictailcontacts.modules.ImgurModule;
import timber.log.Timber;


public class App extends Application {

    private ObjectGraph objectGraph;


    @Override
    public void onCreate() {
        super.onCreate();

        setupLog();

        setupGraph();
    }

    @Override
    public Object getSystemService(@NonNull String name) {

        //access dagger graph via system service
        if (Dagger.matchesService(name)) {
            return objectGraph;
        }
        return super.getSystemService(name);
    }

    /*****************************************************
     * ---------------- * Logs * --------------------
     *
     *
     *
     ****************************************************/

    private void setupLog(){

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    /*****************************************************
     * ---------------- * Dagger * --------------------
     *
     *
     *
     ****************************************************/

    /**
     * Dagger graph setup
     * @see Dagger for graph injection
     */
    private void setupGraph(){
        objectGraph = ObjectGraph.create(new AppModule(this));
    }

}
