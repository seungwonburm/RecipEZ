package com.example.orderez;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.util.Locale;


public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "OrderEZ";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Users";
    private static final String ID = "id";
    private static final String FIRST = "first";
    private static final String LAST = "last";
    private static final String EMAIL = "email";
    private static final String SECURITY = "security";
    private static final String SECURITY_ANS = "security_ans";
    private static final String PASSWORD = "password";




    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "create table " + TABLE_NAME + " ( " + ID;
        sqlCreate += " integer primary key autoincrement, " + FIRST;
        sqlCreate += " text, " + LAST + " text, " + EMAIL + " text, ";
        sqlCreate += PASSWORD + " text, " + SECURITY + " text, " + SECURITY_ANS + " text ) ";

        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(String first, String last, String email, String password, String security, String security_ans) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST, first.toLowerCase());
        contentValues.put(LAST, last.toLowerCase());
        contentValues.put(EMAIL, email.toLowerCase());
        String encryptedMsg;
        try {
          encryptedMsg = AESCrypt.encrypt(password, email.toLowerCase());
          contentValues.put(PASSWORD, encryptedMsg);
        }catch (GeneralSecurityException e){
            return false;
            //handle error
        }
        contentValues.put(SECURITY, security.toLowerCase());
        contentValues.put(SECURITY_ANS, security_ans.toLowerCase());




        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else
            return true;

    }


    public Cursor search(String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where email = ?  ", new String[]{email});

        return cursor;
    }

    public Cursor searchId(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where id = ?  ", new String[]{id});

        return cursor;
    }

    public int verify(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where email = ?  ", new String[]{email});
        if(cursor.moveToFirst())
        {
            return -1;
        }

        if (!email.matches(emailPattern)){
            return -2;
        }

        return 1;
    }

    public boolean updatePW(String id, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        String encryptedMsg;
        try {
            encryptedMsg = AESCrypt.encrypt(password, email.toLowerCase());
            contentValues.put(PASSWORD, encryptedMsg);
        }catch (GeneralSecurityException e){
            //handle error
            return false;
        }
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});

        return true;
    }

//    public void delete(String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sqlDelete ="delete from " + TABLE_NAME + " where id = " + id;
//        db.execSQL(sqlDelete);
//        db.close();
//
//    }


//    public String[] autoComplete() {
//        ArrayList<String> data =new ArrayList();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM " + TABLE_NAME;
//        Cursor cursor = db.rawQuery(sql, null);
//        String[] theData = new String[cursor.getCount()];
//        if (cursor != null && cursor.getCount()>0){
//            cursor.moveToFirst();
//
//            int i=0;
//            do {
//                theData[i]=cursor.getString(3);
//                i++;
//            }while(cursor.moveToNext());
//
//        }
//
//        return theData;
//
//    }

}

