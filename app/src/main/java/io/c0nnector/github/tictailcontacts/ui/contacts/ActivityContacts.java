package io.c0nnector.github.tictailcontacts.ui.contacts;

import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import butterknife.Bind;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.ApiService;
import io.c0nnector.github.tictailcontacts.ui.BaseActivity;
import io.c0nnector.github.tictailcontacts.util.Val;

public class ActivityContacts extends BaseActivity {

    @Inject
    ApiService apiService;

    @Bind(R.id.vMain)
    ViewContacts viewContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void afterViews() {
        super.afterViews();

        viewContacts.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //update contact if available
        if (Val.notNull(viewContacts)) viewContacts.onActivityResult(requestCode, resultCode, data);
    }
}
