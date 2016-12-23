package com.fourtyonestudio.rcr.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.Indicator;
import com.fourtyonestudio.rcr.models.Item;
import com.fourtyonestudio.rcr.models.ItemTimes;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mohamadsodiq on 12/13/16.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> list = new ArrayList<>();
    private Context context;

    public ItemAdapter(Context context, List<Item> list) {
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
        final Item item = list.get(holder.getAdapterPosition());
        holder.tvName.setText(item.getName());
        //dummy, hapus klo API dah d bnerin
        if (item.getTimesList().isEmpty()) {
            item.setTimesList(ItemTimes.getDummyItemTimes());
        }else{

        }

        holder.tvTime1.setText(item.getTimesList().get(0).getTime());
        if (item.getTimesList().get(0).getIndicator() != null) {
            holder.tvRate1.setText(item.getTimesList().get(0).getIndicator().getCode());
        } else {
            holder.tvRate1.setText("");
        }
        holder.tvTime2.setText(item.getTimesList().get(1).getTime());
        if (item.getTimesList().get(1).getIndicator() != null) {
            holder.tvRate2.setText(item.getTimesList().get(1).getIndicator().getCode());
        } else {
            holder.tvRate2.setText("");
        }
        holder.tvRate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giveRates(position, 0);
            }
        });
        holder.tvRate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giveRates(position, 1);
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

        @Bind(R.id.tvTime1)
        TextView tvTime1;
        @Bind(R.id.tvRate1)
        TextView tvRate1;
        @Bind(R.id.tvTime2)
        TextView tvTime2;
        @Bind(R.id.tvRate2)
        TextView tvRate2;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void giveRates(final int indexItem, final int indexTime) {
        final CharSequence[] items = new CharSequence[Indicator.getDummyIndicators().size() + 1];
        for (int i = 0; i < Indicator.getDummyIndicators().size(); i++) {
            items[i] = Indicator.getDummyIndicators().get(i).getCode() + " - " + Indicator.getDummyIndicators().get(i).getDescription();
        }
        items[Indicator.getDummyIndicators().size()] = "Cancel";

        AlertDialog.Builder builder = new AlertDialog.Builder(
                context);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item < Indicator.getDummyIndicators().size()) {
                    list.get(indexItem).getTimesList().get(indexTime).setIndicator(Indicator.getDummyIndicators().get(item));
                    notifyDataSetChanged();
                }
                dialog.dismiss();
            }
        });
        builder.show();

    }
}
