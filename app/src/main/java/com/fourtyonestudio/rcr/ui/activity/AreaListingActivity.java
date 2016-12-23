package com.fourtyonestudio.rcr.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.fourtyonestudio.rcr.utils.DateUtils;
import com.fourtyonestudio.rcr.utils.Retrofit2Utils;
import com.fourtyonestudio.rcr.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AreaListingActivity extends AppCompatActivity {

    @Bind(R.id.tvCurrentDateArea)
    TextView tvCurrentDate;
    @Bind(R.id.rvArea)
    RecyclerView rvArea;
    private List<Area> areas;
    private AreaAdapter adapter;

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

        areas = new ArrayList<>();
        adapter = new AreaAdapter(AreaListingActivity.this, areas);
        adapter.notifyDataSetChanged();
        rvArea.setAdapter(adapter);
        getAreas();
    }

    private void getAreas() {
        if (CommonUtils.isNetworkAvailable(AreaListingActivity.this)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(AreaListingActivity.this);
            DataPreferences dataPreferences = new DataPreferences(AreaListingActivity.this);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().getArea(loginSession.getAuth_token()).enqueue(new Callback<AreaResponse>() {
                @Override
                public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {
                        areas.addAll(response.body().getAreas());
                        adapter.notifyDataSetChanged();
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
}
