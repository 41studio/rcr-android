package com.fourtyonestudio.rcr.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.AreaItemResponse;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.networks.RestApi;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.utils.CommonUtils;
import com.fourtyonestudio.rcr.utils.KeyboardUtils;
import com.fourtyonestudio.rcr.utils.UIHelper;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditAreaActivity extends AppCompatActivity {

    @Bind(R.id.etNameArea)
    EditText etNameArea;

    private int id;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_area);
        ButterKnife.bind(this);
        name = getIntent().getStringExtra(Constant.EXTRAS.NAME_AREA);
        id = getIntent().getIntExtra(Constant.EXTRAS.ID_AREA, 0);

        etNameArea.setText(name);
    }

    private void attemptAdd() {

        if (TextUtils.isEmpty(etNameArea.getText().toString())) {
            UIHelper.showSnackbar(getCurrentFocus(), "Please input name area");
            etNameArea.requestFocus();

        } else {
            addArea();
        }
    }

    private void attemptClone() {

        if (TextUtils.isEmpty(etNameArea.getText().toString())) {
            UIHelper.showSnackbar(getCurrentFocus(), "Please input name area");
            etNameArea.requestFocus();

        } else {
            cloneArea();
        }
    }

    @OnClick(R.id.btnDelete)
    public void delete(View view) {
        KeyboardUtils.hideSoftKeyboard(this, view);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                deleteArea();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }

    @OnClick(R.id.btnAdd)
    public void onClick(View view) {
        KeyboardUtils.hideSoftKeyboard(this, view);
        attemptAdd();

    }

    @OnClick(R.id.btnClone)
    public void clone(View view) {
        KeyboardUtils.hideSoftKeyboard(this, view);
        attemptClone();

    }

    private void cloneArea() {
        new DataPreferences(this).setLoadArea(true);
        if (CommonUtils.isNetworkAvailable(this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);
            DataPreferences dataPreferences = new DataPreferences(this);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().cloneArea(loginSession.getAuthToken(), id, etNameArea.getText().toString()).enqueue(new Callback<AreaItemResponse>() {
                @Override
                public void onResponse(Call<AreaItemResponse> call, Response<AreaItemResponse> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {

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
                public void onFailure(Call<AreaItemResponse> call, Throwable t) {
                    UIHelper.dismissDialog(pDialog);
                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_POST);
                }
            });
        } else {
            UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.NO_INET);
        }
    }

    private void addArea() {
        new DataPreferences(this).setLoadArea(true);
        if (CommonUtils.isNetworkAvailable(this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);
            DataPreferences dataPreferences = new DataPreferences(this);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().putArea(loginSession.getAuthToken(), id, etNameArea.getText().toString()).enqueue(new Callback<AreaItemResponse>() {
                @Override
                public void onResponse(Call<AreaItemResponse> call, Response<AreaItemResponse> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {

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
                public void onFailure(Call<AreaItemResponse> call, Throwable t) {
                    UIHelper.dismissDialog(pDialog);
                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_POST);
                }
            });
        } else {
            UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.NO_INET);
        }
    }


    private void deleteArea() {
        new DataPreferences(this).setLoadArea(true);
        if (CommonUtils.isNetworkAvailable(this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);
            DataPreferences dataPreferences = new DataPreferences(this);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().deleteArea(loginSession.getAuthToken(), id).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {

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
                public void onFailure(Call<String> call, Throwable t) {
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
