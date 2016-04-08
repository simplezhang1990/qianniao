package com.simple.qianniao.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainPageActivity extends SimgleFragmentActivity {

    private MainPageFragment mFragment;

    @Override
    protected Fragment createFragment() {

        mFragment = new MainPageFragment();
        return mFragment;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        mFragment.onBackPressed();
    }
}
