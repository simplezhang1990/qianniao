package com.simple.qianniao.activity;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.simple.qianniao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> mTitles = new ArrayList<String>();
    private int mBackButtonPressedTime = 0;
    private Handler mHandler = new Handler();
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mBackButtonPressedTime = 0;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.mainpage_viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.mainpage_tablayout);

        mTitles.add("头条");
        mTitles.add("我的");

        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(0)), true);
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(1)));

        MyFragmentPagerAdapter mFragmentAdapter = new MyFragmentPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        TabLayout.Tab tab = mTabLayout.getTabAt(0);
        tab.setIcon(R.drawable.homepage_check);
        tab = mTabLayout.getTabAt(1);
        tab.setIcon(R.drawable.personal_profile_uncheck);

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    tab.setIcon(R.drawable.personal_profile_check);
                } else if (tab.getPosition() == 0) {
                    tab.setIcon(R.drawable.homepage_check);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    tab.setIcon(R.drawable.personal_profile_uncheck);
                } else if (tab.getPosition() == 0) {
                    tab.setIcon(R.drawable.homepage_uncheck);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PersonalInfoFragment();
                case 1:
                    return new RegisterFragment();
                default:
                    return new PersonalInfoFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }


    public void onBackPressed() {
        mBackButtonPressedTime++;
        if (mBackButtonPressedTime == 2) {
            getActivity().finishAffinity();
            return;
        }
        Toast.makeText(getContext(), R.string.press_back_twice_to_exist, Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }
}
