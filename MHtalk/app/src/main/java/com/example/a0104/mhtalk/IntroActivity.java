package com.example.a0104.mhtalk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.a0104.mhtalk.AppGuide.GuideActivity;
import com.example.a0104.mhtalk.DB.DBHelper;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by a0104 on 2017-07-03.
 */

public class IntroActivity extends AppCompatActivity {
    Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "UserInfo.db", null, 1);

        if(dbHelper.getResult().get("isLogin").equals("y")) {
            in = new Intent(IntroActivity.this, MainUIActivity.class);
        }
        else {
            in = new Intent(IntroActivity.this, GuideActivity.class);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(in);
                finish();
            }
        }, 2000);
    }

    @Override
    protected void attachBaseContext(Context newBase) { super.attachBaseContext(TypekitContextWrapper.wrap(newBase));}
}
