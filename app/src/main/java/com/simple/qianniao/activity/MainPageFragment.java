package com.simple.qianniao.activity;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
    private View view1, view2, view3, view4, view5;
    private List<View> mViewList = new ArrayList<View>();
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

        view1 = inflater.inflate(R.layout.fragment_login, null);
        view2 = inflater.inflate(R.layout.fragment_login, null);
        view3 = inflater.inflate(R.layout.fragment_login, null);
        view4 = inflater.inflate(R.layout.fragment_login, null);
        view5 = inflater.inflate(R.layout.fragment_login, null);
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);
        mViewList.add(view5);
        mTitles.add("头条");
        mTitles.add("热点");
        mTitles.add("本地");
        mTitles.add("财经");
        mTitles.add("科技");
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(0)), true);
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(4)));

        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        // mTabLayout.setTabsFromPagerAdapter(mAdapter);
        TabLayout.Tab tab = mTabLayout.getTabAt(0);
        tab.setIcon(R.drawable.image10);
        tab = mTabLayout.getTabAt(1);
        tab.setIcon(R.drawable.image11);
        tab = mTabLayout.getTabAt(2);
        tab.setIcon(R.drawable.image12);
        tab = mTabLayout.getTabAt(3);
        tab.setIcon(R.drawable.image13);
        tab = mTabLayout.getTabAt(4);
        tab.setIcon(R.drawable.image14);

        return view;
    }

    class MyPagerAdapter extends PagerAdapter {

        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

    }


    public void onBackPressed() {
        mBackButtonPressedTime++;
        if(mBackButtonPressedTime==2){
            getActivity().finishAffinity();
            return;
        }
        Toast.makeText(getContext(),R.string.press_back_twice_to_exist,Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable,2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mHandler!=null){
            mHandler.removeCallbacks(mRunnable);
        }
    }
}
