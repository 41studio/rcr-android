package com.fourtyonestudio.rcr.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.AreaDetailResponse;
import com.fourtyonestudio.rcr.models.AreaItemList;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.models.request.Item;
import com.fourtyonestudio.rcr.models.request.ItemAreaRequest;
import com.fourtyonestudio.rcr.models.request.ItemTimeAttributesRequest;
import com.fourtyonestudio.rcr.networks.RestApi;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.utils.CommonUtils;
import com.fourtyonestudio.rcr.utils.InputFilterMinMax;
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

public class EditAreaItemActivity extends AppCompatActivity {
    @Bind(R.id.etNameArea)
    EditText etNameArea;
    @Bind(R.id.top)
    LinearLayout top;

    private AreaItemList areaItemList;
    private int id;
    private List<EditText> etList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_area_item);
        ButterKnife.bind(this);

        id = getIntent().getIntExtra(Constant.EXTRAS.ID_AREA, 0);

        getAreaDetails();
    }


    private void attemptAdd() {

        if (TextUtils.isEmpty(etNameArea.getText().toString())) {
            UIHelper.showSnackbar(getCurrentFocus(), "Please input name areaItemList");
            etNameArea.requestFocus();

        } else {

            boolean startHit = true;

            for (int x = 0; x < etList.size(); x++) {
                if (etList.get(x).getText().toString().matches("")) {
                    UIHelper.showSnackbar(getCurrentFocus(), "Please input time areaItemList");
                    startHit = false;
                    break;
                }

            }

            if (startHit) {
                putItemArea();
            }

        }
    }

    @OnClick(R.id.btnAddItem)
    public void onClick(View view) {
        KeyboardUtils.hideSoftKeyboard(this, view);
        attemptAdd();
    }

    @OnClick(R.id.btnDelete)
    public void onClickDelete(View view) {
        KeyboardUtils.hideSoftKeyboard(this, view);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                deleteItemArea();
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


    private void getAreaDetails() {
        if (getIntent().hasExtra(Constant.EXTRAS.AREA)) {
            areaItemList = (AreaItemList) getIntent().getExtras().get(Constant.EXTRAS.AREA);
            etNameArea.setText(areaItemList.getName());

            for (int j = 0; j < areaItemList.getTimes().size(); j++) {

                LayoutInflater i = LayoutInflater.from(this);
                View view = i.inflate(R.layout.item_time, top, false);
                EditText etTime = (EditText) view.findViewById(R.id.et_time);
                etTime.setText(areaItemList.getTimes().get(j).getTime());
                etTime.setFilters(new InputFilter[]{new InputFilterMinMax("0", "23")});
                top.addView(view);

                etList.add(etTime);
            }


        }
    }


    private void deleteItemArea() {
        new DataPreferences(this).setLoadAreaItem(true);
        if (CommonUtils.isNetworkAvailable(this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);
            DataPreferences dataPreferences = new DataPreferences(this);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().deleteItemArea(loginSession.getAuthToken(), id, areaItemList.getId()).enqueue(new Callback<String>() {
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

    private void putItemArea() {
        new DataPreferences(this).setLoadAreaItem(true);
        if (CommonUtils.isNetworkAvailable(this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);

            List<ItemTimeAttributesRequest> itemTimesAttributes = new ArrayList<>();
            for (int i = 0; i < areaItemList.getTimes().size(); i++) {

                itemTimesAttributes.add(new ItemTimeAttributesRequest(Integer.toString(areaItemList.getTimes().get(i).getId()), etList.get(i).getText().toString()));

            }

            Item item = new Item(itemTimesAttributes, etNameArea.getText().toString());

            ItemAreaRequest itemAreaRequest = new ItemAreaRequest(item);


            DataPreferences dataPreferences = new DataPreferences(this);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().putItemsArea(loginSession.getAuthToken(), id, areaItemList.getId(), itemAreaRequest).enqueue(new Callback<AreaDetailResponse>() {
                @Override
                public void onResponse(Call<AreaDetailResponse> call, Response<AreaDetailResponse> response) {
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
                public void onFailure(Call<AreaDetailResponse> call, Throwable t) {
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
