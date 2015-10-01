package io.c0nnector.github.tictailcontacts.api;

import java.util.List;

import io.c0nnector.github.tictailcontacts.api.model.Contact;
import retrofit.http.GET;
import rx.Observable;

/**
 * Api endpoints
 */
public interface ApiService {

    @GET("/contacts")
    Observable<List<Contact>> getContacts();
}
