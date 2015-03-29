package com.zenas.tools;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * SQL工具
 * Created by Administrator on 2015/3/26.
 */
public class SqltieTools extends SQLiteOpenHelper {
    private Context context;

    public SqltieTools(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //创建表
            db.execSQL(SQLCreateTable.TOKEN_TEBLE);
            db.execSQL(SQLCreateTable.USER_TEBLE);
            db.execSQL(SQLCreateTable.CONTACT_NUMBER_TABLE);
            db.execSQL(SQLCreateTable.MSG_CONTENT);
            db.execSQL(SQLCreateTable.GET_COMMENT);
            db.execSQL(SQLCreateTable.PUB_MESSAGE);
            db.execSQL(SQLCreateTable.PUB_COMMENT);
            Log.d("test","Table Created!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "create tables successful!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }
}
