package com.simple.qianniao.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.simple.qianniao.R;

/**
 * Created by Baiming.Zhang on 4/7/2016.
 */
public class ProgressDialog extends Dialog {
    private ImageView mImageView;
    private TextView mTextView;
    public ProgressDialog(Context context) {
        super(context);
    }

    public ProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        View view = getLayoutInflater().inflate(R.layout.customer_progress_dialog,null);
        mImageView = (ImageView)view.findViewById(R.id.progress_imageview);
        mTextView = (TextView)view.findViewById(R.id.progress_text);
        mTextView.setText("Loading...");
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_ainmation);
        LinearInterpolator lin = new LinearInterpolator();
        animation.setInterpolator(lin);
        if (animation != null)
            mImageView.setAnimation(animation);
        setContentView(view);
    }
}
