package rs.fon.dontforgetyourmedicine.data.managers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

import rs.fon.dontforgetyourmedicine.data.DBOpenHelper;
import rs.fon.dontforgetyourmedicine.data.model.AlarmDatabaseModel;
import rs.fon.dontforgetyourmedicine.data.model.HistoryModel;


/**
 * Created by radovan.bogdanic on 6/6/2017.
 */

public class HistoryManager {

    public static final String ALARM_ID_H = "histid";
    public static final String MEDIC_NAME_H = "histName";
    public static final String ALARM_START_DATE_H = "histStartDate";
    public static final String ALARM_REPEAT_H = "histHoursRepeat";
    public static final String ALARM_END_DATE_H = "histEndDate";
    public static final String ALARM_DATE_CREATED_H = "histDateCreated";

    public static HistoryModel getInfo(Cursor cursor) {
        HistoryModel alarm = new HistoryModel();
        alarm.setHistid(cursor.getInt(cursor.getColumnIndex(ALARM_ID_H)));
        alarm.setHistmMedicName(cursor.getString(cursor.getColumnIndex(MEDIC_NAME_H)));
        alarm.setHistStartDate(new Date(cursor.getLong(cursor.getColumnIndex(ALARM_START_DATE_H))));
        alarm.setHistAlarmRepeat(cursor.getInt(cursor.getColumnIndex(ALARM_REPEAT_H)));
        alarm.setHistEndDate(new Date(cursor.getLong(cursor.getColumnIndex(ALARM_END_DATE_H))));
        alarm.setDateCreated(new Date(cursor.getLong(cursor.getColumnIndex(ALARM_DATE_CREATED_H))));
        return alarm;
    }

    public static long insert(HistoryModel model, SQLiteDatabase db) {

        ContentValues content = new ContentValues();

        content.put(MEDIC_NAME_H,model.getHistmMedicName());
        content.put(ALARM_START_DATE_H, model.getHistStartDate().getTime());
        content.put(ALARM_REPEAT_H,model.getHistAlarmRepeat());
        content.put(ALARM_END_DATE_H,model.getHistEndDate().getTime());
        content.put(ALARM_DATE_CREATED_H,model.getDateCreated().getTime());

        return db.insert(DBOpenHelper.HISTORY_TABLE_NAME,null,content);
    }


    public static int delete(SQLiteDatabase paramSQLiteDatabase, int id) {
        return paramSQLiteDatabase.delete(DBOpenHelper.HISTORY_TABLE_NAME, ALARM_ID_H + " = ? ", new String[]{String.valueOf(id)});
    }

    public static ArrayList<HistoryModel> getAllAlarmsForHistory(SQLiteDatabase db) {
        ArrayList<HistoryModel> model = new ArrayList<>();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.HISTORY_TABLE_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                model.add(getInfo(cursor));
                cursor.moveToNext();
            }
        }
        return model;
    }
}
