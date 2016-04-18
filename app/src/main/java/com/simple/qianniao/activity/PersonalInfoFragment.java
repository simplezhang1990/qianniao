package com.simple.qianniao.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.simple.qianniao.R;
import com.simple.qianniao.Util.ImageUtil;
import com.simple.qianniao.modul.Customer;
import com.simple.qianniao.modul.CustomerSQLiteHelper;
import com.simple.qianniao.view.RoundImageView;
import com.simple.qianniao.view.UITableView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalInfoFragment extends Fragment {

    public static final String PERSONAL_HEAD_IMAGE_PATH = "personal_head_image_path";
    private static final int PICK_PHOTO = 99;
    private UITableView mPersonInfoUITableView;
    private Button mPersonalTakePicture;
    private CustomerSQLiteHelper mCustomerSQLiteHelper;
    private SharedPreferences mSharedPreferences;
    private String mSharedPreference_Username;
    private Customer mCurrentCustomer;
    private TextView mAlis, mAccount;
    private RoundImageView mHeadImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);
        mPersonInfoUITableView = (UITableView) view.findViewById(R.id.personinfo_UITableView);
        mPersonalTakePicture = (Button) view.findViewById(R.id.personal_upload_headimage_button);
        mAlis = (TextView) view.findViewById(R.id.personal_alis);
        mAccount = (TextView) view.findViewById(R.id.personal_account);
        mHeadImage = (RoundImageView)view.findViewById(R.id.personal_head_ImageView);
        mPersonInfoUITableView.addBasicItem("example1");
        mPersonInfoUITableView.addBasicItem("example2", "summary");
        mPersonInfoUITableView.addBasicItem(R.drawable.image20, "example3", "summary");
        mPersonInfoUITableView.addBasicItem("example4", "summary", R.color.CadetBlue);
        mPersonInfoUITableView.commit();
        mPersonalTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PickPictureActivity.class);
                intent.putExtra(MainPageFragment.SHAREDPREFERENCES_CURRENTUSER,mSharedPreference_Username);
                startActivityForResult(intent,PICK_PHOTO);
            }
        });
        if (mCurrentCustomer != null) {
            mAccount.setText(getResources().getString(R.string.register_username)+": "  +mCurrentCustomer.getUsername());
            mAlis.setText(getResources().getString(R.string.register_alis)+": "  +mCurrentCustomer.getAlia());
        }else {
            Toast.makeText(getContext(),"customer info is null",Toast.LENGTH_SHORT).show();
        }
        if (!"".equals(mCurrentCustomer.getHeadImage())) {

        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomerSQLiteHelper = CustomerSQLiteHelper.get(getContext());
        mSharedPreferences = getActivity().getSharedPreferences(MainPageFragment.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        mSharedPreference_Username = mSharedPreferences.getString(MainPageFragment.SHAREDPREFERENCES_CURRENTUSER, "");
        mCurrentCustomer = mCustomerSQLiteHelper.searchCustomer(mSharedPreference_Username);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_PHOTO&&resultCode== Activity.RESULT_OK){
            String path = Environment.getExternalStorageDirectory() .getAbsolutePath()+"/"+data.getStringExtra(PERSONAL_HEAD_IMAGE_PATH);
            Bitmap bitmap = decodeSampledBitmap(path,mHeadImage);
            mHeadImage.setImageBitmap(bitmap);
        }
    }

    private Bitmap decodeSampledBitmap(String pathName, ImageView imageView) {
        int width = imageView.getMeasuredWidth();
        int height =imageView.getMeasuredHeight();
        return ImageUtil.decodeSampledBitmap(pathName, width, height);
    }
}
