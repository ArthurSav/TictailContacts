package io.c0nnector.github.tictailcontacts.ui.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
        disableToolbarTitle();
    }


    @Override
    protected void onResume() {
        super.onResume();
        viewContacts.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //update contact if available
        if (Val.notNull(viewContacts)) viewContacts.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {

            viewContacts.viewSearch.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
