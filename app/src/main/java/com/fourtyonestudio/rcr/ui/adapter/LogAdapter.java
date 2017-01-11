package com.fourtyonestudio.rcr.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.LogDataDatum;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Riris.
 */

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {

    private List<LogDataDatum> areaList = new ArrayList<>();
    private Context context;

    public LogAdapter(Context context, List<LogDataDatum> list) {
        this.context = context;
        this.areaList = list;
    }

    @Override
    public LogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        return new LogAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LogAdapter.ViewHolder holder, final int position) {
        final LogDataDatum area = areaList.get(holder.getAdapterPosition());
        holder.tvName.setText(area.getAttributes().getContent());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(context, AreaItemListingActivity.class);
//                intent.putExtra(Constant.EXTRAS.ID_AREA, area.getId());
//                context.startActivity(intent);
//            }
//        });
//
//
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Intent intent = new Intent(context, EditAreaActivity.class);
//                intent.putExtra(Constant.EXTRAS.NAME_AREA, area.getAttributes().getName());
//                intent.putExtra(Constant.EXTRAS.ID_AREA, area.getId());
//                context.startActivity(intent);
//                return false;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return areaList == null ? 0 : areaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvName)
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
