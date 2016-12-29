package com.fourtyonestudio.rcr.ui.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.Area;
import com.fourtyonestudio.rcr.models.AreaItemList;
import com.fourtyonestudio.rcr.models.AreaItemResponse;
import com.fourtyonestudio.rcr.models.AreaResponse;
import com.fourtyonestudio.rcr.models.Indicators;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.networks.RestApi;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.ui.adapter.ItemAdapter;
import com.fourtyonestudio.rcr.ui.adapter.ItemsAdapter;
import com.fourtyonestudio.rcr.utils.CommonUtils;
import com.fourtyonestudio.rcr.utils.DateUtils;
import com.fourtyonestudio.rcr.utils.Retrofit2Utils;
import com.fourtyonestudio.rcr.utils.UIHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AreaItemListingActivity extends AppCompatActivity {

    @Bind(R.id.tvCurrentDate)
    TextView tvCurrentDate;
    @Bind(R.id.rvItem)
    RecyclerView rvItem;
    @Bind(R.id.fab_add)
    FloatingActionButton fabAdd;

    //private Area area;
//    private List<Item> itemList;
    private List<Area> itemList;
    private List<AreaItemList> itemsList;
    private ItemAdapter adapter;
    private ItemsAdapter adapters;

    private int idArea;


    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat dateFormatter1;

    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_listing);
        ButterKnife.bind(this);

        idArea = getIntent().getIntExtra(Constant.EXTRAS.ID_AREA, 0);

        tvCurrentDate.setText(DateUtils.getDateNow());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        rvItem.setLayoutManager(layoutManager);
        rvItem.setHasFixedSize(true);

        itemsList = new ArrayList<>();
        adapters = new ItemsAdapter(AreaItemListingActivity.this, itemsList);
        adapters.notifyDataSetChanged();
        rvItem.setAdapter(adapters);

        getAreasItemDate(DateUtils.getDateNow1());

        String role = new DataPreferences(this).getLoginSession().getUser().getRole();
        if (role.equals(Constant.EXTRAS.MANAGER)) {
            fabAdd.setVisibility(View.VISIBLE);
        } else if (role.equals(Constant.EXTRAS.HELPER)) {
            fabAdd.setVisibility(View.GONE);
        }

        //Calendar
        dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        dateFormatter1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvCurrentDate.setText(dateFormatter.format(newDate.getTime()));
                getAreasItemDate(dateFormatter1.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    @OnClick(R.id.tvCurrentDate)
    public void clickCurrentDate() {
        fromDatePickerDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isLoadAreaItem = new DataPreferences(this).isLoadAreaItem();
        if (isLoadAreaItem) {
            getAreasItemDate(DateUtils.getDateNow1());
            new DataPreferences(this).setLoadAreaItem(false);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.fab_add)
    public void onClick(View view) {
        Intent intent = new Intent(this, AddAreaItemActivity.class);
        intent.putExtra(Constant.EXTRAS.ID_AREA, idArea);
        startActivity(intent);
    }


    private void getAreasItemDate(String time) {
        if (CommonUtils.isNetworkAvailable(this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);
            final DataPreferences dataPreferences = new DataPreferences(this);
            final LoginSession loginSession = dataPreferences.getLoginSession();


            new RestApi().getApi().getAreaItemsDate(loginSession.getAuthToken(), idArea, time).enqueue(new Callback<AreaItemResponse>() {
                @Override
                public void onResponse(Call<AreaItemResponse> call, Response<AreaItemResponse> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {

                        List<AreaItemList> areaList = response.body().getData().getAttributes().getItemList();

//                        for (int i = 0; i < areaList.size(); i++) {
//                            for (int j = 0; j < areaList.get(i).getTimes().size(); j++) {
//
//                                List<ItemAreaTable> itemAreaTables = SugarRecord.find(ItemAreaTable.class, "idtimes = ?", Integer.toString(areaList.get(i).getTimes().get(j).getId()));
//                                if (itemAreaTables.size() == 0) {
//                                    ItemAreaTable itemAreaTable = new ItemAreaTable();
//                                    itemAreaTable.setId_area(areaList.get(i).getId());
//                                    itemAreaTable.setId_times(areaList.get(i).getTimes().get(j).getId());
//                                    itemAreaTable.setTime(areaList.get(i).getTimes().get(j).getTime());
//                                    itemAreaTable.save();
//                                }
//                            }
//                        }


                        itemsList.clear();
                        itemsList.addAll(areaList);
                        adapters.notifyDataSetChanged();

                        Indicators indicators = dataPreferences.getIndicator();
                        if (indicators == null) {
                            getIndicator();
                        } else {
                            UIHelper.dismissDialog(pDialog);
                        }


                    } else {
                        UIHelper.dismissDialog(pDialog);
                        UIHelper.showSnackbar(getCurrentFocus(), Retrofit2Utils.getMessageError(response));
                    }
                }

                @Override
                public void onFailure(Call<AreaItemResponse> call, Throwable t) {
                    UIHelper.dismissDialog(pDialog);
                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_GET);
                }
            });
        } else {
            UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.NO_INET);
        }
    }

    private void getAreas() {
        if (CommonUtils.isNetworkAvailable(this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);
            final DataPreferences dataPreferences = new DataPreferences(this);
            final LoginSession loginSession = dataPreferences.getLoginSession();

            new RestApi().getApi().getAreaItems(loginSession.getAuthToken(), idArea).enqueue(new Callback<AreaResponse>() {
                @Override
                public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {

                        List<Area> areaList = response.body().getAreas();

//                        for (int i = 0; i < areaList.size(); i++) {
//                            for (int j = 0; j < areaList.get(i).getAttributes().getTimes().size(); j++) {
//
//                                List<ItemAreaTable> itemAreaTables = SugarRecord.find(ItemAreaTable.class, "idtimes = ?", Integer.toString(areaList.get(i).getAttributes().getTimes().get(j).getId()));
//                                if (itemAreaTables.size() == 0) {
//                                    ItemAreaTable itemAreaTable = new ItemAreaTable();
//                                    itemAreaTable.setId_area(areaList.get(i).getId());
//                                    itemAreaTable.setId_times(areaList.get(i).getAttributes().getTimes().get(j).getId());
//                                    itemAreaTable.setTime(areaList.get(i).getAttributes().getTimes().get(j).getTime());
//                                    itemAreaTable.save();
//                                }
//                            }
//                        }


                        itemList.clear();
                        itemList.addAll(areaList);
                        adapter.notifyDataSetChanged();

                        Indicators indicators = dataPreferences.getIndicator();
                        if (indicators == null) {
                            getIndicator();
                        } else {
                            UIHelper.dismissDialog(pDialog);
                        }


                    } else {
                        UIHelper.dismissDialog(pDialog);
                        UIHelper.showSnackbar(getCurrentFocus(), Retrofit2Utils.getMessageError(response));
                    }
                }

                @Override
                public void onFailure(Call<AreaResponse> call, Throwable t) {
                    UIHelper.dismissDialog(pDialog);
                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_GET);
                }
            });
        } else {
            UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.NO_INET);
        }
    }

    private void getIndicator() {
        if (CommonUtils.isNetworkAvailable(this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(this);
            DataPreferences dataPreferences = new DataPreferences(this);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().getIndicators(loginSession.getAuthToken()).enqueue(new Callback<Indicators>() {
                @Override
                public void onResponse(Call<Indicators> call, Response<Indicators> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {
                        new DataPreferences(AreaItemListingActivity.this).setIndicators(response.body());
//                        areas.clear();
//                        areas.addAll(response.body().getAreas());
//                        adapter.notifyDataSetChanged();


                    } else {
                        UIHelper.showSnackbar(getCurrentFocus(), Retrofit2Utils.getMessageError(response));
                    }
                }

                @Override
                public void onFailure(Call<Indicators> call, Throwable t) {
                    UIHelper.dismissDialog(pDialog);
                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_GET);
                }
            });
        } else {
            UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.NO_INET);
        }
    }
}
