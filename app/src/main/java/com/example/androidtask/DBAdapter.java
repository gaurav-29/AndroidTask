package com.example.androidtask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBAdapter extends SQLiteOpenHelper {

    SQLiteDatabase database;
    TextView txtdepartment;

    public static final String DATABASE = "empdatabase.db";
    public static final String ANDROID = "android";
    public static final String IOS = "ios";
    public static final String WEB = "web";

    private static final String CREATE_ANDROID = "create table IF NOT EXISTS " + ANDROID +
            "(_id integer PRIMARY KEY AUTOINCREMENT, employee_email1 text)";
    private static final String CREATE_IOS = "create table IF NOT EXISTS " + IOS +
            "(_id integer PRIMARY KEY AUTOINCREMENT, employee_email2 text)";
    private static final String CREATE_WEB = "create table IF NOT EXISTS " + WEB +
            "(_id integer PRIMARY KEY AUTOINCREMENT, employee_email3 text)";

    public static final int VERSION = 1;
    private Context ctx;

    public DBAdapter(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE, factory, VERSION);
        // Database will be created here.
        ctx = context;
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // This method will run only first time we run project in mobile or after uninstalling app from mobile. So below log.d(CREATE_EMPLOYEE)
        // and log.d("Table Created") will be shown only first time.

        // this method will be called before above constructor, so "db" object of SQLiteDatabase class (as mentiond this method argument) is used to execute execSQL() method, instead of "database" object.
        // Tables will be created here.
        // Error came- no such table android found, when I deleted EMPLOYEE table and created ANDROID, IOS, WEB tables. So I uninstalled the app from
        //  mobile and again run the app in mobile, so error not came.
        log.d(CREATE_ANDROID);
        log.d(CREATE_IOS);
        log.d(CREATE_WEB);
        try
        {
            db.execSQL(CREATE_ANDROID);      // table will be created
            db.execSQL(CREATE_IOS);      // table will be created
            db.execSQL(CREATE_WEB);      // table will be created
        }
        catch(SQLiteException error)
        {
            log.e(error.getMessage());
            Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_LONG).show();
        }
        log.d("Tables are Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table IF EXISTS " + ANDROID);
        sqLiteDatabase.execSQL("drop table IF EXISTS " + IOS);
        sqLiteDatabase.execSQL("drop table IF EXISTS " + WEB);
        onCreate(sqLiteDatabase);
    }

    public boolean insert_data_android(String employeeemail) {
        // This method will be called from onClickListener of btnsave and response(true or false) will be returned to it.
        // Insert Query(Second approach)
        ContentValues values = new ContentValues();
        values.put("employee_email1", employeeemail);

        long response = database.insert(ANDROID,null,values);
        if(response == -1)    // Bcoz insert query returns -1, if error occurs.
        {
            return false;
        }
        else
        {
            return true;
        }
    }

  /*  public boolean insert_data_ios(String employeeemail) {
        // This method will be called from onClickListener of btnsave and response(true or false) will be returned to it.
        // Insert Query(Second approach)
        ContentValues values = new ContentValues();
        values.put("employee_email2", employeeemail);

        long response = database.insert(IOS,null,values);
        if(response == -1)    // Bcoz insert query returns -1, if error occurs.
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean insert_data_web(String employeeemail) {
        // This method will be called from onClickListener of btnsave and response(true or false) will be returned to it.
        // Insert Query(Second approach)
        ContentValues values = new ContentValues();
        values.put("employee_email3", employeeemail);

        long response = database.insert(WEB,null,values);
        if(response == -1)    // Bcoz insert query returns -1, if error occurs.
        {
            return false;
        }
        else
        {
            return true;
        }
    }  */

    public Cursor view_data_android()
    {
        // Select query
        Cursor cursor = database.rawQuery("select * from " + ANDROID, null);    // First approach
        return cursor;
       /* String FieldList[] = {"_id","product_name","supplier_name","mobile","price","quantity"};   // Second approach
        Cursor cursor = database.query(DBAdapter.INVENTORY, FieldList,"_id=?", Condition, null, null, null);
        return cursor;*/
    }
/*
    public Cursor view_data_ios()
    {
        Cursor cursor = database.rawQuery("select * from " + IOS, null);    // First approach
        return cursor;
    }

    public Cursor view_data_web()
    {
        Cursor cursor = database.rawQuery("select * from " + WEB, null);    // First approach
        return cursor;
    }  */



    public void delete_data(String[] Condition)
    {
        // Delete query (Second approach)
        database.delete(ANDROID,"_id=?",Condition);
    }
}
