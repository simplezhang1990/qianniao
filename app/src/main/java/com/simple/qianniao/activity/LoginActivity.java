package com.simple.qianniao.activity;

import android.support.v4.app.Fragment;

public class LoginActivity extends SimgleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }


}
