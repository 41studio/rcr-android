package com.fourtyonestudio.rcr.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.LoginSession;
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

public class AcceptInvitationActivity extends AppCompatActivity {

    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.etPasswordConfirmation)
    EditText etPasswordConfirmation;
    @Bind(R.id.btnUpdate)
    Button btnUpdate;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_invitation);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            token = uri.getQueryParameter("invitation_token");
            Log.d("token", token);
        }

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
        } else {
            updateUserDetail();
        }


    }


    private void updateUserDetail() {
        if (CommonUtils.isNetworkAvailable(this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);
            new RestApi().getApi().acceptInvitation(token, etPassword.getText().toString(), etPasswordConfirmation.getText().toString()).enqueue(new Callback<LoginSession>() {
                @Override
                public void onResponse(Call<LoginSession> call, Response<LoginSession> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {

                        new DataPreferences(AcceptInvitationActivity.this).clear();
                        new DataPreferences(AcceptInvitationActivity.this).setLoginSession(response.body());
                        Intent intent = new Intent(AcceptInvitationActivity.this, MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        etPassword.setText("");
                        etPasswordConfirmation.setText("");

                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            UIHelper.showSnackbar(getCurrentFocus(), jObjError.getString(Constant.MESSAGE.ERROR_BODY));
                        } catch (Exception e) {
                            UIHelper.showSnackbar(getCurrentFocus(), e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginSession> call, Throwable t) {
                    etPassword.setText("");
                    etPasswordConfirmation.setText("");
                    UIHelper.dismissDialog(pDialog);
                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_GET);
                }
            });
        } else {
            UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.NO_INET);
        }
    }
}
