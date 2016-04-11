package com.simple.qianniao.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.simple.qianniao.R;
import com.simple.qianniao.modul.CustomerSQLiteHelper;
import com.simple.qianniao.view.ProgressDialog;
import com.simple.qianniao.view.RoundImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private static final String SHAREDPREFERENCES_NAME = "qianniao_sharedpreferences";
    private static final String SHAREDPREFERENCES_USERNAME = "username";
    private static final String SHAREDPREFERENCES_PASSWORD = "password";
    private static final int REQUEST_CODE_REGISTER = 99;
    private static final int RESULT_OK = 100;
    private static final String RESULT_USERNAME = "username";
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private TextView mRegisterTextView;
    private TextView mForgetPasswordTextView;
    private TextView mErrorMessageTextView;
    private Button mLoginButton;
    private Dialog mProgressDialog;
    private CustomerSQLiteHelper mCustomerSQLiteHelper;
    private AsyncTask mLoginAsynTask;
    private SharedPreferences mSharedPreferences;
    private String mSharedPreference_Username, mSharedPreference_Password;
    private boolean mPasswordCorrect;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomerSQLiteHelper = CustomerSQLiteHelper.get(getContext());
        mSharedPreferences = getActivity().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        mSharedPreference_Username = mSharedPreferences.getString(SHAREDPREFERENCES_USERNAME,"");
        mSharedPreference_Password = mSharedPreferences.getString(SHAREDPREFERENCES_PASSWORD,"");
        Log.i(TAG,"******** username = "+mSharedPreference_Username+"   password = "+mSharedPreference_Password);
    }

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
                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                if (null == username || "".equals(username) || null == password || "".equals(password)) {
                    mErrorMessageTextView.setText(R.string.login_empty_error);
                } else {
                    performLogin(username, password);
                }
            }
        });
        return view;
    }

    private void performLogin(String username, String password) {
        showProgressDialog();
        if (mLoginAsynTask != null && mLoginAsynTask.getStatus() != AsyncTask.Status.FINISHED)
            mLoginAsynTask.cancel(true);
        mLoginAsynTask = new LoginAsynTask().execute(new String[]{username, password});



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_REGISTER:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String username = bundle.getString(RESULT_USERNAME, "");
                    mUsernameEditText.setText(username);
                }
                break;
        }
    }

    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getContext(), R.style.dialog);
        mProgressDialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams wmlp = mProgressDialog.getWindow().getAttributes();
        Point p = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(p);
        double y = p.y * 0.25;
        wmlp.y = (int) y;
        mProgressDialog.show();
    }

    private void clearProgressDialog() {
        mProgressDialog.cancel();
        mProgressDialog = null;
    }

    class LoginAsynTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String[] params) {
            return mCustomerSQLiteHelper.customerLogin(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mPasswordCorrect = aBoolean;
            logintoMainPage();
        }
    }

    private void logintoMainPage() {
        if (mPasswordCorrect) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(SHAREDPREFERENCES_USERNAME, mUsernameEditText.getText().toString());
            editor.putString(SHAREDPREFERENCES_PASSWORD, mPasswordEditText.getText().toString());
            editor.commit();
            clearProgressDialog();
            Intent intent = new Intent(getContext(), MainPageActivity.class);
            intent.putExtra(RESULT_USERNAME, mUsernameEditText.getText().toString());
            startActivity(intent);
        } else {
            clearProgressDialog();
            mErrorMessageTextView.setText(R.string.login_password_error);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!"".equals(mSharedPreference_Username)&&!"".equals(mSharedPreference_Password)){
            mUsernameEditText.setText(mSharedPreference_Username);
            mPasswordEditText.setText(mSharedPreference_Password);
            performLogin(mSharedPreference_Username,mSharedPreference_Password);

        }


    }
}
