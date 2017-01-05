package com.fourtyonestudio.rcr.ui.activity;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.AreaData;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.networks.RestApi;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.utils.CommonUtils;
import com.fourtyonestudio.rcr.utils.KeyboardUtils;
import com.fourtyonestudio.rcr.utils.UIHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddAreaItemActivity extends AppCompatActivity {


    @Bind(R.id.etNameArea)
    EditText etNameArea;
    @Bind(R.id.btnAddItem)
    Button btnAddItem;
    @Bind(android.R.id.list)
    ListView list;
    @Bind(R.id.etTime)
    EditText etTime;

    private int idArea;

    private ArrayList<String> listItems = new ArrayList<String>();
    private ArrayAdapter<String> adapterArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_area_item);
        ButterKnife.bind(this);
        idArea = getIntent().getIntExtra(Constant.EXTRAS.ID_AREA, 0);

        adapterArea = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        adapterArea.notifyDataSetChanged();
        list.setAdapter(adapterArea);

        //etTime.setFilters(new InputFilter[]{new InputFilterMinMax("0", "23")});

    }


    @OnClick(R.id.etTime)
    public void clickEtTime(View view) {
        KeyboardUtils.hideSoftKeyboard(this, view);
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddAreaItemActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                etTime.setText(selectedHour + ":" + selectedMinute);

            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


    @OnClick(R.id.addTime)
    public void clickTime(View view) {
        KeyboardUtils.hideSoftKeyboard(this, view);
        if (!TextUtils.isEmpty(etTime.getText().toString())) {
            if(listItems.size() == 0) {
                listItems.add(etTime.getText().toString());

            }else{
                for (int i = 0; i < listItems.size(); i++) {
                    if (!etTime.getText().toString().equals(listItems.get(i).toString())) {
                        listItems.add(etTime.getText().toString());
                    } else {
                        UIHelper.showSnackbar(getCurrentFocus(), "Sorry, time have been added");
                        break;

                    }
                }
            }


            adapterArea.notifyDataSetChanged();
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

            new RestApi().getApi().postAreaItems(loginSession.getAuthToken(), idArea, etNameArea.getText().toString(), listItems).enqueue(new Callback<AreaData>() {
                @Override
                public void onResponse(Call<AreaData> call, Response<AreaData> response) {
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
                public void onFailure(Call<AreaData> call, Throwable t) {
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
