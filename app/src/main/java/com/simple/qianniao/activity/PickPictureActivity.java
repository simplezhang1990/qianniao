package com.simple.qianniao.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.simple.qianniao.PickPictureFragment;

public class PickPictureActivity extends SimgleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PickPictureFragment();
    }

}
