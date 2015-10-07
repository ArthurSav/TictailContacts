package io.c0nnector.github.tictailcontacts.ui.contacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

import com.f2prateek.dart.Dart;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import io.c0nnector.github.least.Binder;
import io.c0nnector.github.least.LeastAdapter;
import io.c0nnector.github.least.LeastView;
import io.c0nnector.github.least.ListItemListener;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.ApiService;
import io.c0nnector.github.tictailcontacts.api.RetroSubscriber;
import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.misc.Dagger;
import io.c0nnector.github.tictailcontacts.ui.add_contact.ActivityAddContact;
import io.c0nnector.github.tictailcontacts.ui.contact.ActivityContact;
import io.c0nnector.github.tictailcontacts.util.Intents;
import io.c0nnector.github.tictailcontacts.util.UtilRx;
import io.c0nnector.github.tictailcontacts.util.Val;
import io.c0nnector.github.tictailcontacts.util.leastview.GridSpacingItemDecoration;
import io.c0nnector.github.tictailcontacts.views.BaseRelativeLayout;
import io.c0nnector.github.tictailcontacts.views.ViewSearch;
import retrofit.RetrofitError;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * View handler for the main activity
 */
public class ViewContacts extends BaseRelativeLayout implements ViewSearch.ViewSearchListener{

    /**
     * Activity for result code, contact
     */
    public static final int RESULT_CONTACT = 223;


    Activity activity;
    List<Contact> contacts;

    LeastAdapter adapter;

    private final CompositeSubscription subscriptions = new CompositeSubscription();


    @Inject
    ApiService apiService;

    @Bind(R.id.leastView)
    LeastView leastView;

    @Bind(R.id.vSearch)
    ViewSearch viewSearch;


    @OnClick(R.id.btnAdd)
    public void onButtonAdd(){
        ActivityAddContact.start(activity);
    }



    public ViewContacts(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            Dagger.inject(this);
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        UtilRx.unsubscribeIfNotNull(subscriptions);
    }


    /**
     * Binds the view
     */
    public void bind(AppCompatActivity activity){
        this.activity = activity;

        viewSearch.bind(this, this);

        requestContacts();
    }

    /**
     * Setup contacts list
     * @param contacts
     */
    private void setupContacts(List<Contact> contacts){
        this.contacts = contacts;

        if (Val.isNull(adapter)) {

            //contact binder
            Binder contactBinder = ContactBinder
                    .instance()

                    //item click listener, opens contact view
                    .setListItemClickListener((holder, contact, position) -> ActivityContact.start(activity, contact, position, holder.imgAvatar));

            adapter = new LeastAdapter.Builder()
                    .binder(contactBinder)
                    .items(contacts)
                    .build(getContext());

            leastView.setAdapter(adapter);

            //grid
            leastView.setLayoutManager(new GridLayoutManager(getContext(), 4));

            //item spacing
            leastView.addItemDecoration(new GridSpacingItemDecoration(4, 20, true));
        }

        else adapter.replace(contacts);
    }

    /*****************************************************
     * ---------------- * Requests * --------------------
     *
     *
     *
     ****************************************************/

    @SuppressWarnings("unchecked")
    private void requestContacts(){

        subscriptions.add(

                apiService.getContacts()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(this::setupContacts)
                        .subscribe(new RetroSubscriber<List<Contact>>() {
                            @Override
                            public void onRetrofitError(RetrofitError error) {
                                super.onRetrofitError(error);
                                //todo - show error
                            }
                        })
        );

    }

    /*****************************************************
     * ---------------- * Update contact * --------------------
     *
     *
     *
     ****************************************************/

    /**
     * Handles onActivity result
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if (resultCode == Intents.RESULT_CONTACT && Val.notNull(data)) {

            Bundle bundle = data.getExtras();

            //contact
            Contact contact = Parcels.unwrap(bundle.getParcelable("contact"));

            //position in the list
            Integer position = bundle.getInt("position");

            if (!Val.containsNull(contact, position)) updateContact(position, contact);
        }
    }

    /**
     * Updates a contact from the list
     * @param position
     * @param contact
     */
    private void updateContact(int position, @NonNull Contact contact){

        if (Val.notNull(adapter)) {
            adapter.replace(contact, position);
        }
    }


    /*****************************************************
     * ---------------- * Search * --------------------
     *
     *
     *
     ****************************************************/

    @Override
    public void onTextSearch(String query) {

        //don't search when empty
        if (Val.isNull(contacts)) return;

        Observable.from(contacts)
                .filter(contact -> contact.getName().contains(query))
                .toList()
                .doOnError(Throwable::printStackTrace)
                .subscribe(adapter::replace);
    }
}
