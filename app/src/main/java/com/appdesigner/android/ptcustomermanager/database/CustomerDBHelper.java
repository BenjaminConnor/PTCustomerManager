package com.appdesigner.android.ptcustomermanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Benjamin on 9/21/2017.
 */

public class CustomerDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PTCMDatabase.db";
    private static final String CUSTOMERS_TABLE = "customers";
    private static final String SESSIONS_TABLE = "sessions";
    private static final int DATABASE_VERSION = 1;

    public static final String CUSTOMER_ID_COLUMN = "_id";
    public static final String CUSTOMER_NAME_COLUMN = "name";
    public static final String CUSTOMER_PHONE_COLUMN = "phone";
    public static final String CUSTOMER_ADDRESS_COLUMN = "address";
    public static final String CUSTOMER_EMAIL_COLUMN = "email";
    public static final String CUSTOMER_INFO_NOTES_COLUMN = "info_notes";
    public static final String CUSTOMER_NAME_ON_CARD_COLUMN = "name_on_card";
    public static final String CUSTOMER_BILLING_ADDRESS_COLUMN = "billing_address";
    public static final String CUSTOMER_CITY_COLUMN = "city";
    public static final String CUSTOMER_STATE_COLUMN = "state";
    public static final String CUSTOMER_ZIP_COLUMN = "zip";
    public static final String CUSTOMER_CARD_NUMBER_COLUMN = "card_number";
    public static final String CUSTOMER_EXP_DATE_COLUMN = "exp_date";
    public static final String CUSTOMER_CVV_COLUMN = "cvv";

    public static final String SESSION_ID_COLUMN = "session_id";
    public static final String SESSION_CUSTOMER_NAME_COLUMN = "session_customer_name";
    public static final String SESSION_DATE_COLUMN = "session_date";
    public static final String SESSION_TIME_COLUMN = "session_time";
    public static final String SESSION_LOCATION_COLUMN = "session_location";
    public static final String SESSION_NOTES_COLUMN = "session_notes";


    public CustomerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CUSTOMERS_TABLE + "(" +
            CUSTOMER_ID_COLUMN + " integer primary key, " +
            CUSTOMER_NAME_COLUMN + " string, " +
            CUSTOMER_PHONE_COLUMN + " string, " +
            CUSTOMER_ADDRESS_COLUMN + " string, " +
            CUSTOMER_EMAIL_COLUMN + " string, " +
            CUSTOMER_INFO_NOTES_COLUMN + " string, " +
            CUSTOMER_NAME_ON_CARD_COLUMN + " string, " +
            CUSTOMER_BILLING_ADDRESS_COLUMN + " string, " +
            CUSTOMER_CITY_COLUMN + " string, " +
            CUSTOMER_STATE_COLUMN + " string, " +
            CUSTOMER_ZIP_COLUMN + " string, " +
            CUSTOMER_CARD_NUMBER_COLUMN + " string, " +
            CUSTOMER_EXP_DATE_COLUMN + " string, " +
            CUSTOMER_CVV_COLUMN + " string);");


        db.execSQL("CREATE TABLE " + SESSIONS_TABLE + " (" +
                SESSION_ID_COLUMN + " integer primary key, " +
                CUSTOMER_ID_COLUMN + " integer, " +
                SESSION_CUSTOMER_NAME_COLUMN + " string, " +
                SESSION_DATE_COLUMN + " string, " +
                SESSION_TIME_COLUMN + " string, " +
                SESSION_LOCATION_COLUMN + " string, " +
                SESSION_NOTES_COLUMN + " string);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMERS_TABLE + ", " + SESSIONS_TABLE);
        onCreate(db);
    }

    public boolean insertNewCustomer(String name, String phone, String address, String email, String infoNotes,
                               String nameOnCard, String billingAddress, String city, String state, String zip, String cardNumber,
                               String expDate, String cvv) {

        ContentValues values = new ContentValues();

        values.put(CUSTOMER_NAME_COLUMN, name);
        values.put(CUSTOMER_PHONE_COLUMN, phone);
        values.put(CUSTOMER_ADDRESS_COLUMN, address);
        values.put(CUSTOMER_EMAIL_COLUMN, email);
        values.put(CUSTOMER_INFO_NOTES_COLUMN, infoNotes);
        values.put(CUSTOMER_NAME_ON_CARD_COLUMN, nameOnCard);
        values.put(CUSTOMER_BILLING_ADDRESS_COLUMN, billingAddress);
        values.put(CUSTOMER_CITY_COLUMN, city);
        values.put(CUSTOMER_STATE_COLUMN, state);
        values.put(CUSTOMER_ZIP_COLUMN, zip);
        values.put(CUSTOMER_CARD_NUMBER_COLUMN, cardNumber);
        values.put(CUSTOMER_EXP_DATE_COLUMN, expDate);
        values.put(CUSTOMER_CVV_COLUMN, cvv);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(CUSTOMERS_TABLE, null, values);
        return true;
    }

    public boolean insertNewSession(int customerID, String customerName, String date, String time,
            String location, String notes) {
        ContentValues values = new ContentValues();

        values.put(CUSTOMER_ID_COLUMN, customerID);
        values.put(SESSION_CUSTOMER_NAME_COLUMN, customerName);
        values.put(SESSION_DATE_COLUMN, date);
        values.put(SESSION_TIME_COLUMN, time);
        values.put(SESSION_LOCATION_COLUMN, location);
        values.put(SESSION_NOTES_COLUMN, notes);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(SESSIONS_TABLE, null, values);
        return true;
    }

    public boolean updateCustomerInfo(Integer id, String name, String phone, String address, String email, String infoNotes) {
        ContentValues values = new ContentValues();
        values.put(CUSTOMER_NAME_COLUMN, name);
        values.put(CUSTOMER_PHONE_COLUMN, phone);
        values.put(CUSTOMER_ADDRESS_COLUMN, address);
        values.put(CUSTOMER_EMAIL_COLUMN, email);
        values.put(CUSTOMER_INFO_NOTES_COLUMN, infoNotes);


        SQLiteDatabase db = this.getWritableDatabase();
        db.update(CUSTOMERS_TABLE, values, CUSTOMER_ID_COLUMN + " = ? ", new String[] { Integer.toString(id)});
        return true;

    }

    public boolean updateCustomerBilling(Integer id, String nameOnCard, String billingAddress, String city, String state,
                                      String zip, String cardNumber, String expDate, String cvv) {
        ContentValues values = new ContentValues();
        values.put(CUSTOMER_NAME_ON_CARD_COLUMN, nameOnCard);
        values.put(CUSTOMER_BILLING_ADDRESS_COLUMN, billingAddress);
        values.put(CUSTOMER_CITY_COLUMN, city);
        values.put(CUSTOMER_STATE_COLUMN, state);
        values.put(CUSTOMER_ZIP_COLUMN, zip);
        values.put(CUSTOMER_CARD_NUMBER_COLUMN, cardNumber);
        values.put(CUSTOMER_EXP_DATE_COLUMN, expDate);
        values.put(CUSTOMER_CVV_COLUMN, cvv);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(CUSTOMERS_TABLE, values, CUSTOMER_ID_COLUMN + " = ? ", new String[] { Integer.toString(id)});
        return true;
    }

    public boolean updateSession(int sessionID, int customerID, String customerName, String date, String time,
         String location, String notes) {

        ContentValues values = new ContentValues();
        values.put(SESSION_ID_COLUMN, sessionID);
        values.put(CUSTOMER_ID_COLUMN, customerID);
        values.put(SESSION_CUSTOMER_NAME_COLUMN, customerName);
        values.put(SESSION_DATE_COLUMN, date);
        values.put(SESSION_TIME_COLUMN, time);
        values.put(SESSION_LOCATION_COLUMN, location);
        values.put(SESSION_NOTES_COLUMN, notes);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(SESSIONS_TABLE, values, SESSION_ID_COLUMN + " = ? ", new String[] {Integer.toString(sessionID)});
        return true;
    }

    public Cursor getCustomer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + CUSTOMERS_TABLE + " WHERE " +
            CUSTOMER_ID_COLUMN + "=?", new String[]{Integer.toString(id)});
        return result;
    }

    public Cursor getSession(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + SESSIONS_TABLE + " WHERE " +
                SESSION_ID_COLUMN + "=?", new String[]{Integer.toString(id)});
        return result;
    }

    public Cursor getCustomerSessions(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + SESSIONS_TABLE + " WHERE " +
            CUSTOMER_ID_COLUMN + "=?", new String[]{Integer.toString(id)});
        return result;
    }

    public Cursor getAllCustomers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + CUSTOMERS_TABLE, null);
        return result;
    }

    public Cursor getAllSessions() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + SESSIONS_TABLE, null);
        return result;
    }
}
