package com.example.electricitybillscalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database constants
    private static final String DATABASE_NAME = "ElectricityBills.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    public static final String TABLE_BILLS = "bills";
    public static final String COL_ID = "id";
    public static final String COL_MONTH = "month";
    public static final String COL_UNIT_USED = "unit_used";
    public static final String COL_REBATE_PERCENTAGE = "rebate_percentage";
    public static final String COL_TOTAL_CHARGES = "total_charges";
    public static final String COL_FINAL_COST = "final_cost";

    // SQL query to create the table
    private static final String CREATE_TABLE_BILLS =
            "CREATE TABLE " + TABLE_BILLS + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_MONTH + " TEXT, " +
                    COL_UNIT_USED + " REAL, " +
                    COL_REBATE_PERCENTAGE + " REAL, " +
                    COL_TOTAL_CHARGES + " REAL, " +
                    COL_FINAL_COST + " REAL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute the SQL to create the table when the database is first created
        db.execSQL(CREATE_TABLE_BILLS);
        Log.d("DatabaseHelper", "Database table created: " + TABLE_BILLS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILLS);
        onCreate(db);
        Log.d("DatabaseHelper", "Database table upgraded from version " + oldVersion + " to " + newVersion);
    }

    /**
     * Adds a new BillRecord to the database.
     * @param billRecord The BillRecord object to add.
     * @return true if insertion is successful, false otherwise.
     */
    public boolean addBillRecord(BillRecord billRecord) {
        SQLiteDatabase db = this.getWritableDatabase(); // Get writable database instance
        ContentValues cv = new ContentValues(); // Create a ContentValues object to store key-value pairs

        // Put all data into ContentValues
        cv.put(COL_MONTH, billRecord.getMonth());
        cv.put(COL_UNIT_USED, billRecord.getUnitUsed());
        cv.put(COL_REBATE_PERCENTAGE, billRecord.getRebatePercentage());
        cv.put(COL_TOTAL_CHARGES, billRecord.getTotalCharges());
        cv.put(COL_FINAL_COST, billRecord.getFinalCost());

        // Insert data into the table and check if successful
        long result = db.insert(TABLE_BILLS, null, cv);
        db.close(); // Close the database connection
        return result != -1; // If result is -1, insertion failed
    }

    /**
     * Retrieves all bill records from the database.
     * @return A List of BillRecord objects.
     */
    public List<BillRecord> getAllBillRecords() {
        List<BillRecord> billList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase(); // Get readable database instance
        Cursor cursor = null; // Cursor to hold results from the query

        try {
            // Query all rows from the bills table
            String query = "SELECT * FROM " + TABLE_BILLS;
            cursor = db.rawQuery(query, null);

            // Loop through all rows and add to list
            if (cursor.moveToFirst()) {
                do {
                    // Retrieve data from cursor by column index
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                    String month = cursor.getString(cursor.getColumnIndexOrThrow(COL_MONTH));
                    double unitUsed = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_UNIT_USED));
                    double rebatePercentage = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_REBATE_PERCENTAGE));
                    double totalCharges = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_TOTAL_CHARGES));
                    double finalCost = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_FINAL_COST));

                    // Create BillRecord object and add to list
                    BillRecord billRecord = new BillRecord(id, month, unitUsed, rebatePercentage, totalCharges, finalCost);
                    billList.add(billRecord);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error getting all bill records: " + e.getMessage());
        } finally {
            // Close cursor and database to prevent memory leaks
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return billList;
    }
}