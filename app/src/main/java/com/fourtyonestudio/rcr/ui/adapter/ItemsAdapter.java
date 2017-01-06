package com.fourtyonestudio.rcr.ui.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.fourtyonestudio.rcr.utils.KeyboardUtils;
import com.fourtyonestudio.rcr.utils.UIHelper;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    private List<AreaItemList> areaItemLists = new ArrayList<>();
    private Context context;

    public ItemsAdapter(Context context, List<AreaItemList> list) {
        this.context = context;
        this.areaItemLists = list;
    }

    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        return new ItemsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ItemsAdapter.ViewHolder holder, final int position) {
        final AreaItemList itemList = areaItemLists.get(holder.getAdapterPosition());
        holder.tvName.setText(itemList.getName());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context, EditAreaItemActivity.class);
                intent.putExtra(Constant.EXTRAS.AREA, (Serializable) itemList);
                intent.putExtra(Constant.EXTRAS.ID_AREA, itemList.getId());
                context.startActivity(intent);
                return false;
            }
        });

        String role = new DataPreferences(context).getLoginSession().getUser().getRole();
        if (role.equals(Constant.EXTRAS.HELPER)) {
            for (int j = 0; j < itemList.getTimes().size(); j++) {

                LayoutInflater i = LayoutInflater.from(context);
                View view = i.inflate(R.layout.child_helper, holder.parent, false);

                TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
                tvTime.setText(itemList.getTimes().get(j).getTime());


                final NumberFormat formatter = new DecimalFormat("00");
                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int minute = Calendar.getInstance().get(Calendar.MINUTE);
                String time = formatter.format(hour) + ":" + formatter.format(minute);

                CheckBox chxTime = (CheckBox) view.findViewById(R.id.chx_time);
                if (itemList.getTimes().get(j).getAppraisals() != null) {
                    chxTime.setChecked(itemList.getTimes().get(j).getAppraisals().getChecked());

                } else {
                    final int finalJ = j;

                    String pattern = "HH:mm";
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);

                    try {
                        Date date1 = sdf.parse(itemList.getTimes().get(j).getTime());
                        Date date2 = sdf.parse(time);

                        if (date1.before(date2)) {
                            chxTime.setEnabled(true);
                            chxTime.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    if (((CheckBox) v).isChecked()) {
                                        postAppraisals(itemList.getTimes().get(finalJ).getId());
//                            Toast.makeText(context,
//                                    "Checked" + itemList.getAttributes().getTimes().get(finalJ).getTime(), Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                        } else {

                            chxTime.setEnabled(false);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

//                    if (Integer.parseInt(itemList.getTimes().get(j).getTime()) < hour) {
//                        chxTime.setEnabled(true);
//                        chxTime.setOnClickListener(new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                if (((CheckBox) v).isChecked()) {
//                                    postAppraisals(itemList.getTimes().get(finalJ).getId());
////                            Toast.makeText(context,
////                                    "Checked" + itemList.getAttributes().getTimes().get(finalJ).getTime(), Toast.LENGTH_LONG).show();
//                                }
//
//                            }
//                        });
//                    } else {
//                        chxTime.setEnabled(false);
//                    }

//                    String regexStr = "^[0-9]*$";
//
//                    if(itemList.getTimes().get(j).getTime().trim().matches(regexStr))
//                    {
//                        if (Integer.parseInt(itemList.getTimes().get(j).getTime()) < hour) {
//                            chxTime.setEnabled(true);
//                            chxTime.setOnClickListener(new View.OnClickListener() {
//
//                                @Override
//                                public void onClick(View v) {
//                                    if (((CheckBox) v).isChecked()) {
//                                        postAppraisals(itemList.getTimes().get(finalJ).getId());
////                            Toast.makeText(context,
////                                    "Checked" + itemList.getAttributes().getTimes().get(finalJ).getTime(), Toast.LENGTH_LONG).show();
//                                    }
//
//                                }
//                            });
//                        } else {
//                            chxTime.setEnabled(false);
//                        }
//                    }
//                    else{
//                        chxTime.setEnabled(false);
//                    }


                }


                holder.parent.addView(view);
            }
        } else {

            for (int j = 0; j < itemList.getTimes().size(); j++) {

                //final List<ItemAreaTable> itemAreaTables = SugarRecord.find(ItemAreaTable.class, "idtimes = ?", Integer.toString(itemList.getTimes().get(j).getId()));
                LayoutInflater i = LayoutInflater.from(context);
                View view = i.inflate(R.layout.child_manager, holder.parent, false);

                CheckBox chx = (CheckBox) view.findViewById(R.id.chx);
                chx.setChecked(false);
                chx.setEnabled(false);

                //Set time appriasals
                TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
                tvTime.setText(itemList.getTimes().get(j).getTime());

                final TextView tvRate = (TextView) view.findViewById(R.id.tvRate);

                if (itemList.getTimes().get(j).getAppraisals() != null) {

                    if (itemList.getTimes().get(j).getAppraisals().getChecked() != null) {
                        chx.setEnabled(true);
                        chx.setChecked(itemList.getTimes().get(j).getAppraisals().getChecked());
                    } else {
                        chx.setEnabled(false);
                        chx.setChecked(false);
                    }

                    //Set rate appriasals
                    if (itemList.getTimes().get(j).getAppraisals().getIndicator() != null) {
                        tvRate.setText(itemList.getTimes().get(j).getAppraisals().getIndicator());
                    }


                    final int finalJ = j;

                    tvRate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // custom dialog
                            final Dialog dialog = new Dialog(context);
                            dialog.setContentView(R.layout.dialog_rate);
                            dialog.setTitle("Description");

                            final EditText text = (EditText) dialog.findViewById(R.id.etDescription);
                            Button btnRate = (Button) dialog.findViewById(R.id.btnAddRate);
                            final Spinner spRate = (Spinner) dialog.findViewById(R.id.spRate);


                            DataPreferences dataPreferences = new DataPreferences(context);
                            final Indicators indicators = dataPreferences.getIndicator();

                            List<String> listItems = new ArrayList<String>();
                            for (int i = 0; i < indicators.getData().size(); i++) {
                                listItems.add(indicators.getData().get(i).getAttributes().getCode() + " - " + indicators.getData().get(i).getAttributes().getDescription());
                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, listItems);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spRate.setAdapter(dataAdapter);

                            int selectionPosition = dataAdapter.getPosition(itemList.getTimes().get(finalJ).getAppraisals().getIndicator() + " - " + itemList.getTimes().get(finalJ).getAppraisals().getIndicatorDescription());
                            spRate.setSelection(selectionPosition);

                            final String[] indicator = new String[1];
                            spRate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    indicator[0] = indicators.getData().get(i).getId();
//                                    putAppraisals(itemList.getTimes().get(finalJ).getAppraisals().getId(), indicators.getData().get(i).getId());
//                                    tvRate.setText(indicators.getData().get(i).getAttributes().getCode());

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                            if (itemList.getTimes().get(finalJ).getAppraisals().getDescription() != null) {
                                text.setText(itemList.getTimes().get(finalJ).getAppraisals().getDescription());
                            }

                            btnRate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    KeyboardUtils.hideSoftKeyboard(context, v);

                                    if (!TextUtils.isEmpty(text.getText().toString())) {
                                        putAppraisalsDesc(itemList.getTimes().get(finalJ).getAppraisals().getId(), text.getText().toString(), indicator[0]);
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(context, "Please fill the description first", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            dialog.show();


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
        return areaItemLists == null ? 0 : areaItemLists.size();
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
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(context, jObjError.getString(Constant.MESSAGE.ERROR_BODY), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
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

    private void putAppraisalsDesc(int time_id, String description, String indicator_id) {
        if (CommonUtils.isNetworkAvailable(context)) {
            final ProgressDialog pDialog = UIHelper.showProgressDialog(context);
            DataPreferences dataPreferences = new DataPreferences(context);
            LoginSession loginSession = dataPreferences.getLoginSession();
            new RestApi().getApi().putAppraisalsDescription(loginSession.getAuthToken(), time_id, description, indicator_id).enqueue(new Callback<AppraisalsResponse>() {
                @Override
                public void onResponse(Call<AppraisalsResponse> call, final Response<AppraisalsResponse> response) {
                    UIHelper.dismissDialog(pDialog);
                    if (response.isSuccessful()) {

                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Constant.EXTRAS.RESUMEDATA);
                        context.sendBroadcast(intent);

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(context, jObjError.getString(Constant.MESSAGE.ERROR_BODY), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
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
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(context, jObjError.getString(Constant.MESSAGE.ERROR_BODY), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

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

