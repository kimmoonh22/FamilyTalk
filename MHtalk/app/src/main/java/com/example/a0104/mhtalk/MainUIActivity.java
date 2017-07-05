package com.example.a0104.mhtalk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a0104.mhtalk.DB.DBHelper;
import com.example.a0104.mhtalk.Dialogs.CustomDialog;
import com.example.a0104.mhtalk.MapGPS.MapActivity;
import com.example.a0104.mhtalk.Services.GPSProvider;
import com.tsengvn.typekit.TypekitContextWrapper;

public class MainUIActivity extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper;
    String name;
    int icon;
    ImageView myicon;
    Button map, cal, msg;
    TextView myname;
    CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        myicon = (ImageView) findViewById(R.id.my_icon); myicon.setOnClickListener(this);
        myname = (TextView) findViewById(R.id.my_name);
        map = (Button) findViewById(R.id.map_btn); map.setOnClickListener(this);
        cal = (Button) findViewById(R.id.cal_btn); cal.setOnClickListener(this);
        msg = (Button) findViewById(R.id.msg_btn); msg.setOnClickListener(this);

        getUser();

        myname.setText(name);
        myicon.setImageResource(icon);

        try {
            Intent in = new Intent(MainUIActivity.this, GPSProvider.class);
            startService(in);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_btn:
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
                break;
            case R.id.cal_btn:
                break;
            case R.id.msg_btn:
                break;
            case R.id.my_icon:
                dialog = new CustomDialog(v.getContext(),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // btnON click listener
                                startService(new Intent(v.getContext(), GPSProvider.class));
                                dialog.cancel();
                            }
                        }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // btnOFF click listener
                        stopService(new Intent(v.getContext(), GPSProvider.class));
                        dialog.cancel();
                    }
                }, "위치 추적\n1시간 간격으로 위치를 추적\n(배터리 소모량이 증가합니다)");
                dialog.show();
                break;
        }
    }

    private void getUser() {
        dbHelper = new DBHelper(getApplicationContext(), "UserInfo.db", null, 1);
        if(dbHelper.getResult().get("usrid").equals("kjb")) {
            name = "김종복";
            icon = R.drawable.kjb_selector;
        }
        else if(dbHelper.getResult().get("usrid").equals("kmh")) {
            name = "김문희";
            icon = R.drawable.kmh_selector;
        }
        else if(dbHelper.getResult().get("usrid").equals("kkt")) {
            name = "김규태";
            icon = R.drawable.kkt_selector;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) { super.attachBaseContext(TypekitContextWrapper.wrap(newBase));}
}
