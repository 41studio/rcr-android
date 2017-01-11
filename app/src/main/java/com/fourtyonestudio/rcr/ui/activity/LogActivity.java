package com.fourtyonestudio.rcr.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.LogDataDatum;
import com.fourtyonestudio.rcr.models.LogDataResponse;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.networks.RestApi;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.ui.adapter.LogAdapter;
import com.fourtyonestudio.rcr.utils.CommonUtils;
import com.fourtyonestudio.rcr.utils.DateUtils;
import com.fourtyonestudio.rcr.utils.EndlessOnScrollListener;
import com.fourtyonestudio.rcr.utils.UIHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LogActivity extends AppCompatActivity {


    @Bind(R.id.tvCurrentDateArea)
    TextView tvCurrentDateArea;
    @Bind(R.id.tvEmpty)
    TextView tvEmpty;
    @Bind(R.id.rvArea)
    RecyclerView rvArea;
    @Bind(R.id.activity_area_listing)
    RelativeLayout activityAreaListing;
    private List<LogDataDatum> areaList;
    private LogAdapter areaAdapter;

    private LinearLayoutManager layoutManager;

    private int currentTotal = 1;
    private int totalCount = 0;
    private boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        rvArea.setLayoutManager(layoutManager);
        rvArea.setHasFixedSize(true);

        areaList = new ArrayList<>();
        areaAdapter = new LogAdapter(LogActivity.this, areaList);
        areaAdapter.notifyDataSetChanged();
        rvArea.setAdapter(areaAdapter);

//        getAreas(1);
        getLogActivity();

        rvArea.addOnScrollListener(new EndlessOnScrollListener() {

            @Override
            public void onScrolledToEnd() {
                if (!loading) {
                    currentTotal = currentTotal + 1;
                    if (currentTotal <= totalCount) {
//                        getAreas(currentTotal);
                        getLogActivity();
                    }

                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isLoadArea = new DataPreferences(this).isLoadArea();
        if (isLoadArea) {
//            getAreas(1);
            getLogActivity();
            new DataPreferences(this).setLoadArea(false);
        }
    }


    private void getLogActivity() {
        if (CommonUtils.isNetworkAvailable(LogActivity.this)) {
            loading = true;
            final ProgressDialog pDialog = UIHelper.showProgressDialog(LogActivity.this);
            DataPreferences dataPreferences = new DataPreferences(LogActivity.this);
            LoginSession loginSession = dataPreferences.getLoginSession();

            TimeZone tz = TimeZone.getDefault();
            System.out.println("TimeZone   " + tz.getDisplayName(false, TimeZone.SHORT) + " Timezon id :: " + tz.getID());

            new RestApi().getApi().getLogActivity(loginSession.getAuthToken(), DateUtils.getDateNow1(), tz.getID()).enqueue(new Callback<LogDataResponse>() {
                @Override
                public void onResponse(Call<LogDataResponse> call, final Response<LogDataResponse> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {

                        final LogDataResponse areaResponse = response.body();

                        if (areaResponse.getData().size() != 0) {
                            tvEmpty.setVisibility(View.GONE);
                            rvArea.setVisibility(View.VISIBLE);

//                            currentTotal = areaResponse.getMeta().getCurrentPage();
//                            totalCount = areaResponse.getMeta().getTotalPages();
//
//                            if (currentTotal == 1) {
//                                areaList.clear();
//                            }

                            //delete if was detect pagination
                            areaList.clear();

                            areaList.addAll(areaResponse.getData());
                            areaAdapter.notifyDataSetChanged();
                        } else {
                            tvEmpty.setVisibility(View.VISIBLE);
                            rvArea.setVisibility(View.GONE);
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            UIHelper.showSnackbar(getCurrentFocus(), jObjError.getString(Constant.MESSAGE.ERROR_BODY));
                        } catch (Exception e) {
                            UIHelper.showSnackbar(getCurrentFocus(), e.getMessage());
                        }
                    }

                    loading = false;
                }

                @Override
                public void onFailure(Call<LogDataResponse> call, Throwable t) {
                    loading = false;
                    UIHelper.dismissDialog(pDialog);
                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_GET);
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
