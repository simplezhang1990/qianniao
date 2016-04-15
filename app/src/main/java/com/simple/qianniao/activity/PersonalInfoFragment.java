package com.simple.qianniao.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.simple.qianniao.R;
import com.simple.qianniao.modul.Customer;
import com.simple.qianniao.modul.CustomerSQLiteHelper;
import com.simple.qianniao.view.UITableView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalInfoFragment extends Fragment {

    private UITableView mPersonInfoUITableView;
    private Button mPersonalTakePicture;
    private CustomerSQLiteHelper mCustomerSQLiteHelper;
    private SharedPreferences mSharedPreferences;
    private String mSharedPreference_Username;
    private Customer mCurrentCustomer;
    private TextView mAlis, mAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);
        mPersonInfoUITableView = (UITableView) view.findViewById(R.id.personinfo_UITableView);
        mPersonalTakePicture = (Button) view.findViewById(R.id.personal_upload_headimage_button);
        mAlis = (TextView) view.findViewById(R.id.personal_alis);
        mAccount = (TextView) view.findViewById(R.id.personal_account);
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
                startActivity(intent);
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
}
