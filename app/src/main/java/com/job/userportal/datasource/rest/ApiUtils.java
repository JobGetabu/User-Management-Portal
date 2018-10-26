package com.job.userportal.datasource.rest;

/**
 * Created by Job on Friday : 10/26/2018.
 */
public class ApiUtils {
    public static final String BASE_URL = "https://reqres.in/api/";

    public static UserApiService getUserApiService() {
        return RetrofitClient.getClient(BASE_URL).create(UserApiService.class);
    }
}
