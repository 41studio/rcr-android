package com.fourtyonestudio.rcr.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.fourtyonestudio.rcr.utils.EndlessOnScrollListener;
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

public class AreaListingActivity extends AppCompatActivity {

    @Bind(R.id.tvCurrentDateArea)
    TextView tvCurrentDate;
    @Bind(R.id.rvArea)
    RecyclerView rvArea;
    @Bind(R.id.fab_add)
    FloatingActionButton fabAdd;
    @Bind(R.id.etSearch)
    EditText etSearch;
    @Bind(R.id.btnSearch)
    RelativeLayout btnSearch;
    @Bind(R.id.tvEmpty)
    TextView tvEmpty;

    private List<Area> areaList;
    private AreaAdapter areaAdapter;

    private LinearLayoutManager layoutManager;

    private int currentTotal = 1;
    private int totalCount = 0;
    private boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_listing);
        ButterKnife.bind(this);
//        tvCurrentDate.setText(DateUtils.getDateNow());

        etSearch.clearFocus();

        layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        rvArea.setLayoutManager(layoutManager);
        rvArea.setHasFixedSize(true);

        areaList = new ArrayList<>();
        areaAdapter = new AreaAdapter(AreaListingActivity.this, areaList);
        areaAdapter.notifyDataSetChanged();
        rvArea.setAdapter(areaAdapter);

        getAreas(1);
        String role = new DataPreferences(this).getLoginSession().getUser().getRole();
        if (role.equals(Constant.EXTRAS.HELPER)) {
            fabAdd.setVisibility(View.GONE);
        } else {
            fabAdd.setVisibility(View.VISIBLE);
        }


        rvArea.addOnScrollListener(new EndlessOnScrollListener() {

            @Override
            public void onScrolledToEnd() {
                if (!loading) {
                    currentTotal = currentTotal + 1;
                    if (currentTotal <= totalCount) {
                        getAreas(currentTotal);
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
            getAreas(1);
            new DataPreferences(this).setLoadArea(false);
        }
    }

    @OnClick(R.id.btnSearch)
    public void clickSearch(View view) {
        KeyboardUtils.hideSoftKeyboard(this, view);
        attemptSearch();

    }

    private void attemptSearch() {

        if (TextUtils.isEmpty(etSearch.getText().toString())) {
            getAreas(1);

        } else {
            searchAreas(etSearch.getText().toString());
        }
    }

    @OnClick(R.id.fab_add)
    public void onClick(View view) {
        Intent intent = new Intent(AreaListingActivity.this, AddAreaActivity.class);
        startActivity(intent);
    }


    private void getAreas(int page) {
        if (CommonUtils.isNetworkAvailable(AreaListingActivity.this)) {
            loading = true;
            final ProgressDialog pDialog = UIHelper.showProgressDialog(AreaListingActivity.this);
            DataPreferences dataPreferences = new DataPreferences(AreaListingActivity.this);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().getArea(loginSession.getAuthToken(), page).enqueue(new Callback<AreaResponse>() {
                @Override
                public void onResponse(Call<AreaResponse> call, final Response<AreaResponse> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {

                        final AreaResponse areaResponse = response.body();

                        if (areaResponse.getAreas().size() != 0) {
                            tvEmpty.setVisibility(View.GONE);
                            rvArea.setVisibility(View.VISIBLE);

                            currentTotal = areaResponse.getMeta().getCurrentPage();
                            totalCount = areaResponse.getMeta().getTotalPages();

                            if (currentTotal == 1) {
                                areaList.clear();
                            }

                            areaList.addAll(areaResponse.getAreas());
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
                public void onFailure(Call<AreaResponse> call, Throwable t) {
                    loading = false;
                    UIHelper.dismissDialog(pDialog);
                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_GET);
                }
            });
        } else {
            UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.NO_INET);
        }
    }

    private void searchAreas(String name) {
        if (CommonUtils.isNetworkAvailable(AreaListingActivity.this)) {
            loading = true;
            final ProgressDialog pDialog = UIHelper.showProgressDialog(AreaListingActivity.this);
            DataPreferences dataPreferences = new DataPreferences(AreaListingActivity.this);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().searchArea(loginSession.getAuthToken(), name).enqueue(new Callback<AreaResponse>() {
                @Override
                public void onResponse(Call<AreaResponse> call, final Response<AreaResponse> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {

                        final AreaResponse areaResponse = response.body();

                        if (areaResponse.getAreas().size() != 0) {
                            tvEmpty.setVisibility(View.GONE);
                            rvArea.setVisibility(View.VISIBLE);

                            currentTotal = areaResponse.getMeta().getCurrentPage();
                            totalCount = areaResponse.getMeta().getTotalPages();

                            if (currentTotal == 1) {
                                areaList.clear();
                            }

                            areaList.addAll(areaResponse.getAreas());
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
                public void onFailure(Call<AreaResponse> call, Throwable t) {
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
