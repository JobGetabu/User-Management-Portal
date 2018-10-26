package com.job.userportal.datasource.rest;

import com.job.userportal.model.CreateUser;
import com.job.userportal.model.Login;
import com.job.userportal.model.Register;
import com.job.userportal.model.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Job on Friday : 10/26/2018.
 */
public interface UserApiService {


    @GET("/api/users/2")
    Call<UserResponse> getUser();

    @GET("/api/users?page=2")
    Call<List<UserResponse>> getUsers();

    @GET("/api/users?page=2")
    Call<UserResponse> getMyUsers();

    @POST("/api/users")
    @FormUrlEncoded
    Call<CreateUser> createUser(@Field("name") String name, @Field("job") String job);

    @POST("/api/login")
    @FormUrlEncoded
    Call<Login> performLogin(@Field("email") String email, @Field("password") String password);

    @POST("/api/register")
    @FormUrlEncoded
    Call<Register> performRegister(@Field("email") String email, @Field("password") String password);

    @PUT("/api/users/2")
    @FormUrlEncoded
    Call<CreateUser> updateUser(@Field("name") String name, @Field("job") String job);

    @DELETE("/api/users/2")
    Call deleteUser();

}
