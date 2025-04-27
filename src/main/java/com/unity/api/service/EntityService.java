package com.unity.api.service;

import com.unity.api.model.Publisher;
import com.unity.api.model.User;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface EntityService {

    @GET("/admin/login")
    Call<String> initLogin();

    @GET("/admin")
    Call<String> initAdmin();

    @FormUrlEncoded
    @POST("/admin/login")
    Call<Void> login(
            @HeaderMap Map<String, String> headers,
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("/admin/api/resources/Publisher/actions/new")
    Call<String> createPublisher(@HeaderMap Map<String, String> headers, @Body Publisher publisherRequest);

    @Multipart
    @POST("/admin/api/resources/Publisher/actions/new")
    Call<String> createPublisher(
            @HeaderMap Map<String, String> headers,
            @Part("name") RequestBody name,
            @Part("email") RequestBody email
    );

    @GET("/admin/api/resources/Publisher/actions/list")
    Call<String> getPublisher(@HeaderMap Map<String, String> headers);

    @GET("/locales/en/translation.json")
    Call<String> translateJson(@HeaderMap Map<String, String> headers);

    @Multipart
    @POST("/admin/api/resources/Post/actions/new")
    Call<String> createPost( @HeaderMap Map<String, String> headers,
                             @Part("title") RequestBody title, @Part("content") RequestBody content,
                             @Part("published") RequestBody published, @Part("content") RequestBody publisher,
                             @Part("content") RequestBody status);

    @Multipart
    @PUT("/admin/api/resources/Post/actions/new")
    Call<String> updatePost( @HeaderMap Map<String, String> headers,
                             @Part("title") RequestBody title, @Part("content") RequestBody content,
                             @Part("published") RequestBody published, @Part("content") RequestBody publisher,
                             @Part("content") RequestBody status);
}

