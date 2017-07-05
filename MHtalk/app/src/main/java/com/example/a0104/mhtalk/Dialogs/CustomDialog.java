package com.example.a0104.mhtalk.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.a0104.mhtalk.R;

/**
 * Created by a0104 on 2017-07-04.
 */

public class CustomDialog extends Dialog {

    Button btn_1;
    Button btn_2;
    TextView txt_dialog_title;
    String title;

    View.OnClickListener btn1listener;
    View.OnClickListener btn2listener;
    public CustomDialog(Context context, View.OnClickListener btn1listener, View.OnClickListener btn2listener, String title) {
        super(context);
        this.btn1listener = btn1listener;
        this.btn2listener = btn2listener;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        txt_dialog_title = (TextView)findViewById(R.id.txt_dialog_title);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_1.setOnClickListener(btn1listener);
        btn_2.setOnClickListener(btn2listener);
        txt_dialog_title.setText(title);
    }

}

