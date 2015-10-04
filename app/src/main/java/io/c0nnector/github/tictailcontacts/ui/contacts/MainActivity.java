package io.c0nnector.github.tictailcontacts.ui.contacts;

import android.os.Bundle;

import javax.inject.Inject;

import butterknife.Bind;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.ApiService;
import io.c0nnector.github.tictailcontacts.misc.Dagger;
import io.c0nnector.github.tictailcontacts.ui.BaseActivity;

public class MainActivity extends BaseActivity {

    @Inject
    ApiService apiService;

    @Bind(R.id.vMain)
    ViewMain viewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dagger.inject(this);
    }

    @Override
    protected void afterViews() {
        super.afterViews();

        viewMain.bind(this);
    }

}
