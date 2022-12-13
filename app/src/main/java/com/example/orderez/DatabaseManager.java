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

    //Database Information
    private static final String DATABASE_NAME = "RecipEZ";
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
    private static final String ITEM_AMOUNT = "amount";
    private static final String ITEM_UNIT = "unit";
    private static final String ITEM_EXPIRE_DATE = "expire_date";
    private static final String ITEM_MEMO = "memo";
    private static final String ITEM_USERS_ID= "user_id";


    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //Creates Table using execSQL


        String sqlCreate_users = "create table " + TABLE_USERS + " ( " + USERS_ID;
        sqlCreate_users += " integer primary key autoincrement, " + USERS_FIRST;
        sqlCreate_users += " text, " + USERS_LAST + " text, " + USERS_EMAIL + " text, ";
        sqlCreate_users += USERS_PASSWORD + " text, " + USERS_SECURITY + " text, " + USERS_SECURITY_ANS + " text ) ";

        String sqlCreate_items = "create table " + TABLE_ITEM + " ( " + ITEM_ID;
        sqlCreate_items += " integer primary key autoincrement, " + ITEM_NAME + " text, " + ITEM_AMOUNT;
        sqlCreate_items += " text, " + ITEM_UNIT + " text, " + ITEM_EXPIRE_DATE + " text, " + ITEM_MEMO + " text, " + ITEM_USERS_ID + " text ) ";

        db.execSQL(sqlCreate_users);
        db.execSQL(sqlCreate_items);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //Upgrades Table using execSQL
        db.execSQL("drop table if exists " + TABLE_USERS);
        db.execSQL("drop table if exists " + TABLE_ITEM);
        onCreate(db);
    }

    public boolean insert(String first, String last, String email, String password, String security, String security_ans) {//Insert a new row in User Table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();//Using ContentValues to put all elements in User Table
        contentValues.put(USERS_FIRST, first.substring(0,1).toUpperCase() + first.substring(1).toLowerCase());
        contentValues.put(USERS_LAST, last.substring(0,1).toUpperCase() + last.substring(1).toLowerCase());
        contentValues.put(USERS_EMAIL, email.toLowerCase());
        String encryptedPwd, encryptedAns;
        try {
            encryptedPwd = AESCrypt.encrypt(password, email.toLowerCase());
            encryptedAns = AESCrypt.encrypt(security_ans, email.toLowerCase());
            contentValues.put(USERS_PASSWORD, encryptedPwd); //Password Encryption
            contentValues.put(USERS_SECURITY_ANS, encryptedAns); //Security Answer Encryption

        }catch (GeneralSecurityException e){
            return false;
            //handle error
        }
        contentValues.put(USERS_SECURITY, security.toLowerCase());


        long result = db.insert(TABLE_USERS, null, contentValues);

        if (result == -1) {
            return false;// Item not added in the DB
        } else
            return true;// Item added in the DB

    }
    public boolean insertItem(String name, String amount, String unit, String expire_date, String memo, String user_id) { //Insert a new row in Item Table

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(); //Using ContentValues to put all elements in Item Table
        contentValues.put(ITEM_NAME, name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase());
        contentValues.put(ITEM_AMOUNT, amount);
        contentValues.put(ITEM_UNIT,unit);
        contentValues.put(ITEM_EXPIRE_DATE, expire_date);
        contentValues.put(ITEM_MEMO, memo);
        contentValues.put(ITEM_USERS_ID, user_id);

        long result = db.insert(TABLE_ITEM, null, contentValues);

        if (result == -1) {
            return false; // Item not added in the DB
        } else
            return true; // Item added in the DB

    }


    public Cursor search(String email) { //Search user account using email
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_USERS + " where email = ?  ", new String[]{email});

        return cursor;
    }

    public Cursor searchId(String id){ // Search user account using user_id
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_USERS + " where id = ?  ", new String[]{id});

        return cursor;
    }

    public Cursor searchItemId(String user_id){ // Search items associated with the user account
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_ITEM + " where user_id = ?  ", new String[]{user_id});

        return cursor;
    }

    public Cursor searchItemNameId(String user_id, String item){ //Search item name associated with the user account
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_ITEM + " where user_id = ? AND name = ? ", new String[]{user_id, item});
        return cursor;

    }

    public int verify(String email){  //Checks if email is valid for registration
        SQLiteDatabase db = this.getWritableDatabase();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Cursor cursor = db.rawQuery("select * from " + TABLE_USERS + " where email = ?  ", new String[]{email});
        if(cursor.moveToFirst())  // Checks if email already exists
        {
            return -1;
        }

        if (!email.matches(emailPattern)){ //Checks if email is valid
            return -2;
        }

        return 1; // Passes the test
    }

    public boolean updatePW(String id, String email, String password){ //Updates Password in a row in User Table
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_ID, id);
        String encryptedMsg; //Password Encryption
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

    public void updateItem(String item_id, String item, String date, String amount, String unit, String memo){ //Updates a row in Item Table

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues(); //Using ContentValues to put all elements in Item Table
        contentValues.put(ITEM_NAME, item.substring(0,1).toUpperCase() + item.substring(1).toLowerCase());
        contentValues.put(ITEM_AMOUNT, amount);
        contentValues.put(ITEM_UNIT,unit);
        contentValues.put(ITEM_EXPIRE_DATE, date);
        contentValues.put(ITEM_MEMO, memo);

        db.update(TABLE_ITEM, contentValues, "ID = ?", new String[] {item_id});

    }

    public void delete(String id) { // Deletes a row in Item Table using item_id
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDelete ="delete from " + TABLE_ITEM + " where id = " + id;
        db.execSQL(sqlDelete);
        db.close();

    }


}