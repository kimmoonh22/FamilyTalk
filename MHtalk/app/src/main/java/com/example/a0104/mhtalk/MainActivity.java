package com.example.a0104.mhtalk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.a0104.mhtalk.DB.DBHelper;
import com.tsengvn.typekit.TypekitContextWrapper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper;
    Button kjb, kmh, kkt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kjb = (Button) findViewById(R.id.father);
        kmh = (Button) findViewById(R.id.daughter);
        kkt = (Button) findViewById(R.id.son);

        kjb.setOnClickListener(this);
        kmh.setOnClickListener(this);
        kkt.setOnClickListener(this);

        dbHelper = new DBHelper(getApplicationContext(), "UserInfo.db", null, 1);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.father :
                dbHelper.insert("kjb", "y");
                break;
            case R.id.daughter :
                dbHelper.insert("kmh", "y");
                break;
            case R.id.son :
                dbHelper.insert("kkt", "y");
                break;
        }
        Intent in = new Intent(MainActivity.this, MainUIActivity.class);
        startActivity(in);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) { super.attachBaseContext(TypekitContextWrapper.wrap(newBase));}
}
