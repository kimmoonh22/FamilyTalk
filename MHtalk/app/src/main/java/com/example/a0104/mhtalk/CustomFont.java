package com.example.a0104.mhtalk;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by a0104 on 2017-07-04.
 */

public class CustomFont extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "nanumsquare.ttf"))
                .addBold(Typekit.createFromAsset(this, "nanumsquareeb.ttf"));
    }

}
