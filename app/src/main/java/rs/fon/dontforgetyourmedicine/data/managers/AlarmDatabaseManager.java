package rs.fon.dontforgetyourmedicine.data.managers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import rs.fon.dontforgetyourmedicine.data.DBOpenHelper;
import rs.fon.dontforgetyourmedicine.data.model.AlarmDatabaseModel;
import rs.fon.dontforgetyourmedicine.util.MedicineConstants;

/**
 * Created by radovan.bogdanic on 3/18/2017.
 */

public class AlarmDatabaseManager {

    public static final String ALARM_ID = "id";
    public static final String MEDIC_NAME = "medicName";
    public static final String ALARM_START_TIME = "startTime";
    public static final String ALARM_START_DATE = "startDate";
    public static final String ALARM_REPEAT = "alarmRepeat";
    public static final String ALARM_END_DATE = "endDate";
    public static final String ALARM_DATE_CREATED = "dateCreated";
    public static final String ALARM_DATE_DELETED = "dateDeleted";

    public static AlarmDatabaseModel getInfo(Cursor cursor) {
        AlarmDatabaseModel alarm = new AlarmDatabaseModel();
        alarm.setId(cursor.getInt(cursor.getColumnIndex(ALARM_ID)));
        alarm.setMedicName(cursor.getString(cursor.getColumnIndex(MEDIC_NAME)));
        alarm.setStartDate(new Date(cursor.getLong(cursor.getColumnIndex(ALARM_START_DATE))));
        alarm.setStartTime(new Date(cursor.getLong(cursor.getColumnIndex(ALARM_START_TIME))));
        alarm.setAlarmRepeat(cursor.getInt(cursor.getColumnIndex(ALARM_REPEAT)));
        alarm.setAlarmExpireDate(new Date(cursor.getLong(cursor.getColumnIndex(ALARM_END_DATE))));
        alarm.setDateCreated(new Date(cursor.getLong(cursor.getColumnIndex(ALARM_DATE_CREATED))));
        alarm.setDateDeleted(new Date(cursor.getLong(cursor.getColumnIndex(ALARM_DATE_DELETED))));
        return alarm;
    }

    public static long insert(AlarmDatabaseModel model, SQLiteDatabase db) {

        ContentValues content = new ContentValues();

        content.put(MEDIC_NAME,model.getMedicName());
        content.put(ALARM_START_TIME,model.getStartTime().getTime());
        content.put(ALARM_START_DATE, model.getStartDate().getTime());
        content.put(ALARM_DATE_CREATED,model.getDateCreated().getTime());
        content.put(ALARM_REPEAT,model.getAlarmRepeat());
        content.put(ALARM_END_DATE,model.getAlarmExpireDate().getTime());

        return db.insert(DBOpenHelper.ALARM_TABLE_NAME,null,content);
    }

    public static long update(AlarmDatabaseModel model, SQLiteDatabase db) {

        ContentValues content = new ContentValues();
        content.put(ALARM_DATE_DELETED,model.getDateDeleted().getTime());

        return db.update(DBOpenHelper.ALARM_TABLE_NAME,content, ALARM_ID + " = ?",new String[]{String.valueOf(model.getId())});
    }

    public static int delete(SQLiteDatabase paramSQLiteDatabase, int id) {
        return paramSQLiteDatabase.delete(DBOpenHelper.ALARM_TABLE_NAME, ALARM_ID + " = ? ", new String[]{String.valueOf(id)});
    }

    public static ArrayList<AlarmDatabaseModel> getAllAlarms(SQLiteDatabase db) {
        ArrayList<AlarmDatabaseModel> model = new ArrayList<>();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.ALARM_TABLE_NAME + " WHERE dateDeleted is null", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                model.add(getInfo(cursor));
                cursor.moveToNext();
            }
        }
        return model;
    }

    public static ArrayList<AlarmDatabaseModel> getAllAlarmsForHistory(SQLiteDatabase db) {
        ArrayList<AlarmDatabaseModel> model = new ArrayList<>();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.ALARM_TABLE_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                model.add(getInfo(cursor));
                cursor.moveToNext();
            }
        }
        return model;
    }
}
