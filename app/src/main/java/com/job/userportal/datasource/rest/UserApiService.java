package com.job.userportal.datasource.rest;

import com.job.userportal.model.CreateUser;
import com.job.userportal.model.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Job on Friday : 10/26/2018.
 */
public interface UserApiService {


    @GET("/api/users/2")
    Call<UserResponse> getUser();

    @GET("/api/users?page=2")
    Call<List<UserResponse>> getUsers();

    @POST("/api/users")
    @FormUrlEncoded
    Call<CreateUser> createUser(@Field("name") String name, @Field("job") String job);

}
