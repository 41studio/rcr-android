package com.fourtyonestudio.rcr.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.fourtyonestudio.rcr.R;

import java.util.ArrayList;

/**
 * Created by Riris.
 */

public class TimeAdapter extends BaseAdapter {

    private static final String LOG_TAG = TimeAdapter.class.getSimpleName();

    private Context context_;
    private ArrayList<String> items;

    public TimeAdapter(Context context, ArrayList<String> items) {
        this.context_ = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context_.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.item_time, null);
        }

//        EditText tv = (EditText) convertView.findViewById(R.id.tv);
//        String text = items.get(position);
//        tv.setText(text);

//        tv.requestFocus();

        return convertView;
    }
}