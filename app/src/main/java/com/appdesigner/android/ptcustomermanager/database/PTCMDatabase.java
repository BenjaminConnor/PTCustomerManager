package com.appdesigner.android.ptcustomermanager.database;

/**
 * Created by Benjamin on 9/21/2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PTCMDatabase {
    //The index (key) column name for use in where clauses.
    public static final String KEY_ID = "_id";

    //The name and column index of each column in your database.
    //These should be descriptive.
    public static final String KEY_NAME_COLUMN =
            "NAME_COLUMN";
    public static final String KEY_PHONE_COLUMN =
            "PHONE_COLUMN";
    public static final String KEY_ADDRESS_COLUMN =
            "ADDRESS_COLUMN";
    public static final String KEY_EMAIL_COLUMN =
            "EMAIL_COLUMN";
    public static final String KEY_INFO_NOTES_COLUMN =
            "INFO_NOTES_COLUMN";
    public static final String KEY_NAME_ON_CARD_COLUMN =
            "NAME_ON_CARD_COLUMN";
    public static final String KEY_BILLING_ADDRESS_COLUMN =
            "BILLING_ADDRESS_COLUMN";
    public static final String KEY_CITY_COLUMN =
            "CITY_COLUMN";
    public static final String KEY_STATE_COLUMN =
            "STATE_COLUMN";
    public static final String KEY_ZIP_COLUMN =
            "ZIP_COLUMN";
    public static final String KEY_CARD_NUMBER_COLUMN =
            "CARD_NUMBER_COLUMN";
    public static final String KEY_EXP_DATE_COLUMN =
            "EXP_DATE_COLUMN";
    public static final String KEY_CVV_COLUMN =
            "CVV_COLUMN";


    // Database open/upgrade helper
    private CustomerDBOpenHelper customerDBOpenHelper;

    public void PTCMDatabase(Context context) {
        customerDBOpenHelper = new CustomerDBOpenHelper(context, CustomerDBOpenHelper.DATABASE_NAME, null,
                CustomerDBOpenHelper.DATABASE_VERSION);
    }

    // Called when you no longer need access to the database.
    public void closeDatabase() {
        customerDBOpenHelper.close();
    }

    private Cursor getCustomer() {
        /**
         * Listing 8-3: Querying a database
         */
        // Specify the result column projection. Return the minimum set
        // of columns required to satisfy your requirements.
        String[] result_columns = new String[] {
                KEY_ID, KEY_NAME_COLUMN, KEY_PHONE_COLUMN, KEY_ADDRESS_COLUMN, KEY_EMAIL_COLUMN,
                KEY_INFO_NOTES_COLUMN, KEY_NAME_ON_CARD_COLUMN, KEY_BILLING_ADDRESS_COLUMN,
                KEY_CITY_COLUMN, KEY_STATE_COLUMN, KEY_ZIP_COLUMN, KEY_CARD_NUMBER_COLUMN,
                KEY_EXP_DATE_COLUMN, KEY_CVV_COLUMN};

        // Specify the where clause that will limit our results.
        String where = null;

        // Replace these with valid SQL statements as necessary.
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;

        SQLiteDatabase db = customerDBOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(CustomerDBOpenHelper.DATABASE_TABLE,
                result_columns, where,
                whereArgs, groupBy, having, order);
        //
        return cursor;
    }

    /*public float getAverageAccessibleHoardValue() {
        Cursor cursor = getAccessibleHoard();

        *//**
         * Listing 8-4: Extracting values from a Cursor
         *//*
        float totalHoard = 0f;
        float averageHoard = 0f;

        // Find the index to the column(s) being used.
        int GOLD_HOARDED_COLUMN_INDEX =
                cursor.getColumnIndexOrThrow(KEY_GOLD_HOARDED_COLUMN);

        // Iterate over the cursors rows.
        // The Cursor is initialized at before first, so we can
        // check only if there is a "next" row available. If the
        // result Cursor is empty this will return false.
        while (cursor.moveToNext()) {
            float hoard = cursor.getFloat(GOLD_HOARDED_COLUMN_INDEX);
            totalHoard += hoard;
        }

        // Calculate an average -- checking for divide by zero errors.
        float cursorCount = cursor.getCount();
        averageHoard = cursorCount > 0 ?
                (totalHoard / cursorCount) : Float.NaN;

        // Close the Cursor when you've finished with it.
        cursor.close();

        return averageHoard;
    }*/

    public void addNewCustomer(String name, int phone, String address, String email, String infoNotes,
            String nameOnCard, String billingAddress, String city, String state, int zip, int cardNumber,
            String expDate, int cvv) {
        /**
         * Listing 8-5: Inserting new rows into a database
         */
        // Create a new row of values to insert.
        ContentValues newValues = new ContentValues();

        // Assign values for each row.
        newValues.put(KEY_NAME_COLUMN, name);
        newValues.put(KEY_PHONE_COLUMN, phone);
        newValues.put(KEY_ADDRESS_COLUMN, address);
        newValues.put(KEY_EMAIL_COLUMN, email);
        newValues.put(KEY_INFO_NOTES_COLUMN, infoNotes);
        newValues.put(KEY_NAME_ON_CARD_COLUMN, nameOnCard);
        newValues.put(KEY_BILLING_ADDRESS_COLUMN, billingAddress);
        newValues.put(KEY_CITY_COLUMN, city);
        newValues.put(KEY_STATE_COLUMN, state);
        newValues.put(KEY_ZIP_COLUMN, zip);
        newValues.put(KEY_CARD_NUMBER_COLUMN, cardNumber);
        newValues.put(KEY_EXP_DATE_COLUMN, expDate);
        newValues.put(KEY_CVV_COLUMN, cvv);
        // [ ... Repeat for each column / value pair ... ]

        // Insert the row into your table
        SQLiteDatabase db = customerDBOpenHelper.getWritableDatabase();
        db.insert(CustomerDBOpenHelper.DATABASE_TABLE, null, newValues);
    }

    /*public void updateCustomerValue(int id, String newName) {
        *//**
         * Listing 8-6: Updating a database row
         *//*
        // Create the updated row Content Values.
        ContentValues updatedValues = new ContentValues();

        // Assign values for each row.
        updatedValues.put(KEY_NAME_COLUMN, newName);
        // [ ... Repeat for each column to update ... ]

        // Specify a where clause the defines which rows should be
        // updated. Specify where arguments as necessary.
        String where = KEY_ID + "=" + id;
        String whereArgs[] = null;

        // Update the row with the specified index with the new values.
        SQLiteDatabase db = customerDBOpenHelper.getWritableDatabase();
        db.update(CustomerDBOpenHelper.DATABASE_TABLE, updatedValues,
                where, whereArgs);
    }*/

    /*public void deleteEmptyHoards() {
        *//**
         * Listing 8-7: Deleting a database row
         *//*
        // Specify a where clause that determines which row(s) to delete.
        // Specify where arguments as necessary.
        String where = null;
        String whereArgs[] = null;

        // Delete the rows that match the where clause.
        SQLiteDatabase db = customerDBOpenHelper.getWritableDatabase();
        db.delete(CustomerDBOpenHelper.DATABASE_TABLE, where, whereArgs);
    }*/

    /**
     * Listing 8-2: Implementing an SQLite Open Helper
     */
    private static class CustomerDBOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "PTCMDatabase.db";
        private static final String DATABASE_TABLE = "Customers";
        private static final int DATABASE_VERSION = 1;

        // SQL Statement to create a new database.
        private static final String DATABASE_CREATE = "create table " +
                DATABASE_TABLE + " (" + KEY_ID +
                " integer primary key autoincrement, " +
                KEY_NAME_COLUMN + " string, " +
                KEY_PHONE_COLUMN + " integer, " +
                KEY_ADDRESS_COLUMN + " string, " +
                KEY_EMAIL_COLUMN + " string, " +
                KEY_INFO_NOTES_COLUMN + " string, " +
                KEY_NAME_ON_CARD_COLUMN + " string, " +
                KEY_BILLING_ADDRESS_COLUMN + " string, " +
                KEY_CITY_COLUMN + " string, " +
                KEY_STATE_COLUMN + " string, " +
                KEY_ZIP_COLUMN + " integer, " +
                KEY_CARD_NUMBER_COLUMN + " integer, " +
                KEY_EXP_DATE_COLUMN + " string, " +
                KEY_CVV_COLUMN + " integer);";

        public CustomerDBOpenHelper(Context context, String name,
                                 CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // Called when no database exists in disk and the helper class needs
        // to create a new one.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        // Called when there is a database version mismatch meaning that
        // the version of the database on disk needs to be upgraded to
        // the current version.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            // Log the version upgrade.
            Log.w("TaskDBAdapter", "Upgrading from version " +
                    oldVersion + " to " +
                    newVersion + ", which will destroy all old data");

            // Upgrade the existing database to conform to the new
            // version. Multiple previous versions can be handled by
            // comparing oldVersion and newVersion values.

            // The simplest case is to drop the old table and create a new one.
            db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
            // Create a new one.
            onCreate(db);
        }
    }
}
