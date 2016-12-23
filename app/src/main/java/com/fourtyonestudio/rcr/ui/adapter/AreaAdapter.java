package com.fourtyonestudio.rcr.ui.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.Area;
import com.fourtyonestudio.rcr.models.AreaDetailResponse;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.networks.RestApi;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.ui.activity.ItemListingActivity;
import com.fourtyonestudio.rcr.utils.CommonUtils;
import com.fourtyonestudio.rcr.utils.Retrofit2Utils;
import com.fourtyonestudio.rcr.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mohamadsodiq on 12/13/16.
 */

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {

    private List<Area> list = new ArrayList<>();
    private Context context;

    public AreaAdapter(Context context, List<Area> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Area area = list.get(holder.getAdapterPosition());
        holder.tvName.setText(area.getAttributes().getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, ItemListingActivity.class);
//                intent.putExtra(Constant.EXTRAS.AREA, area);
//                context.startActivity(intent);
                getAreas(area.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvName)
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void getAreas(int id) {
        if (CommonUtils.isNetworkAvailable(context)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog((Activity) context);
            DataPreferences dataPreferences = new DataPreferences(context);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().getAreaDetail(loginSession.getAuth_token(), id).enqueue(new Callback<AreaDetailResponse>() {
                @Override
                public void onResponse(Call<AreaDetailResponse> call, Response<AreaDetailResponse> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(context, ItemListingActivity.class);
                        intent.putExtra(Constant.EXTRAS.AREA, response.body().getAreas());
                        context.startActivity(intent);
                    } else {
                        UIHelper.showSnackbar(((Activity) context).getCurrentFocus(), Retrofit2Utils.getMessageError(response));
                    }
                }

                @Override
                public void onFailure(Call<AreaDetailResponse> call, Throwable t) {
                    UIHelper.dismissDialog(pDialog);
                    UIHelper.showSnackbar(((Activity) context).getCurrentFocus(), Constant.MESSAGE.ERROR_GET);
                }
            });
        } else {
            UIHelper.showSnackbar(((Activity) context).getCurrentFocus(), Constant.MESSAGE.NO_INET);
        }
    }
}
