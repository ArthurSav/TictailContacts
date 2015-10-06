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

import java.util.List;

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
import io.c0nnector.github.tictailcontacts.util.Val;
import io.c0nnector.github.tictailcontacts.util.leastview.GridSpacingItemDecoration;
import io.c0nnector.github.tictailcontacts.views.BaseRelativeLayout;
import retrofit.RetrofitError;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * View handler for the main activity
 */
public class ViewContacts extends BaseRelativeLayout {

    /**
     * Activity for result code, contact
     */
    public static final int RESULT_CONTACT = 223;


    Activity activity;

    private final CompositeSubscription subscriptions = new CompositeSubscription();


    @Inject
    ApiService apiService;

    @Bind(R.id.leastView)
    LeastView leastView;


    @OnClick(R.id.btnAdd)
    public void onButtonAdd(){
        ActivityAddContact.start(activity);
    }


    LeastAdapter adapter;

    public ViewContacts(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            Dagger.inject(this);
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        subscriptions.unsubscribe();
    }

    /**
     * Binds the view
     */
    public void bind(AppCompatActivity activity){
        this.activity = activity;

        requestContacts();
    }

    /**
     * Setup contacts list
     * @param contacts
     */
    private void setupContacts(List<Contact> contacts){

        if (adapter == null) {

            //contact binder
            Binder contactBinder = ContactBinder
                    .instance()
                    .setListItemClickListener(listItemListenerContact);

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

    /**
     * Contacts list item listener
     */
    private ListItemListener<ContactViewHolder, Contact> listItemListenerContact = (contactViewHolder, contact, i) -> {
        ActivityContact.start(activity, contact, i, contactViewHolder.imgAvatar);
    };

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
                        .subscribe(contactsSubscriber)
        );
    }

    private RetroSubscriber contactsSubscriber = new RetroSubscriber<List<Contact>>() {
        @Override
        public void onRetrofitError(RetrofitError error) {
            super.onRetrofitError(error);
        }
    };

    /*****************************************************
     * ---------------- * Update contact * --------------------
     *
     *
     *
     ****************************************************/

    /**
     * Update contact from intent result
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
}
