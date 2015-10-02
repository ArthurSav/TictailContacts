package io.c0nnector.github.tictailcontacts.api;

import java.util.List;
import java.util.Map;

import io.c0nnector.github.tictailcontacts.api.model.Contact;
import io.c0nnector.github.tictailcontacts.util.Strings;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;

/**
 * Api endpoints
 */
public interface ApiService {


    @GET("/contacts")
    Observable<List<Contact>> getContacts();

    @GET("/contacts/{id}")
    Observable<Contact> getContact(

            @Path("id")
            String id
    );

    @FormUrlEncoded
    @POST("/contacts")
    Observable<Contact> addContact(

            @Field("id")
            String id,

            @Field("first_name")
            String firstName,

            @Field("last_name")
            String lastName,

            @Field("image")
            String image,

            @Field("location")
            String location,

            @Field("team")
            Strings team,

            @Field("color")
            String color
    );

    @FormUrlEncoded
    @POST("/contacts")
    Observable<Contact> addContact(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @PUT("/contact/{id}")
    Observable<Contact> updateContact(

            @Path("id")
            String id,

            @FieldMap
            Map<String, String> fields);

    @DELETE("/contacts/{id}")
    Observable<Contact> deleteContact(

            @Path("id")
            String id
    );
}
