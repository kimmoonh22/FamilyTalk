package com.example.a0104.mhtalk.AppGuide;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.a0104.mhtalk.R;
import com.tsengvn.typekit.TypekitContextWrapper;

public class GuideActivity extends AppCompatActivity {

    ViewPager mViewPager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        mViewPager = (ViewPager)findViewById(R.id.pager);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

    }

    public class MyViewPagerAdapter extends FragmentStatePagerAdapter
    {
        Fragment[] fragments = new Fragment[3];
        Bundle frag[] = new Bundle[3];
        public MyViewPagerAdapter (FragmentManager fm) {
            super(fm);

            for(int i=0; i<fragments.length; i++)
            {
                frag[i] = new Bundle();
                frag[i].putInt("idx", i);
                fragments[i] = new GuideFragment();
                fragments[i].setArguments(frag[i]);
            }
        }

        public Fragment getItem(int arg0) {
            return fragments[arg0];
        }

        public int getCount() {
            return fragments.length;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) { super.attachBaseContext(TypekitContextWrapper.wrap(newBase));}
}
