package in.nighthawk.wificonqrer.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by adi on 4/5/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_SCANS = "scans";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SCAN = "scan";

    private static final String DATABASE_NAME = "scans.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_SCANS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SCAN
            + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCANS);
        onCreate(db);
    }
}
