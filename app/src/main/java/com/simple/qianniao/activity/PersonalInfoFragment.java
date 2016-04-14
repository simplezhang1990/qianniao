package com.simple.qianniao.activity;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.simple.qianniao.R;
import com.simple.qianniao.view.UITableView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalInfoFragment extends Fragment {

    private UITableView mPersonInfoUITableView;
    private Button mPersonalTakePicture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);
        mPersonInfoUITableView = (UITableView) view.findViewById(R.id.personinfo_UITableView);
        mPersonalTakePicture = (Button)view.findViewById(R.id.personal_upload_headimage_button);
        mPersonInfoUITableView.addBasicItem("example1");
        mPersonInfoUITableView.addBasicItem("example2", "summary");

        mPersonInfoUITableView.addBasicItem(R.drawable.image20, "example3", "summary");
        mPersonInfoUITableView.addBasicItem("example4", "summary", R.color.CadetBlue);
        mPersonInfoUITableView.commit();
        mPersonalTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),PickPictureActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
