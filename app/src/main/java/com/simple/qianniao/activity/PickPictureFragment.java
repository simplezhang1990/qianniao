package com.simple.qianniao.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.simple.qianniao.R;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Baiming.Zhang on 4/14/2016.
 */
public class PickPictureFragment extends Fragment {
    private static final String TAG = "PickPictureFragment";
    public static final int TAKE_PHOTO = 1;
    public static final int PICK_PHOTO = 2;
    private Button mTakePicture;
    private Button mPickePicture;
    private Uri mImageUri;
    private String mFilename;
    private String mUsername;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUsername = getActivity().getIntent().getStringExtra(MainPageFragment.SHAREDPREFERENCES_CURRENTUSER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_picture, container, false);
        mTakePicture = (Button) view.findViewById(R.id.personal_take_picture);
        mPickePicture = (Button) view.findViewById(R.id.personal_pick_picture);
        mTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date(System.currentTimeMillis());
                mFilename = format.format(date);
                String bast_path = Environment.getExternalStorageDirectory().getAbsolutePath();
                bast_path =bast_path +"/"+ getActivity().getPackageName()+"/"+mUsername;
                Log.i(TAG,bast_path +"/"+mFilename + ".jpg");
                File path = new File(bast_path);
                if (!path.exists()) {
                    if (!path.mkdir()) {
                        Toast.makeText(getContext(),"Create Image folder failed",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                File outputImage = new File(bast_path, mFilename + ".jpg");
                mFilename = getActivity().getPackageName()+"/"+mUsername + "/" + mFilename + ".jpg";
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //将File对象转换为Uri并启动照相程序
                mImageUri = Uri.fromFile(outputImage);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //照相
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri); //指定图片输出地址
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
        mPickePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            Intent intent = new Intent();
            intent.putExtra(PersonalInfoFragment.PERSONAL_HEAD_IMAGE_PATH,mFilename);
            getActivity().setResult(Activity.RESULT_OK,intent);
            getActivity().finish();
        }
    }
}
