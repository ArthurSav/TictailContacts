package io.c0nnector.github.tictailcontacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import javax.inject.Inject;

import io.c0nnector.github.tictailcontacts.api.ApiService;
import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.misc.Dagger;
import io.c0nnector.github.tictailcontacts.ui.BaseActivity;
import rx.Subscriber;

public class MainActivity extends BaseActivity {

    @Inject
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dagger.inject(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        apiService.getContacts()
                .subscribe(new Subscriber<List<Contact>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Contact> contacts) {

                    }
                });
    }
}
