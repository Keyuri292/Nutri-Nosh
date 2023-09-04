package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper
{
    public DBHelper( Context context) {
        super(context,"Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase Mydb) {
        Mydb.execSQL("create table Users(username Text Primary key,mobile_no Text,password Text,orderitem Text DEFAULT NULL," +
                "total_price INTEGER DEFAULT 0,date_info Text DEFAULT NULL,time_slot Text DEFAULT NULL)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase Mydb, int i, int i1) {
        Mydb.execSQL("drop table if exists Users");
    }

    public Boolean insertData(String username,String mobile_no,String password)
    {
        SQLiteDatabase Mydb=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("mobile_no",mobile_no);
        contentValues.put("password",password);
        long result=Mydb.insert("Users",null,contentValues);

        if(result == -1)
        {
            return false;
        }


        else
        {
            return true;
        }
    }
    public Boolean updateOrderitem(String orderitem,String username,int sum,String dateinfo,String timeinfo)
    {
        SQLiteDatabase Mydb=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("orderitem",orderitem);
        contentValues.put("total_price",sum);
        contentValues.put("date_info",dateinfo);
        contentValues.put("time_slot",timeinfo);


        Cursor cursor=Mydb.rawQuery("select * from Users where username=?",new String[] {username});
        if(cursor.getCount() > 0)
        {
            long result1=Mydb.update("Users",contentValues,"username=?",new String[] {username});

            if(result1 == -1)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    //to check if user already exists or not
    public Boolean checkusername(String username)
    {
        SQLiteDatabase Mydb=this.getWritableDatabase();
        Cursor cursor=Mydb.rawQuery("select * from Users where username=?",new String[] {username});
        if(cursor.getCount() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    //to check whether username and password that are entered are true or not
    public Boolean checkusernamepassword(String username,String password)
    {
        SQLiteDatabase Mydb=this.getWritableDatabase();
        Cursor cursor=Mydb.rawQuery("select * from Users where username=? and password=?",new String[] {username,password});
        if(cursor.getCount() >0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

}
