package com.simple.qianniao.activity;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.simple.qianniao.R;
import com.simple.qianniao.view.ProgressDialog;
import com.simple.qianniao.view.RoundImageView;




/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private static final int REQUEST_CODE_REGISTER = 99;
    private static final int RESULT_OK = 100;
    private static final String RESULT_USERNAME = "username";
    private RoundImageView mHeadImage;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private TextView mRegisterTextView;
    private TextView mForgetPasswordTextView;
    private TextView mErrorMessageTextView;
    private Button mLoginButton;
    private ImageView mProgressImageView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mUsernameEditText = (EditText) view.findViewById(R.id.login_username_editview);
        mPasswordEditText = (EditText) view.findViewById(R.id.login_password_editview);
        mRegisterTextView = (TextView) view.findViewById(R.id.login_register);
        mForgetPasswordTextView = (TextView) view.findViewById(R.id.login_forget_password);
        mErrorMessageTextView = (TextView) view.findViewById(R.id.login_error_message);
        mLoginButton = (Button) view.findViewById(R.id.login_button);

        mRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivityForResult(intent, REQUEST_CODE_REGISTER);
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), MainPageActivity.class);
//                startActivity(intent);
                initDynamicBox();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_REGISTER:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String username = bundle.getString(RESULT_USERNAME, "");
                    Log.i(TAG, "username from register page is " + username);
                    mUsernameEditText.setText(username);
                }
                break;
        }
    }

    private void initDynamicBox() {
        Dialog dialog = new ProgressDialog(getContext(),R.style.dialog);

        dialog.show();


    }

}
