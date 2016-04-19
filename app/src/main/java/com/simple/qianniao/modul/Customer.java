package com.simple.qianniao.modul;

import android.util.Log;

import java.util.UUID;

/**
 * Created by Baiming.Zhang on 4/6/2016.
 */
public class Customer {

    private String mUsername;
    private String mAlia;
    private String mAge;
    private String mSex;
    private String mPassword;
    private String mHeadImagePath;

    public String getUsername() {
        return mUsername;
    }

    public String getAlia() {
        return mAlia;
    }

    public void setAlia(String alia) {
        mAlia = alia;
    }

    public String getAge() {
        return mAge;
    }

    public void setAge(String age) {
        mAge = age;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(String sex) {
        mSex = sex;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public Customer(String username, String alis, String age, String sex, String password) {
        this(username, alis, age, sex, password, "");
    }

    public Customer(String username, String alis, String age, String sex, String password, String head_image) {

        mUsername = username;
        mAlia = alis;
        mAge = age;
        mSex = sex;
        mPassword = password;
        mHeadImagePath = head_image;
    }


    public String getHeadImagePath() {
        return mHeadImagePath;
    }

    public void setHeadImagePath(String headImagePath) {
        mHeadImagePath = headImagePath;
        Log.i("qianniao","set image path: "+headImagePath);
    }
}
