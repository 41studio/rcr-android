package com.fourtyonestudio.rcr.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.InviteUser;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.models.Roles;
import com.fourtyonestudio.rcr.models.RolesDatum;
import com.fourtyonestudio.rcr.models.UserResponse;
import com.fourtyonestudio.rcr.networks.RestApi;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.utils.CommonUtils;
import com.fourtyonestudio.rcr.utils.KeyboardUtils;
import com.fourtyonestudio.rcr.utils.UIHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.etEmail)
    EditText etUsername;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.etPasswordConfirmation)
    EditText etPasswordConfirmation;
    @Bind(R.id.spRole)
    Spinner spRole;
    @Bind(R.id.etName)
    EditText etName;

    private String idRole = "";

    private List<RolesDatum> listRole = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ButterKnife.bind(this);

        etPassword.setVisibility(View.GONE);
        etPasswordConfirmation.setVisibility(View.GONE);

        getRoles();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        // On selecting a spinner item
//        String item = parent.getItemAtPosition(position).toString();
//        // Showing selected spinner item
//        Log.d( "Selected: ", item);
        idRole = listRole.get(position).getId();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    @OnClick(R.id.btnRegisterNow)
    public void onClick(View view) {
        KeyboardUtils.hideSoftKeyboard(this, view);
        attemptRegister();
    }

    private void attemptRegister() {

        if (!Patterns.EMAIL_ADDRESS.matcher(etUsername.getText().toString()).matches()) {
            UIHelper.showSnackbar(getCurrentFocus(), "Please input valid email");
            etUsername.requestFocus();
        } else if (TextUtils.isEmpty(etName.getText().toString())) {
            UIHelper.showSnackbar(getCurrentFocus(), "Please input name");
            etName.requestFocus();
//        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
//            UIHelper.showSnackbar(getCurrentFocus(), "Please input password");
//            etPassword.requestFocus();
//        } else if (!etPassword.getText().toString().equals(etPasswordConfirmation.getText().toString())) {
//            Log.d("response", "Please input correct password confirmation");
//            UIHelper.showSnackbar(getCurrentFocus(), "Please input correct password confirmation");
//            etPasswordConfirmation.requestFocus();
        } else if (idRole.equals("")) {
            UIHelper.showSnackbar(getCurrentFocus(), "Please input role id");
        } else {
//            createUser();
            inviteUser();
        }
    }


//    private void addUser() {
//        new DataPreferences(this).setLoadAreaItem(true);
//        if (CommonUtils.isNetworkAvailable(this)) {
//            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);
//            DataPreferences dataPreferences = new DataPreferences(this);
//            LoginSession loginSession = dataPreferences.getLoginSession();
//            new RestApi().getApi().createUser(loginSession.getAuthToken(), etUsername.getText().toString(), etPassword.getText().toString(), etPasswordConfirmation.getText().toString(), idRole).enqueue(new Callback<UserResponse>() {
//                @Override
//                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                    UIHelper.dismissDialog(pDialog);
//                    if (response.isSuccessful()) {
////                        Intent intent = new Intent(AddUserActivity.this, MenuActivity.class);
////                        startActivity(intent);
//
//                        etUsername.setText("");
//                        etPassword.setText("");
//                        etPasswordConfirmation.setText("");
//                        spRole.setSelection(0);
//
//                        finish();
//
//                    } else {
//                        UIHelper.showSnackbar(getCurrentFocus(), Retrofit2Utils.getMessageError(response));
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<UserResponse> call, Throwable t) {
//                    UIHelper.dismissDialog(pDialog);
//                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_POST);
//                }
//            });
//        } else {
//            UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.NO_INET);
//        }
//    }

    private void inviteUser() {
        new DataPreferences(this).setLoadArea(true);
        if (CommonUtils.isNetworkAvailable(this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);
            DataPreferences dataPreferences = new DataPreferences(this);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().inviteUser(loginSession.getAuthToken(), etUsername.getText().toString(), etName.getText().toString(), idRole).enqueue(new Callback<InviteUser>() {
                @Override
                public void onResponse(Call<InviteUser> call, Response<InviteUser> response) {
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
                public void onFailure(Call<InviteUser> call, Throwable t) {
                    UIHelper.dismissDialog(pDialog);
                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_POST);
                }
            });
        } else {
            UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.NO_INET);
        }
    }


    private void createUser() {
        new DataPreferences(this).setLoadArea(true);
        if (CommonUtils.isNetworkAvailable(this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);
            DataPreferences dataPreferences = new DataPreferences(this);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().createUser(loginSession.getAuthToken(), etUsername.getText().toString(), etName.getText().toString(),
                    etPassword.getText().toString(), etPasswordConfirmation.getText().toString(), idRole).enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
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
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    UIHelper.dismissDialog(pDialog);
                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_POST);
                }
            });
        } else {
            UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.NO_INET);
        }
    }


    private void getRoles() {
        if (CommonUtils.isNetworkAvailable(AddUserActivity.this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(AddUserActivity.this);
            new RestApi().getApi().getRoles().enqueue(new Callback<Roles>() {
                @Override
                public void onResponse(Call<Roles> call, final Response<Roles> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {
                        listRole = response.body().getData();

                        spRole.setOnItemSelectedListener(AddUserActivity.this);

                        List<String> listItems = new ArrayList<String>();

                        for (int i = 0; i < listRole.size(); i++) {
                            listItems.add(listRole.get(i).getAttributes().getName());
                        }

                        // Creating adapter for spinner
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddUserActivity.this, android.R.layout.simple_spinner_item, listItems);

                        // Drop down layout style - list view with radio button
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // attaching data adapter to spinner
                        spRole.setAdapter(dataAdapter);


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
                public void onFailure(Call<Roles> call, Throwable t) {
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
