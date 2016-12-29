package com.fourtyonestudio.rcr.ui.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.AppraisalsResponse;
import com.fourtyonestudio.rcr.models.Area;
import com.fourtyonestudio.rcr.models.Indicators;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.networks.RestApi;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.tables.ItemAreaTable;
import com.fourtyonestudio.rcr.ui.activity.EditAreaActivity;
import com.fourtyonestudio.rcr.utils.CommonUtils;
import com.fourtyonestudio.rcr.utils.Retrofit2Utils;
import com.fourtyonestudio.rcr.utils.UIHelper;
import com.orm.SugarRecord;

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

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Area> list = new ArrayList<>();
    private Context context;

    public ItemAdapter(Context context, List<Area> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Area area = list.get(holder.getAdapterPosition());
        holder.tvName.setText(area.getAttributes().getName());





        String role = new DataPreferences(context).getLoginSession().getUser().getRole();
        if (role.equals(Constant.EXTRAS.MANAGER)) {
            for (int j = 0; j < area.getAttributes().getTimes().size(); j++) {

                final List<ItemAreaTable> itemAreaTables = SugarRecord.find(ItemAreaTable.class, "idtimes = ?", Integer.toString(area.getAttributes().getTimes().get(j).getId()));
                LayoutInflater i = LayoutInflater.from(context);
                View view = i.inflate(R.layout.child_manager, holder.parent, false);
                TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
                tvTime.setText(area.getAttributes().getTimes().get(j).getTime());

                final TextView tvRate = (TextView) view.findViewById(R.id.tvRate);

                if (itemAreaTables.get(0).getIndicator() != null) {
                    tvRate.setText(itemAreaTables.get(0).getIndicator());
                }
                final int finalJ = j;
                tvRate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DataPreferences dataPreferences = new DataPreferences(context);
                        final Indicators indicators = dataPreferences.getIndicator();

                        final CharSequence[] items = new CharSequence[indicators.getData().size() + 1];
                        for (int i = 0; i < indicators.getData().size(); i++) {
                            items[i] = indicators.getData().get(i).getAttributes().getCode() + " - " + indicators.getData().get(i).getAttributes().getDescription();
                        }
                        items[indicators.getData().size()] = "Cancel";

                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                context);
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {

                                if (item == indicators.getData().size()) {
                                    dialog.dismiss();
                                } else {

                                    Log.d("id time" + area.getAttributes().getTimes().get(finalJ).getId(), "id indicator" + indicators.getData().get(item).getId());
                                    putAppraisals(area.getAttributes().getTimes().get(finalJ).getId(), indicators.getData().get(item).getId());

                                    itemAreaTables.get(0).setIndicator(indicators.getData().get(item).getAttributes().getCode());
                                    itemAreaTables.get(0).save();

                                    tvRate.setText(indicators.getData().get(item).getAttributes().getCode());

                                    dialog.dismiss();
                                }
                            }
                        });
                        builder.show();

                    }
                });
                holder.parent.addView(view);
            }
        } else if (role.equals(Constant.EXTRAS.HELPER)) {
            for (int j = 0; j < area.getAttributes().getTimes().size(); j++) {

                final List<ItemAreaTable> itemAreaTables = SugarRecord.find(ItemAreaTable.class, "idtimes = ?", Integer.toString(area.getAttributes().getTimes().get(j).getId()));
                LayoutInflater i = LayoutInflater.from(context);
                View view = i.inflate(R.layout.child_helper, holder.parent, false);
                TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
                tvTime.setText(area.getAttributes().getTimes().get(j).getTime());

                CheckBox chxTime = (CheckBox) view.findViewById(R.id.chx_time);

                final int finalJ = j;
                chxTime.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked()) {
                            postAppraisals(area.getAttributes().getTimes().get(finalJ).getId());
//                            Toast.makeText(context,
//                                    "Checked" + area.getAttributes().getTimes().get(finalJ).getTime(), Toast.LENGTH_LONG).show();
                        }

                    }
                });


                holder.parent.addView(view);
            }
        }


        holder.setIsRecyclable(false);

    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.parent)
        LinearLayout parent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void putAppraisals(int time_id, String indicator_id) {
        if (CommonUtils.isNetworkAvailable(context)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(context);
            DataPreferences dataPreferences = new DataPreferences(context);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().putAppraisals(loginSession.getAuthToken(), time_id, indicator_id).enqueue(new Callback<AppraisalsResponse>() {
                @Override
                public void onResponse(Call<AppraisalsResponse> call, final Response<AppraisalsResponse> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {

                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, Retrofit2Utils.getMessageError(response), Toast.LENGTH_SHORT).show();
//                        UIHelper.showSnackbar(context.getCurrentFocus(), Retrofit2Utils.getMessageError(response));
                    }
                }

                @Override
                public void onFailure(Call<AppraisalsResponse> call, Throwable t) {
                    UIHelper.dismissDialog(pDialog);
                    Toast.makeText(context, Constant.MESSAGE.ERROR_POST, Toast.LENGTH_SHORT).show();
//                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_POST);
                }
            });
        } else {
            Toast.makeText(context, Constant.MESSAGE.NO_INET, Toast.LENGTH_SHORT).show();
//            UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.NO_INET);
        }
    }

    private void postAppraisals(int time_id) {
        if (CommonUtils.isNetworkAvailable(context)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(context);
            DataPreferences dataPreferences = new DataPreferences(context);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().postAppraisals(loginSession.getAuthToken(), time_id).enqueue(new Callback<AppraisalsResponse>() {
                @Override
                public void onResponse(Call<AppraisalsResponse> call, final Response<AppraisalsResponse> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {

                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, Retrofit2Utils.getMessageError(response), Toast.LENGTH_SHORT).show();
//                        UIHelper.showSnackbar(context.getCurrentFocus(), Retrofit2Utils.getMessageError(response));
                    }
                }

                @Override
                public void onFailure(Call<AppraisalsResponse> call, Throwable t) {
                    UIHelper.dismissDialog(pDialog);
                    Toast.makeText(context, Constant.MESSAGE.ERROR_POST, Toast.LENGTH_SHORT).show();
//                    UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.ERROR_POST);
                }
            });
        } else {
            Toast.makeText(context, Constant.MESSAGE.NO_INET, Toast.LENGTH_SHORT).show();
//            UIHelper.showSnackbar(getCurrentFocus(), Constant.MESSAGE.NO_INET);
        }
    }

}
