package com.fourtyonestudio.rcr.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.Area;
import com.fourtyonestudio.rcr.models.Indicators;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.tables.ItemAreaTable;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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

        for (int j = 0; j < area.getAttributes().getTimes().size(); j++) {

            final List<ItemAreaTable> itemAreaTables = SugarRecord.find(ItemAreaTable.class, "idtimes = ?", Integer.toString(area.getAttributes().getTimes().get(j).getId()));


            LayoutInflater i = LayoutInflater.from(context);
            View view = i.inflate(R.layout.child, holder.parent, false);
            TextView correctAnswer = (TextView) view.findViewById(R.id.tvTime);
            correctAnswer.setText(area.getAttributes().getTimes().get(j).getTime());

            final TextView tvRate = (TextView) view.findViewById(R.id.tvRate);

            if (itemAreaTables.get(0).getIndicator() != null) {
                tvRate.setText(itemAreaTables.get(0).getIndicator());
            }
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

                    Log.d("null", items.length + "");


                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            context);
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {

                            itemAreaTables.get(0).setIndicator(indicators.getData().get(item).getAttributes().getCode());
                            itemAreaTables.get(0).save();

                            tvRate.setText(indicators.getData().get(item).getAttributes().getCode());

                            dialog.dismiss();
                        }
                    });
                    builder.show();

                }
            });
            holder.parent.addView(view);
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

}
