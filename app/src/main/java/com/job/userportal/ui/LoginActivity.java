package com.job.userportal.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.job.userportal.R;
import com.job.userportal.datasource.rest.ApiUtils;
import com.job.userportal.datasource.rest.UserApiService;
import com.job.userportal.model.Login;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.job.userportal.util.Constants.LOGIN_USER_PREFS;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_email)
    TextInputLayout loginEmail;
    @BindView(R.id.login_password)
    TextInputLayout loginPassword;
    @BindView(R.id.login_forgotpass)
    TextView forgotpass;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.create_account)
    TextView create_account;
    @BindView(R.id.background_dim_login)
    View dim;
    @BindView(R.id.progress_bar_login)
    ProgressBar progressBar;

    private static final String TAG = "login";

    private UserApiService mUserApiService;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        mUserApiService = ApiUtils.getUserApiService();

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(signup);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getEditText().getText().toString().trim();
                String password = loginPassword.getEditText().getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()){
                    if (email.isEmpty()){
                        loginEmail.getEditText().setError("Email is required");
                        loginEmail.requestFocus();
                    }
                    if (password.isEmpty()){
                        loginPassword.getEditText().setError("Password is required");
                        loginPassword.requestFocus();
                    }
                }
                else {
                    dim.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);


                    mUserApiService.performLogin(email,password)
                            .enqueue(new Callback<Login>() {
                                @Override
                                public void onResponse(Call<Login> call, Response<Login> response) {
                                    if (response.isSuccessful()){

                                        editor.putString(
                                                LOGIN_USER_PREFS, "logged in");

                                        editor.apply();

                                        dim.setVisibility(View.GONE);
                                        progressBar.setVisibility(View.GONE);
                                        sendUserToMainActivity();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Login> call, Throwable t) {

                                    editor.putString(
                                            LOGIN_USER_PREFS, null);

                                    editor.apply();

                                    dim.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }

        });
    }

    private void sendUserToMainActivity() {
        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
        Intent main = new Intent(LoginActivity.this, HomeActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(main);
    }
}
