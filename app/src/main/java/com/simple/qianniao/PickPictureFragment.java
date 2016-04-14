package com.simple.qianniao;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Baiming.Zhang on 4/14/2016.
 */
public class PickPictureFragment extends Fragment {
    private static final String TAG = "PickPictureFragment";
    private Button mTakePicture;
    private Button mPickePicture;
    private LinearLayout mLinearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_picture, container, false);
        mTakePicture = (Button) view.findViewById(R.id.personal_take_picture);
        mPickePicture = (Button) view.findViewById(R.id.personal_pick_picture);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.personal_pick_picture_linearlayout);
        mTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG,"   Linear height:" + mLinearLayout.getHeight());
            }
        });
        return view;
    }

    private Animation initAnimation() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        Animation animation = new TranslateAnimation(0, 0, 212, 0);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(500);
        return animation;
    }

    @Override
    public void onResume() {
        super.onResume();
        mLinearLayout.startAnimation(initAnimation());
    }
}
