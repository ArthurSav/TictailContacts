package io.c0nnector.github.tictailcontacts.ui.contacts;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import io.c0nnector.github.least.Binder;
import io.c0nnector.github.least.LeastAdapter;
import io.c0nnector.github.least.LeastView;
import io.c0nnector.github.least.ListItemListener;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.api.ApiService;
import io.c0nnector.github.tictailcontacts.api.RetroSubscriber;
import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.misc.Dagger;
import io.c0nnector.github.tictailcontacts.ui.contact.ActivityContact;
import io.c0nnector.github.tictailcontacts.util.leastview.GridSpacingItemDecoration;
import io.c0nnector.github.tictailcontacts.views.BaseRelativeLayout;
import retrofit.RetrofitError;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * View handler for the main activity
 */
public class ViewMain extends BaseRelativeLayout {

    Activity activity;

    private final CompositeSubscription subscriptions = new CompositeSubscription();


    @Inject
    ApiService apiService;

    @Bind(R.id.leastView)
    LeastView leastView;


    LeastAdapter adapter;

    public ViewMain(Context context, AttributeSet attrs) {
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
        ActivityContact.start(activity, contact, contactViewHolder.imgAvatar);
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
}
