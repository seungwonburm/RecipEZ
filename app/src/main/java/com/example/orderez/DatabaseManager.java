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
    private static final String TABLE_USERS = "Users";
    private static final String USERS_ID = "id";
    private static final String USERS_FIRST = "first";
    private static final String USERS_LAST = "last";
    private static final String USERS_EMAIL = "email";
    private static final String USERS_SECURITY = "security";
    private static final String USERS_SECURITY_ANS = "security_ans";
    private static final String USERS_PASSWORD = "password";

    private static final String TABLE_ITEM = "Item";
    private static final String ITEM_ID = "id";
    private static final String ITEM_NAME = "name";
//    private static final String ITEM_QUANTITY = "quantity";
//    private static final String ITEM_CONSUMPTION = "consumption";
    private static final String ITEM_START_DATE = "start_date";
    private static final String ITEM_DAYS = "days";
    private static final String ITEM_FREQUENCY = "frequency";
    private static final String ITEM_END_DATE = "end_date";
    private static final String ITEM_UNIT_ONE = "unit_one";
    private static final String ITEM_UNIT_ENTIRE = "unit_entire";
    private static final String ITEM_AMOUNT_ONE = "amount_one";
    private static final String ITEM_AMOUNT_ENTIRE = "amount_entire";
    private static final String ITEM_MEMO = "memo";
    private static final String ITEM_USERS_ID= "user_id";


    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sqlCreate_users = "create table " + TABLE_USERS + " ( " + USERS_ID;
        sqlCreate_users += " integer primary key autoincrement, " + USERS_FIRST;
        sqlCreate_users += " text, " + USERS_LAST + " text, " + USERS_EMAIL + " text, ";
        sqlCreate_users += USERS_PASSWORD + " text, " + USERS_SECURITY + " text, " + USERS_SECURITY_ANS + " text ) ";

        String sqlCreate_grocery = "create table " + TABLE_ITEM + " ( " + ITEM_ID;
        sqlCreate_grocery += " integer primary key autoincrement, " + ITEM_NAME + " text, " + ITEM_UNIT_ONE;
        sqlCreate_grocery += " text, " + ITEM_UNIT_ENTIRE + " text, " + ITEM_AMOUNT_ONE + " text, " + ITEM_START_DATE + " text, " + ITEM_DAYS + " text, " + ITEM_FREQUENCY + " text, ";
        sqlCreate_grocery +=  ITEM_END_DATE + " text, " + ITEM_AMOUNT_ENTIRE + " text, " + ITEM_MEMO + " text, " + ITEM_USERS_ID + " text ) ";

        db.execSQL(sqlCreate_users);
        db.execSQL(sqlCreate_grocery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_USERS);
        db.execSQL("drop table if exists " + TABLE_ITEM);
        onCreate(db);
    }

    public boolean insert(String first, String last, String email, String password, String security, String security_ans) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_FIRST, first.toLowerCase());
        contentValues.put(USERS_LAST, last.toLowerCase());
        contentValues.put(USERS_EMAIL, email.toLowerCase());
        String encryptedMsg;
        try {
            encryptedMsg = AESCrypt.encrypt(password, email.toLowerCase());
            contentValues.put(USERS_PASSWORD, encryptedMsg);
        }catch (GeneralSecurityException e){
            return false;
            //handle error
        }
        contentValues.put(USERS_SECURITY, security.toLowerCase());
        contentValues.put(USERS_SECURITY_ANS, security_ans.toLowerCase());

        long result = db.insert(TABLE_USERS, null, contentValues);

        if (result == -1) {
            return false;
        } else
            return true;

    }

    public boolean insertItem(String item_name, String start_date, String end_date, String days, String frequency, String unit_one, String unit_entire, String amount_one, String amount_entire, String memo, String user_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME, item_name.toLowerCase());
        contentValues.put(ITEM_START_DATE, start_date);
        contentValues.put(ITEM_END_DATE, end_date);
        contentValues.put(ITEM_DAYS, days.toLowerCase());
        contentValues.put(ITEM_FREQUENCY, frequency.toLowerCase());

        contentValues.put(ITEM_UNIT_ONE, unit_one.toLowerCase());
        contentValues.put(ITEM_UNIT_ENTIRE, unit_entire.toLowerCase());

        contentValues.put(ITEM_AMOUNT_ONE, amount_one.toLowerCase());
        contentValues.put(ITEM_AMOUNT_ENTIRE, amount_entire.toLowerCase());

        contentValues.put(ITEM_MEMO, memo.toLowerCase());
        contentValues.put(ITEM_USERS_ID, user_id.toLowerCase());

        long result = db.insert(TABLE_ITEM, null, contentValues);

        if (result == -1) {
            return false;
        } else
            return true;

    }


    public Cursor search(String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_USERS + " where email = ?  ", new String[]{email});

        return cursor;
    }

    public Cursor searchId(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_USERS + " where id = ?  ", new String[]{id});

        return cursor;
    }

    public Cursor searchItemId(String user_id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_ITEM + " where user_id = ?  ", new String[]{user_id});

        return cursor;
    }

    public int verify(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Cursor cursor = db.rawQuery("select * from " + TABLE_USERS + " where email = ?  ", new String[]{email});
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
        contentValues.put(USERS_ID, id);
        String encryptedMsg;
        try {
            encryptedMsg = AESCrypt.encrypt(password, email.toLowerCase());
            contentValues.put(USERS_PASSWORD, encryptedMsg);
        }catch (GeneralSecurityException e){
            //handle error
            return false;
        }
        db.update(TABLE_USERS, contentValues, "ID = ?", new String[] {id});

        return true;
    }

//    public void delete(String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sqlDelete ="delete from " + TABLE_USERS + " where id = " + id;
//        db.execSQL(sqlDelete);
//        db.close();
//
//    }


//    public String[] autoComplete() {
//        ArrayList<String> data =new ArrayList();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM " + TABLE_USERS;
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

