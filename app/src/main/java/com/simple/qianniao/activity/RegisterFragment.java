
package com.simple.qianniao.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.simple.qianniao.R;
import com.simple.qianniao.modul.Customer;
import com.simple.qianniao.modul.CustomerSQLiteHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterFragment extends Fragment {
    private static final String TAG = "RegisterFragment";
    private static final int RESULT_OK = 100;
    private static final String RESULT_USERNAME = "username";
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mAlisEditText;
    private EditText mConfirmPasswordEditText;
    private NumberPicker mAgePicker;
    private RadioButton mSexRadioMan;
    private RadioButton mSexRadioWoman;
    private Button mRegisterButton;
    private TextView mErrorMessageTextView;
    private CustomerSQLiteHelper mCustomerDBHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomerDBHelper = CustomerSQLiteHelper.get(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mUsernameEditText = (EditText) view.findViewById(R.id.register_username_edittext);
        mPasswordEditText = (EditText) view.findViewById(R.id.register_password_edittext);
        mAlisEditText = (EditText) view.findViewById(R.id.register_alia_edittext);
        mConfirmPasswordEditText = (EditText) view.findViewById(R.id.register_confirm_password_edittext);
        mAgePicker = (NumberPicker) view.findViewById(R.id.register_age_picker);
        mSexRadioMan = (RadioButton)view.findViewById(R.id.register_radio_man);
        Drawable drawable = getResources().getDrawable(R.drawable.man);
        drawable.setBounds(0,0,55,60);
        mSexRadioMan.setCompoundDrawables(drawable,null,null,null);
        mSexRadioWoman = (RadioButton)view.findViewById(R.id.register_radio_woman);
        drawable = getResources().getDrawable(R.drawable.woman);
        drawable.setBounds(0, 0, 45, 55);
        mSexRadioWoman.setCompoundDrawables(drawable,null,null,null);
        mRegisterButton = (Button) view.findViewById(R.id.register_button);
        mErrorMessageTextView = (TextView) view.findViewById(R.id.register_error_message_textview);
        mAgePicker.setMaxValue(100);
        mAgePicker.setMinValue(14);
        mAgePicker.setValue(18);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameEditText.getText().toString();
                String alis = mAlisEditText.getText().toString();
                String age = String.valueOf(mAgePicker.getValue());
                String sex;
                if (mSexRadioMan.isChecked()) {
                    sex = getResources().getString(R.string.register_sex_man);
                } else {
                    sex = getResources().getString(R.string.register_sex_woman);
                }
                String password = mPasswordEditText.getText().toString();
                String confirmpassword = mConfirmPasswordEditText.getText().toString();
                if (null == username || "".equals(username)) {
                    mErrorMessageTextView.setText(R.string.register_username_empty_error);
                } else if (!isMobileNO(username)) {
                    mErrorMessageTextView.setText(R.string.register_username_invalid_error);
                } else if (null != mCustomerDBHelper.searchCustomer(username)) {
                    mErrorMessageTextView.setText(R.string.register_username_duplicate_error);
                } else if (null == alis || "".equals(alis)) {
                    mErrorMessageTextView.setText(R.string.register_alia_empty_error);
                } else if (null == password || "".equals(password)) {
                    mErrorMessageTextView.setText(R.string.register_password_empty_error);
                } else if (password.length() < 8 || 16 < password.length()) {
                    mErrorMessageTextView.setText(R.string.register_password_format_error);
                } else if (null == confirmpassword || "".equals(confirmpassword)) {
                    mErrorMessageTextView.setText(R.string.register_confirm_password_empty_error);
                } else if (!password.equals(confirmpassword)) {
                    mErrorMessageTextView.setText(R.string.register_password_notsame_error);
                } else {
                    Log.i(TAG, "username = " + username + " alis = " + alis + " age = " +
                            age + " sex = " + sex + " password = " + password + " confirmpassword = " + confirmpassword);
                    long result = mCustomerDBHelper.insertCustomer(new Customer(username, alis, age, sex, password));
                    if (-1 == result) {
                        mErrorMessageTextView.setText(R.string.register_failed_error);
                    } else {
                        Toast.makeText(getContext(), R.string.register_success, Toast.LENGTH_LONG).show();
                        Bundle bundle = new Bundle();
                        bundle.putString(RESULT_USERNAME, username);
                        Intent intent = new Intent();
                        intent.putExtras(bundle);
                        getActivity().setResult(RESULT_OK, intent);
                        getActivity().finish();
                    }
                }
            }
        });
        return view;
    }
    private boolean isMobileNO(String username) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(username);
        return m.matches();
    }

}

