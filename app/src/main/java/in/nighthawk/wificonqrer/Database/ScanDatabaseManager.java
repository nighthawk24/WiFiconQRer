package in.nighthawk.wificonqrer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import in.nighthawk.wificonqrer.Models.Scan;

/**
 * Created by adi on 4/5/16.
 */
public class ScanDatabaseManager {

    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_SCAN};

    public ScanDatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Scan createScan(String scan) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_SCAN, scan);
        long insertId = database.insert(DatabaseHelper.TABLE_SCANS, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_SCANS,
                allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Scan newScan = cursorToScan(cursor);
        cursor.close();
        return newScan;
    }

    public void deleteScan(Scan scan) {
        long id = scan.getId();
        System.out.println("Scan deleted with id: " + id);
        database.delete(DatabaseHelper.TABLE_SCANS, DatabaseHelper.COLUMN_ID
                + " = " + id, null);
    }

    public Scan getScan(long id) {

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_SCANS,
                allColumns,
                null,
                new String[]{DatabaseHelper.COLUMN_ID + "=" + id},
                null, null, null, null);

        cursor.moveToFirst();
        Scan scan = cursorToScan(cursor);
        cursor.close();
        return scan;
    }

    public List<Scan> getAllScans() {
        List<Scan> scans = new ArrayList<Scan>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_SCANS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Scan scan = cursorToScan(cursor);
            scans.add(scan);
            cursor.moveToNext();
        }
        cursor.close();
        return scans;
    }

    private Scan cursorToScan(Cursor cursor) {
        Scan scan = new Scan();
        scan.setId(cursor.getLong(0));
        scan.setScan(cursor.getString(1));
        return scan;
    }

}
