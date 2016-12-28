package com.fourtyonestudio.rcr.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.networks.RestApi;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.utils.CommonUtils;
import com.fourtyonestudio.rcr.utils.KeyboardUtils;
import com.fourtyonestudio.rcr.utils.Retrofit2Utils;
import com.fourtyonestudio.rcr.utils.UIHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.etUsername)
    EditText etUsername;
    @Bind(R.id.etPassword)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void onClick(View view) {
        KeyboardUtils.hideSoftKeyboard(LoginActivity.this, view);
        attemptLogin();
    }

    @OnClick(R.id.btnRegister)
    public void onClickRegister(View view) {
        KeyboardUtils.hideSoftKeyboard(LoginActivity.this, view);
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void attemptLogin() {
        if (!Patterns.EMAIL_ADDRESS.matcher(etUsername.getText().toString()).matches()) {
            UIHelper.showSnackbar(getCurrentFocus(), "Please input valid email");
            etUsername.requestFocus();
        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
            UIHelper.showSnackbar(getCurrentFocus(), "Please input password");
            etPassword.requestFocus();
        } else {
            login();
        }
    }

    private void login() {
        if (CommonUtils.isNetworkAvailable(LoginActivity.this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(LoginActivity.this);
            new RestApi().getApi().login(etUsername.getText().toString(), etPassword.getText().toString()).enqueue(new Callback<LoginSession>() {
                @Override
                public void onResponse(Call<LoginSession> call, Response<LoginSession> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {
                        new DataPreferences(LoginActivity.this).setLoginSession(response.body());
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                        etUsername.setText("");
                        etPassword.setText("");
                        finish();
                    } else {
                        UIHelper.showSnackbar(getCurrentFocus(), Retrofit2Utils.getMessageError(response));
                    }
                }

                @Override
                public void onFailure(Call<LoginSession> call, Throwable t) {
                    UIHelper.dismissDialog(pDialog);
                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_POST);
                }
            });
        } else {
            UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.NO_INET);
        }
    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
