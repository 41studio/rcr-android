package com.fourtyonestudio.rcr.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.AreaData;
import com.fourtyonestudio.rcr.models.Indicators;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.networks.RestApi;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.utils.CommonUtils;
import com.fourtyonestudio.rcr.utils.InputFilterMinMax;
import com.fourtyonestudio.rcr.utils.KeyboardUtils;
import com.fourtyonestudio.rcr.utils.Retrofit2Utils;
import com.fourtyonestudio.rcr.utils.UIHelper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAreaItemActivity extends AppCompatActivity {


    @Bind(R.id.etNameArea)
    EditText etNameArea;
    @Bind(R.id.btnAddItem)
    Button btnAddItem;
    @Bind(android.R.id.list)
    ListView list;
    @Bind(R.id.etTime)
    EditText etTime;

    private int id;

    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_area_item);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra(Constant.EXTRAS.ID_AREA, 0);


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

        etTime.setFilters(new InputFilter[]{new InputFilterMinMax("0", "23")});




    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {
        KeyboardUtils.hideSoftKeyboard(this, v);

        if (!TextUtils.isEmpty(etTime.getText().toString())) {
            listItems.add(etTime.getText().toString());
            adapter.notifyDataSetChanged();
            etTime.setText("");
        } else {
            UIHelper.showSnackbar(getCurrentFocus(), "Please input time");
            etTime.requestFocus();
        }

    }

    private void attemptAdd() {

        if (TextUtils.isEmpty(etNameArea.getText().toString())) {
            UIHelper.showSnackbar(getCurrentFocus(), "Please input name area");
            etNameArea.requestFocus();

        } else {
            addArea();
        }
    }

    @OnClick(R.id.btnAddItem)
    public void onClick(View view) {
        KeyboardUtils.hideSoftKeyboard(this, view);
        attemptAdd();

    }

    private void addArea() {
        new DataPreferences(this).setLoadAreaItem(true);
        if (CommonUtils.isNetworkAvailable(this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);
            DataPreferences dataPreferences = new DataPreferences(this);
            LoginSession loginSession = dataPreferences.getLoginSession();

            new RestApi().getApi().postAreaItems(loginSession.getAuth_token(), id, etNameArea.getText().toString(), listItems).enqueue(new Callback<AreaData>() {
                @Override
                public void onResponse(Call<AreaData> call, Response<AreaData> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {

                        finish();

                    } else {
                        UIHelper.showSnackbar(getCurrentFocus(), Retrofit2Utils.getMessageError(response));
                    }
                }

                @Override
                public void onFailure(Call<AreaData> call, Throwable t) {
                    UIHelper.dismissDialog(pDialog);
                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_POST);
                }
            });
        } else {
            UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.NO_INET);
        }
    }


}
