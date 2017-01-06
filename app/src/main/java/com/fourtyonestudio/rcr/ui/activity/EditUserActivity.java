package com.fourtyonestudio.rcr.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.models.Roles;
import com.fourtyonestudio.rcr.models.RolesDatum;
import com.fourtyonestudio.rcr.models.UserData;
import com.fourtyonestudio.rcr.models.UserResponse;
import com.fourtyonestudio.rcr.networks.RestApi;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.utils.CommonUtils;
import com.fourtyonestudio.rcr.utils.KeyboardUtils;
import com.fourtyonestudio.rcr.utils.Retrofit2Utils;
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

public class EditUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.etUsername)
    EditText etUsername;
    @Bind(R.id.spRole)
    Spinner spRole;
    @Bind(R.id.etName)
    EditText etName;
    @Bind(R.id.btnDelete)
    Button btnDelete;

    private String idRole = "";
    private UserData userData;
    private List<RolesDatum> listRole = new ArrayList<>();

    private String id;
    private String name;
    private String email;
    private String role;

    private LoginSession loginSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        ButterKnife.bind(this);

        email = getIntent().getStringExtra(Constant.EXTRAS.EMAIL_AREA);
        name = getIntent().getStringExtra(Constant.EXTRAS.NAME_AREA);
        id = getIntent().getStringExtra(Constant.EXTRAS.ID_USER);
        role = getIntent().getStringExtra(Constant.EXTRAS.ROLE_USER);

        etUsername.setText(email);
        etName.setText(name);

        DataPreferences dataPreferences = new DataPreferences(this);
        loginSession = dataPreferences.getLoginSession();


        if (loginSession.getUser().getId() == Integer.parseInt(id)) {
            btnDelete.setVisibility(View.GONE);
        } else {
            btnDelete.setVisibility(View.VISIBLE);
        }

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

    @OnClick(R.id.btnDelete)
    public void delete(View view) {
        KeyboardUtils.hideSoftKeyboard(this, view);


        if (loginSession.getUser().getRole().equals(Constant.EXTRAS.MANAGER)) {
            if (role.equals(Constant.EXTRAS.HELPER)) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        deleteUser();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }

        } else if (loginSession.getUser().getRole().equals(Constant.EXTRAS.OWNER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm");
            builder.setMessage("Are you sure?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    deleteUser();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }


    }


    @OnClick(R.id.btnRegisterNow)
    public void onClick(View view) {
        KeyboardUtils.hideSoftKeyboard(this, view);
        if (loginSession.getUser().getRole().equals(Constant.EXTRAS.MANAGER)) {
            if (role.equals(Constant.EXTRAS.HELPER)) {
                attemptRegister();
            }

            if (loginSession.getUser().getId() == Integer.parseInt(id)) {
                attemptRegister();
            }

        } else if (loginSession.getUser().getRole().equals(Constant.EXTRAS.OWNER)) {
            attemptRegister();
        }


    }

    private void attemptRegister() {

        if (!Patterns.EMAIL_ADDRESS.matcher(etUsername.getText().toString()).matches()) {
            UIHelper.showSnackbar(getCurrentFocus(), "Please input valid email");
            etUsername.requestFocus();
        } else if (TextUtils.isEmpty(etName.getText().toString())) {
            UIHelper.showSnackbar(getCurrentFocus(), "Please input name");
            etName.requestFocus();

        } else if (idRole.equals("")) {
            UIHelper.showSnackbar(getCurrentFocus(), "Please input role id");
        } else {
            updateUser();
        }
    }


    private void updateUser() {
        new DataPreferences(this).setLoadArea(true);
        if (CommonUtils.isNetworkAvailable(this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);
            DataPreferences dataPreferences = new DataPreferences(this);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().putUsers(loginSession.getAuthToken(), Integer.parseInt(id), etUsername.getText().toString(), etName.getText().toString(), idRole).enqueue(new Callback<UserResponse>() {
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
        if (CommonUtils.isNetworkAvailable(EditUserActivity.this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(EditUserActivity.this);
            new RestApi().getApi().getRoles().enqueue(new Callback<Roles>() {
                @Override
                public void onResponse(Call<Roles> call, final Response<Roles> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {
                        listRole = response.body().getData();

                        spRole.setOnItemSelectedListener(EditUserActivity.this);

                        List<String> listItems = new ArrayList<String>();

                        for (int i = 0; i < listRole.size(); i++) {
                            listItems.add(listRole.get(i).getAttributes().getName());
                        }

                        // Creating adapter for spinner
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(EditUserActivity.this, android.R.layout.simple_spinner_item, listItems);

                        // Drop down layout style - list view with radio button
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // attaching data adapter to spinner
                        spRole.setAdapter(dataAdapter);


                        int selectionPosition = dataAdapter.getPosition(role);
                        spRole.setSelection(selectionPosition);


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

    private void deleteUser() {
        new DataPreferences(this).setLoadArea(true);
        if (CommonUtils.isNetworkAvailable(this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);
            DataPreferences dataPreferences = new DataPreferences(this);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().deleteUsers(loginSession.getAuthToken(), Integer.parseInt(id)).enqueue(new Callback<String>() {
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

    private void getUserDetails() {
        if (getIntent().hasExtra(Constant.EXTRAS.AREA)) {
            userData = (UserData) getIntent().getExtras().get(Constant.EXTRAS.AREA);
            etUsername.setText(userData.getAttributes().getEmail());


        }
    }
}
