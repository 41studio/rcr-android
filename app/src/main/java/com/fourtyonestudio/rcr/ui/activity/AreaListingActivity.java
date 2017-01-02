package com.fourtyonestudio.rcr.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.Area;
import com.fourtyonestudio.rcr.models.AreaResponse;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.networks.RestApi;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.ui.adapter.AreaAdapter;
import com.fourtyonestudio.rcr.utils.CommonUtils;
import com.fourtyonestudio.rcr.utils.Retrofit2Utils;
import com.fourtyonestudio.rcr.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AreaListingActivity extends AppCompatActivity {

    @Bind(R.id.tvCurrentDateArea)
    TextView tvCurrentDate;
    @Bind(R.id.rvArea)
    RecyclerView rvArea;
    @Bind(R.id.fab_add)
    FloatingActionButton fabAdd;
    private List<Area> areaList;
    private AreaAdapter areaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_listing);
        ButterKnife.bind(this);
//        tvCurrentDate.setText(DateUtils.getDateNow());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        rvArea.setLayoutManager(layoutManager);
        rvArea.setHasFixedSize(true);

        areaList = new ArrayList<>();
        areaAdapter = new AreaAdapter(AreaListingActivity.this, areaList);
        areaAdapter.notifyDataSetChanged();
        rvArea.setAdapter(areaAdapter);

        getAreas();

        String role = new DataPreferences(this).getLoginSession().getUser().getRole();
        if (role.equals(Constant.EXTRAS.HELPER)) {
            fabAdd.setVisibility(View.GONE);
        } else {
            fabAdd.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isLoadArea = new DataPreferences(this).isLoadArea();
        if (isLoadArea) {
            getAreas();
            new DataPreferences(this).setLoadArea(false);
        }
    }

    @OnClick(R.id.fab_add)
    public void onClick(View view) {
        Intent intent = new Intent(AreaListingActivity.this, AddAreaActivity.class);
        startActivity(intent);
    }


    private void getAreas() {
        if (CommonUtils.isNetworkAvailable(AreaListingActivity.this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(AreaListingActivity.this);
            DataPreferences dataPreferences = new DataPreferences(AreaListingActivity.this);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().getArea(loginSession.getAuthToken()).enqueue(new Callback<AreaResponse>() {
                @Override
                public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {
                        areaList.clear();
                        areaList.addAll(response.body().getAreas());
                        areaAdapter.notifyDataSetChanged();


                    } else {
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
