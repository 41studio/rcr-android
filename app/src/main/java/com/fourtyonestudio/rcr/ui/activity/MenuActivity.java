package com.fourtyonestudio.rcr.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.preferences.DataPreferences;
import com.fourtyonestudio.rcr.tables.ItemAreaTable;
import com.orm.SugarRecord;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MenuActivity extends AppCompatActivity {

    @Bind(R.id.btnArea)
    Button btnArea;
    @Bind(R.id.btnAddUser)
    Button btnAddUser;
    @Bind(R.id.btnLogout)
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        String role = new DataPreferences(this).getLoginSession().getUser().getRole();
        if (role.equals(Constant.EXTRAS.MANAGER)) {
            btnAddUser.setVisibility(View.VISIBLE);
        } else if (role.equals(Constant.EXTRAS.HELPER)) {
            btnAddUser.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btnLogout)
    public void clickLogout(View view) {
        //SugarRecord.deleteAll(ItemAreaTable.class);
        new DataPreferences(this).clear();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnAddUser)
    public void clickAddUser(View view) {
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnArea)
    public void clickArea(View view) {
        Intent intent = new Intent(this, AreaListingActivity.class);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
