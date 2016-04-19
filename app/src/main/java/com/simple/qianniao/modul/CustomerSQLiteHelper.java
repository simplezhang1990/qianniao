package com.simple.qianniao.modul;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Baiming.Zhang on 4/6/2016.
 */
public class CustomerSQLiteHelper extends SQLiteOpenHelper {
    private static  CustomerSQLiteHelper dbHelper;
    private static final int VERSION = 1;
    private static final String CUSTOMER_TABLE = "customer";
    private static final String CREATE_CUSTOMER_TABLE = "create table customer(" +
            "username varchar(20) primary key," +
            "alis varchar(50), " +
            "age varchar(20)," +
            "sex varchar(10)," +
            "password varchar(200)," +
            "head_image varchar(200))";
    private  SQLiteDatabase mDB;

    public CustomerSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static CustomerSQLiteHelper get(Context context) {
        if (dbHelper == null) {
            dbHelper = new CustomerSQLiteHelper(context, "qianniao_customer_db", null, VERSION);
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CUSTOMER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static ContentValues customerToContentValue(Customer customer){
        ContentValues values = new ContentValues();
        values.put("username",customer.getUsername());
        values.put("alis",customer.getAlia());
        values.put("age",customer.getAge());
        values.put("sex",customer.getSex());
        values.put("password",customer.getPassword());
        values.put("head_image",customer.getHeadImagePath());
        return values;
    }

    public  long insertCustomer(Customer customer){
        ContentValues values = customerToContentValue(customer);
        mDB = dbHelper.getWritableDatabase();
        return mDB.insert(CUSTOMER_TABLE, null, values);
    }

    public  void updateCustomer(Customer customer){
        ContentValues values = customerToContentValue(customer);
        mDB = dbHelper.getWritableDatabase();
        mDB.update(CUSTOMER_TABLE, values, "username=?", new String[]{String.valueOf(customer.getUsername())});
    }

    public  Customer searchCustomer(String username){
        String alis,age,sex,password,head_image;
        Customer customer = null;
        mDB = dbHelper.getReadableDatabase();
        Cursor cursor=mDB.query(CUSTOMER_TABLE, new String[]{ "username", "alis", "age", "sex", "password", "head_image"}, "username=?", new String[]{username}, null, null, null);
        if(cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                alis = cursor.getString(cursor.getColumnIndex("alis"));
                age = cursor.getString(cursor.getColumnIndex("age"));
                sex = cursor.getString(cursor.getColumnIndex("sex"));
                password = cursor.getString(cursor.getColumnIndex("password"));
                head_image = cursor.getString(cursor.getColumnIndex("head_image"));
                customer = new Customer(username, alis, age, sex, password, head_image);
            }
        }
        cursor.close();
        return customer;
    }

    public boolean customerLogin(String username,String password){
        mDB = dbHelper.getReadableDatabase();
        Cursor cursor=mDB.query(CUSTOMER_TABLE, null, "username=? and password=?", new String[]{username,password}, null, null, null);
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }
}
