package rs.fon.dontforgetyourmedicine.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by radovan.bogdanic on 3/7/2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "medicine.db";
    private static final int VERSION = 1;

    public static final String ALARM_TABLE_NAME = "AlarmTable";
    public static final String USER_TABLE_NAME = "AccountTable";
    public static final String HISTORY_TABLE_NAME = "HistoryTable";

    private static SQLiteDatabase database = null;

    public static SQLiteDatabase getSQLiteDatabase(Context context) {
        if (!(database == null || database.isOpen())) {
            database = null;
        }
        if (database == null) {
            DBOpenHelper dbHelper;
            try {
                dbHelper = new DBOpenHelper(context);
                database = dbHelper.getWritableDatabase();
                dbHelper.onOpen(database);
            } catch (Exception e) {
                try {
                    dbHelper = new DBOpenHelper(context);
                    database = dbHelper.getReadableDatabase();
                    dbHelper.onOpen(database);
                } catch (Exception e2) {

                }
            }
            if (!(database == null || database.isOpen())) {
                database = null;
            }
        }
        return database;
    }

    public static void closeDb() {
        try {
            if (database != null) {
                database.close();
            }
        } catch (Exception e) {
        }
        database = null;
    }

    public DBOpenHelper(Context paramContext) {
        super(paramContext, DB_NAME, null, VERSION);
    }

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        if(db != null) {
            db.execSQL("create table if not exists " + ALARM_TABLE_NAME +
                    " (id integer PRIMARY KEY AUTOINCREMENT NOT NULL, medicName text, startTime long, startDate long, alarmRepeat integer, endDate long, dateCreated long, dateDeleted long) ");
            db.execSQL("create table if not exists " + USER_TABLE_NAME +
                    " (userid integer PRIMARY KEY AUTOINCREMENT NOT NULL, userName text, userEmail text, userImg text, isVisitor boolean) ");
            db.execSQL("create table if not exists " + HISTORY_TABLE_NAME +
                    " (histid integer PRIMARY KEY AUTOINCREMENT NOT NULL, histName text, histStartDate long, histHoursRepeat integer, histEndDate long, histDateCreated) ");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db);
        onCreate(db);
    }

    private static void dropTable(SQLiteDatabase db) {
        if(db!=null) {
            db.execSQL("drop table if exists " + ALARM_TABLE_NAME + " ");
        }
    }
}
