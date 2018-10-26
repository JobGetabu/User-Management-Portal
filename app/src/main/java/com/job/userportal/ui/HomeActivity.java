package com.job.userportal.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.job.userportal.R;
import com.job.userportal.datasource.rest.ApiUtils;
import com.job.userportal.datasource.rest.UserApiService;
import com.job.userportal.model.CreateUser;
import com.job.userportal.model.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "Home";
    private UserApiService mUserApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mUserApiService = ApiUtils.getUserApiService();

        testPostUser();
        testUserFetch();
    }

    private void testUserFetch(){

        mUserApiService.getUser()
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                        if (response.isSuccessful()){
                            Toast.makeText(HomeActivity.this, "success", Toast.LENGTH_SHORT).show();

                            Log.d(TAG, "onResponse: "+response.body().getData().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {

                        Log.d("HomeActivity", "error loading from API");
                    }
                });
    }

    private void testPostUser(){
        mUserApiService.createUser("Mr Job","Developer")
                .enqueue(new Callback<CreateUser>() {
                    @Override
                    public void onResponse(Call<CreateUser> call, Response<CreateUser> response) {
                        Log.i(TAG, "post submitted to API." + response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<CreateUser> call, Throwable t) {

                        Log.e(TAG, "Unable to submit post to API.");
                    }
                });
    }
}
