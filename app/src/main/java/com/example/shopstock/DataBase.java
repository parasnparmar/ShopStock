package com.example.shopstock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(@Nullable Context context) {
        super(context, "ShopStack.db",null,1 );
    }
    @Override
    public void onCreate(SQLiteDatabase Mydb) {
        Mydb.execSQL("create Table Signup (email Text primary key, password text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase Mydb, int i, int i1) {
        Mydb.execSQL("drop Table if exists Signup");
    }
    public Boolean insertData(String email, String password){
        SQLiteDatabase Mydb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        long result = Mydb.insert("Signup",null,contentValues);

        if(result== -1){
            return false;
        }else {
            return true;
        }
    }





    public Boolean checkEmail(String email){
        SQLiteDatabase Mydb = this.getWritableDatabase();
        Cursor cursor = Mydb.rawQuery("Select * from Signup where email = ?",new String[]{email});
        if(cursor.getCount() > 0) {
            return true;
        }else{
            return false;
        }
    }

    public boolean checkPassword (String password){
        SQLiteDatabase Mydb = this.getWritableDatabase();
        Cursor cursor = Mydb.rawQuery("Select * from Signup where password = ?",new String[]{password});
        if(cursor.getCount()>0){
            return true;

        }else {
            return false;
        }
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase Mydb = this.getWritableDatabase();
        Cursor cursor = Mydb.rawQuery("Select * from Signup where email = ? and password =?",new String[]{email,password});
        if(cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }
}
