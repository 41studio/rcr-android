package com.fourtyonestudio.rcr.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.models.UserData;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.ui.activity.EditUserActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Riris.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<UserData> list = new ArrayList<>();
    private Context context;

    public UserAdapter(Context context, List<UserData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserAdapter.ViewHolder holder, final int position) {
        final UserData userData = list.get(holder.getAdapterPosition());

        holder.tvName.setText(userData.getAttributes().getName());
        holder.tvEmail.setText(userData.getAttributes().getEmail());
        holder.tvRole.setText(userData.getAttributes().getRole());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                DataPreferences dataPreferences = new DataPreferences(context);
                LoginSession loginSession = dataPreferences.getLoginSession();

                if (loginSession.getUser().getRole().equals(Constant.EXTRAS.MANAGER)) {

                    if (userData.getAttributes().getRole().equals(Constant.EXTRAS.HELPER)) {
                        Intent intent = new Intent(context, EditUserActivity.class);
                        intent.putExtra(Constant.EXTRAS.NAME_AREA, userData.getAttributes().getName());
                        intent.putExtra(Constant.EXTRAS.ID_USER, userData.getId());
                        intent.putExtra(Constant.EXTRAS.ROLE_USER, userData.getAttributes().getRole());
                        intent.putExtra(Constant.EXTRAS.EMAIL_AREA, userData.getAttributes().getEmail());
                        context.startActivity(intent);

                    }

                } else if (loginSession.getUser().getRole().equals(Constant.EXTRAS.OWNER)) {
                    if (loginSession.getUser().getId() != Integer.parseInt(userData.getId())) {
                        Intent intent = new Intent(context, EditUserActivity.class);
                        intent.putExtra(Constant.EXTRAS.NAME_AREA, userData.getAttributes().getName());
                        intent.putExtra(Constant.EXTRAS.ID_USER, userData.getId());
                        intent.putExtra(Constant.EXTRAS.ROLE_USER, userData.getAttributes().getRole());
                        intent.putExtra(Constant.EXTRAS.EMAIL_AREA, userData.getAttributes().getEmail());
                        context.startActivity(intent);
                    }


                }

                return false;
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

        @Bind(R.id.tvRole)
        TextView tvRole;

        @Bind(R.id.tvEmail)
        TextView tvEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
