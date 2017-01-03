package com.fourtyonestudio.rcr.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fourtyonestudio.rcr.Constant;
import com.fourtyonestudio.rcr.R;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.preferences.DataPreferences;

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
    @Bind(R.id.btnSetting)
    Button btnSetting;
    @Bind(R.id.tv_welcome)
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        String role = new DataPreferences(this).getLoginSession().getUser().getRole();
        if (role.equals(Constant.EXTRAS.HELPER)) {
            btnAddUser.setVisibility(View.GONE);
        } else {
            btnAddUser.setVisibility(View.VISIBLE);
        }

        DataPreferences dataPreferences = new DataPreferences(this);
        LoginSession loginSession = dataPreferences.getLoginSession();
        if(loginSession.getUser().getName() != null){
            tvWelcome.setText("Welcome "+loginSession.getUser().getName());
        }else{
            tvWelcome.setText("Welcome User");
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
        Intent intent = new Intent(this, UserListingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnArea)
    public void clickArea(View view) {
        Intent intent = new Intent(this, AreaListingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnSetting)
    public void clickSetting(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
