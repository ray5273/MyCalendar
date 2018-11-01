package com.example.software3.mycalendar;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="calendar.db";
    public static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB_w;
    public static SQLiteDatabase mDB_r;

    private DBHelper mDBHelper;
    private Context mCtx;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateDB._CREATE);
    }


    public static final class CreateDB implements BaseColumns{
        public static final String DATE = "date_num";
        public static final String CONTENT = "content";
        public static final String _TABLENAME = "calendar";
        public static final String _CREATE =
                "create table "+_TABLENAME+"("
                        +DATE+" text not null , "
                        +CONTENT+" text not null );";
    }




    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void insert(String date, String content){
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO calendar VALUES('"+date+"','"+content+"');");
        db.close();

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String read(String date){

        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        Log.e("read date",date);
        Cursor cursor = db.rawQuery("SELECT content FROM calendar where date_num = "+"'"+date+"'",null);
        while(cursor.moveToNext()){
            result += cursor.getString(0) + "///";
        }
        db.close();
        return result;


    }


    public String readDate(){

        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("SELECT DISTINCT date_num FROM calendar ",null);
        while(cursor.moveToNext()){
            result += cursor.getString(0) + "///";
        }
        db.close();
        return result;


    }


}
