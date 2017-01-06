package com.fourtyonestudio.rcr.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.models.UserResponse;
import com.fourtyonestudio.rcr.networks.RestApi;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.utils.CommonUtils;
import com.fourtyonestudio.rcr.utils.UIHelper;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SettingActivity extends AppCompatActivity {

    @Bind(R.id.etName)
    EditText etName;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.etPasswordConfirmation)
    EditText etPasswordConfirmation;
    @Bind(R.id.btnUpdate)
    Button btnUpdate;

    private UserResponse userResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        getUserDetail();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.btnUpdate)
    public void clickUpdate() {
        attemptUpdate();
    }

    private void attemptUpdate() {
        if (TextUtils.isEmpty(etPassword.getText().toString()) || etPassword.length() < 6) {
            UIHelper.showSnackbar(getCurrentFocus(), "Please input password");
            etPassword.requestFocus();
        } else if (!etPassword.getText().toString().equals(etPasswordConfirmation.getText().toString())) {
            Log.d("response", "Please input correct password confirmation");
            UIHelper.showSnackbar(getCurrentFocus(), "Please input correct password confirmation");
            etPasswordConfirmation.requestFocus();
        } else if (TextUtils.isEmpty(etName.getText().toString())) {
            UIHelper.showSnackbar(getCurrentFocus(), "Please input name");
            etName.requestFocus();
        } else {
            updateUserDetail();
        }


    }

    private void getUserDetail() {
        if (CommonUtils.isNetworkAvailable(this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);
            final DataPreferences dataPreferences = new DataPreferences(this);
            final LoginSession loginSession = dataPreferences.getLoginSession();

            new RestApi().getApi().getUserDetail(loginSession.getAuthToken(), loginSession.getUser().getId()).enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {
                        userResponse = response.body();
                        etName.setText(userResponse.getData().getAttributes().getName());

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            UIHelper.showSnackbar(getCurrentFocus(), jObjError.getString(Constant.MESSAGE.ERROR_BODY));
                        } catch (Exception e) {
                            UIHelper.showSnackbar(getCurrentFocus(), e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    UIHelper.dismissDialog(pDialog);
                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_GET);
                }
            });
        } else {
            UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.NO_INET);
        }
    }

    private void updateUserDetail() {
        if (CommonUtils.isNetworkAvailable(this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);
            final DataPreferences dataPreferences = new DataPreferences(this);
            final LoginSession loginSession = dataPreferences.getLoginSession();

            new RestApi().getApi().updateUser(loginSession.getAuthToken(), loginSession.getUser().getId(), loginSession.getUser().getEmail(), Integer.toString(userResponse.getData().getAttributes().getRoleId()), etName.getText().toString(), etPassword.getText().toString(), etPasswordConfirmation.getText().toString()).enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {

                        new DataPreferences(SettingActivity.this).clear();
                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            UIHelper.showSnackbar(getCurrentFocus(), jObjError.getString(Constant.MESSAGE.ERROR_BODY));
                        } catch (Exception e) {
                            UIHelper.showSnackbar(getCurrentFocus(), e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    UIHelper.dismissDialog(pDialog);
                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_GET);
                }
            });
        } else {
            UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.NO_INET);
        }
    }
}
