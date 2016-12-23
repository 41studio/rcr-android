package com.fourtyonestudio.rcr.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.Area;
import com.fourtyonestudio.rcr.models.Item;
import com.fourtyonestudio.rcr.ui.adapter.ItemAdapter;
import com.fourtyonestudio.rcr.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemListingActivity extends AppCompatActivity {

    @Bind(R.id.tvCurrentDate)
    TextView tvCurrentDate;
    @Bind(R.id.rvItem)
    RecyclerView rvItem;
    private List<Item> itemList;
    private Area area;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_listing);
        ButterKnife.bind(this);
        tvCurrentDate.setText(DateUtils.getDateNow());

        GridLayoutManager layoutManager = new GridLayoutManager(ItemListingActivity.this, 2);
        rvItem.setLayoutManager(layoutManager);
        rvItem.setHasFixedSize(true);

        itemList = new ArrayList<>();
        adapter = new ItemAdapter(ItemListingActivity.this, itemList);
        adapter.notifyDataSetChanged();
        rvItem.setAdapter(adapter);
        getAreaDetails();
    }

    private void getAreaDetails() {
        if (getIntent().hasExtra(Constant.EXTRAS.AREA)) {
            area = (Area) getIntent().getExtras().get(Constant.EXTRAS.AREA);
            itemList.addAll(area.getAttributes().getItemList());
            adapter.notifyDataSetChanged();
        }
    }
}
