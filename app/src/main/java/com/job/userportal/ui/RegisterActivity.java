package com.job.userportal.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.job.userportal.R;
import com.job.userportal.datasource.rest.ApiUtils;
import com.job.userportal.datasource.rest.UserApiService;
import com.job.userportal.model.Register;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.job.userportal.util.Constants.LOGIN_USER_PREFS;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.signup_email)
    TextInputLayout signUpEmail;
    @BindView(R.id.signup_password)
    TextInputLayout signUpPassword;
    @BindView(R.id.signup_forgotpass)
    TextView forgotpass;
    @BindView(R.id.signup_button)
    Button signupButton;


    private static final String TAG = "register";

    private UserApiService mUserApiService;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        mUserApiService = ApiUtils.getUserApiService();

    }

    private void sendUserToMainActivity() {
        Toast.makeText(this, "Sign up Successful", Toast.LENGTH_SHORT).show();

        Intent main = new Intent(this, HomeActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(main);
    }

    //registration function
    @OnClick(R.id.signup_button)
    public void onViewClicked() {

        String email = signUpEmail.getEditText().getText().toString().trim();
        String password = signUpPassword.getEditText().getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            if (email.isEmpty()) {
                signUpEmail.getEditText().setError("Email is required");
                signUpEmail.requestFocus();
            }
            if (password.isEmpty()) {
                signUpPassword.getEditText().setError("Password is required");
                signUpPassword.requestFocus();
            }
        } else {


            mUserApiService.performRegister(email, password)
                    .enqueue(new Callback<Register>() {
                        @Override
                        public void onResponse(Call<Register> call, Response<Register> response) {
                            if (response.isSuccessful()) {

                                editor.putString(
                                        LOGIN_USER_PREFS, "logged in");

                                editor.apply();

                                sendUserToMainActivity();
                            }
                        }

                        @Override
                        public void onFailure(Call<Register> call, Throwable t) {

                            editor.putString(
                                    LOGIN_USER_PREFS, null);

                            editor.apply();

                            Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
