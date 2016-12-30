package com.fourtyonestudio.rcr.ui.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.AppraisalsResponse;
import com.fourtyonestudio.rcr.models.AreaItemList;
import com.fourtyonestudio.rcr.models.Indicators;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.networks.RestApi;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.ui.activity.EditAreaItemActivity;
import com.fourtyonestudio.rcr.utils.CommonUtils;
import com.fourtyonestudio.rcr.utils.Retrofit2Utils;
import com.fourtyonestudio.rcr.utils.UIHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Riris.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private List<AreaItemList> list = new ArrayList<>();
    private Context context;

    public ItemsAdapter(Context context, List<AreaItemList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        return new ItemsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ItemsAdapter.ViewHolder holder, final int position) {
        final AreaItemList area = list.get(holder.getAdapterPosition());
        holder.tvName.setText(area.getName());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context, EditAreaItemActivity.class);
                intent.putExtra(Constant.EXTRAS.AREA, (Serializable) area);
                intent.putExtra(Constant.EXTRAS.ID_AREA, area.getId());
                context.startActivity(intent);
                return false;
            }
        });

        String role = new DataPreferences(context).getLoginSession().getUser().getRole();
        if (role.equals(Constant.EXTRAS.MANAGER)) {
            for (int j = 0; j < area.getTimes().size(); j++) {

                //final List<ItemAreaTable> itemAreaTables = SugarRecord.find(ItemAreaTable.class, "idtimes = ?", Integer.toString(area.getTimes().get(j).getId()));
                LayoutInflater i = LayoutInflater.from(context);
                View view = i.inflate(R.layout.child_manager, holder.parent, false);

                CheckBox chx = (CheckBox) view.findViewById(R.id.chx);
                chx.setChecked(false);


                //Set time appriasals
                TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
                tvTime.setText(area.getTimes().get(j).getTime());

                final TextView tvRate = (TextView) view.findViewById(R.id.tvRate);

                if (area.getTimes().get(j).getAppraisals() != null) {

                    if (area.getTimes().get(j).getAppraisals().getChecked() != null) {
                        chx.setChecked(area.getTimes().get(j).getAppraisals().getChecked());
                    } else {
                        chx.setChecked(false);
                    }

                    //Set rate appriasals
                    if (area.getTimes().get(j).getAppraisals().getIndicator() != null) {
                        tvRate.setText(area.getTimes().get(j).getAppraisals().getIndicator());
                    }

                    ImageView imgAdd = (ImageView) view.findViewById(R.id.add);
                    ImageView imgCheck = (ImageView) view.findViewById(R.id.checkmark);

                    if (area.getTimes().get(j).getAppraisals().getDescription() != null) {
                        imgAdd.setVisibility(View.GONE);
                        imgCheck.setVisibility(View.VISIBLE);
                    } else {
                        imgAdd.setVisibility(View.VISIBLE);
                        imgCheck.setVisibility(View.GONE);
                    }

                    final int finalJ = j;

                    RelativeLayout addDesc = (RelativeLayout) view.findViewById(R.id.btnAddDesc);
                    addDesc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // custom dialog
                            final Dialog dialog = new Dialog(context);
                            dialog.setContentView(R.layout.dialog_rate);
                            dialog.setTitle("Description");

                            final EditText text = (EditText) dialog.findViewById(R.id.etDescription);
                            Button btnRate = (Button) dialog.findViewById(R.id.btnAddRate);

                            if (area.getTimes().get(finalJ).getAppraisals().getDescription() != null) {
                                text.setText(area.getTimes().get(finalJ).getAppraisals().getDescription());
                            }

                            btnRate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (!TextUtils.isEmpty(text.getText().toString())) {
                                        Log.d("id appriasal" + area.getTimes().get(finalJ).getAppraisals().getId(), text.getText().toString());
                                        putAppraisalsDesc(area.getTimes().get(finalJ).getAppraisals().getId(), text.getText().toString());
//                                    if (area.getTimes().get(finalJ).getAppraisals().getDescription() != null) {
//                                        //update
//                                    } else {
//                                        //create new
//                                    }
                                    } else {
                                        Toast.makeText(context, "Please fill the description first", Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();


                        }
                    });


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

                                        Log.d("id appriasal" + area.getTimes().get(finalJ).getAppraisals().getId(), "id indicator" + indicators.getData().get(item).getId());
                                        putAppraisals(area.getTimes().get(finalJ).getAppraisals().getId(), indicators.getData().get(item).getId());

//                                        itemAreaTables.get(0).setIndicator(indicators.getData().get(item).getAttributes().getCode());
//                                        itemAreaTables.get(0).save();

                                        tvRate.setText(indicators.getData().get(item).getAttributes().getCode());

                                        dialog.dismiss();
                                    }
                                }
                            });
                            builder.show();


                        }

                    });
                }
                holder.parent.addView(view);
            }
        } else if (role.equals(Constant.EXTRAS.HELPER)) {
            for (int j = 0; j < area.getTimes().size(); j++) {

                // final List<ItemAreaTable> itemAreaTables = SugarRecord.find(ItemAreaTable.class, "idtimes = ?", Integer.toString(area.getTimes().get(j).getId()));
                LayoutInflater i = LayoutInflater.from(context);
                View view = i.inflate(R.layout.child_helper, holder.parent, false);
                TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
                tvTime.setText(area.getTimes().get(j).getTime());

                CheckBox chxTime = (CheckBox) view.findViewById(R.id.chx_time);

                if (area.getTimes().get(j).getAppraisals() != null) {
                    chxTime.setChecked(area.getTimes().get(j).getAppraisals().getChecked());

                } else {
                    final int finalJ = j;
                    chxTime.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (((CheckBox) v).isChecked()) {
                                postAppraisals(area.getTimes().get(finalJ).getId());
//                            Toast.makeText(context,
//                                    "Checked" + area.getAttributes().getTimes().get(finalJ).getTime(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }


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

    private void putAppraisalsDesc(int time_id, String description) {
        if (CommonUtils.isNetworkAvailable(context)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(context);
            DataPreferences dataPreferences = new DataPreferences(context);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().putAppraisalsDescription(loginSession.getAuthToken(), time_id, description).enqueue(new Callback<AppraisalsResponse>() {
                @Override
                public void onResponse(Call<AppraisalsResponse> call, final Response<AppraisalsResponse> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {

                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Constant.EXTRAS.RESUMEDATA);
                        context.sendBroadcast(intent);

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

