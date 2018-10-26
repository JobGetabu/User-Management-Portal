package com.job.userportal.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.job.userportal.R;
import com.job.userportal.adapter.UsersAdapter;
import com.job.userportal.datasource.rest.ApiUtils;
import com.job.userportal.datasource.rest.UserApiService;
import com.job.userportal.model.CreateUser;
import com.job.userportal.model.UserResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.job.userportal.util.Constants.LOGIN_USER_PREFS;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "Home";
    @BindView(R.id.main_clientlist)
    RecyclerView mainClientlist;
    @BindView(R.id.progress_loading)
    ProgressBar progressLoading;
    @BindView(R.id.fab_button)
    FloatingActionButton fabButton;

    private UserApiService mUserApiService;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    private UsersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();

        mUserApiService = ApiUtils.getUserApiService();

        setUpUI();
        loadList();
        testPostUser();
        testUserFetch();
    }

    private void loadList() {

        mUserApiService.getMyUsers()
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                        UserResponse userResponce = response.body();

                        mAdapter = new UsersAdapter(HomeActivity.this, userResponce.getData(), new UsersAdapter.UserItemListener() {
                            @Override
                            public void onPostClick(long id) {
                                Toast.makeText(HomeActivity.this, "cc", Toast.LENGTH_SHORT).show();

                            }
                        });

                        mAdapter.notifyDataSetChanged();
                        mainClientlist.setAdapter(mAdapter);
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {

                    }
                });
    }

    private void setUpUI() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mainClientlist.setLayoutManager(layoutManager);
        mainClientlist.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mainClientlist.addItemDecoration(itemDecoration);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //checks if a user is currently logged in
        String user = mSharedPreferences.getString(LOGIN_USER_PREFS, null);

        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void testUserFetch() {

        mUserApiService.getUser()
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                        if (response.isSuccessful()) {
                            Toast.makeText(HomeActivity.this, "success", Toast.LENGTH_SHORT).show();

                            Log.d(TAG, "onResponse: " + response.body().getData().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {

                        Log.d("HomeActivity", "error loading from API");
                    }
                });
    }

    private void testPostUser() {
        mUserApiService.createUser("Mr Job", "Developer")
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Sign out the user
        if (mSharedPreferences.getString(LOGIN_USER_PREFS, null) != null)
            editor.putString(LOGIN_USER_PREFS, null);

        editor.apply();
    }

    @OnClick(R.id.fab_button)
    public void onViewClicked() {
    }
}
