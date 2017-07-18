package rs.fon.dontforgetyourmedicine.activities.menu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import rs.fon.dontforgetyourmedicine.alarm.ScheduleClient;
import rs.fon.dontforgetyourmedicine.data.managers.HistoryManager;
import rs.fon.dontforgetyourmedicine.data.model.HistoryModel;
import rs.fon.dontforgetyourmedicine.data.model.UserModel;
import rs.fon.dontforgetyourmedicine.dialogs.CustomDatePicker;
import rs.fon.dontforgetyourmedicine.dialogs.CustomTimePicker;
import rs.fon.dontforgetyourmedicine.dialogs.HourPickerDialog;
import rs.fon.dontforgetyourmedicine.R;
import rs.fon.dontforgetyourmedicine.activities.base.NavigationActivity;
import rs.fon.dontforgetyourmedicine.alarm.AlarmReceiver;
import rs.fon.dontforgetyourmedicine.data.DBOpenHelper;
import rs.fon.dontforgetyourmedicine.data.managers.AlarmDatabaseManager;
import rs.fon.dontforgetyourmedicine.data.model.AlarmDatabaseModel;
import rs.fon.dontforgetyourmedicine.util.MedicineConstants;
import rs.fon.dontforgetyourmedicine.util.SharedPrefs;

import static rs.fon.dontforgetyourmedicine.R.id.btnCOnfirmAlarm;

/**
 * Created by radovan.bogdanic on 3/9/2017.
 */

public class ReminderActivity extends NavigationActivity implements View.OnClickListener, HourPickerDialog.HourSetListener,
        CustomDatePicker.DateSetListener,CustomTimePicker.TimeSetListener {

    private ScheduleClient scheduleClient;

    Button confirmReminder;
    EditText etMedicName,etStartTime, etStartDate, etRepeatHours, etEndDate;

    Intent intent;
    Calendar startCal = Calendar.getInstance();
    Calendar endCal = Calendar.getInstance();
    AlarmManager alarmManager;
    int mode = 0;


    public static SharedPreferences sp;

    @Override
    protected int getContentAreaLayoutId() {
        return R.layout.activity_add_reminder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        PreferenceManager.setDefaultValues(this, R.xml.pref_settings,false);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        FloatingActionButton fab = getFab();
        fab.hide();

        etMedicName = (EditText) findViewById(R.id.etMedic);
        etStartTime = (EditText) findViewById(R.id.etSetTIme);
        etStartDate = (EditText) findViewById(R.id.etStartDate);
        etRepeatHours = (EditText) findViewById(R.id.etRepeatHours);
        etEndDate = (EditText) findViewById(R.id.etEndDate);
        confirmReminder = (Button) findViewById(btnCOnfirmAlarm);

        etStartTime.setOnClickListener(this);
        etStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showTimePicker();
                }
            }
        });
        etStartDate.setOnClickListener(this);
        etStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker();
                    mode = MedicineConstants.START_DATE;
                }
            }
        });
        etRepeatHours.setOnClickListener(this);
        etRepeatHours.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showHourPicker();
                }
            }
        });
        etEndDate.setOnClickListener(this);
        etEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker();
                    mode = MedicineConstants.END_DATE;
                }
            }
        });
        confirmReminder.setOnClickListener(this);

        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.etSetTIme:
                showTimePicker();
                break;

            case R.id.etStartDate:
                showDatePicker();
                break;

            case R.id.etRepeatHours:
                showHourPicker();
                break;

            case R.id.etEndDate:
                showDatePicker();
                break;

            case R.id.btnCOnfirmAlarm:
                try {
                    if(validation()) {
                        insertAlarmIntoDB();
                        scheduleClient.setAlarmForNotification(startCal);
                        setAlarm();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;

            default:
        }
    }

    @Override
    protected void onStop() {
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }

    private void showTimePicker() {
        CustomTimePicker.showTimePicker(this);
    }

    @Override
    public void onSetTime(int hour, int minute) {

        startCal.set(Calendar.HOUR_OF_DAY, hour);
        startCal.set(Calendar.MINUTE, minute);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);

        etStartTime.setText(hour+":"+minute);
    }

    private void showHourPicker() {
        HourPickerDialog.showPicker(this,String.valueOf(etRepeatHours.getText().toString()));
    }

    @Override
    public void onSetHourRepeat(int hour) {
        if (hour == 0) {
            etRepeatHours.setText("");
        } else {
            etRepeatHours.setText(String.valueOf(hour));
        }
    }

    private void showDatePicker() {
        CustomDatePicker.showDatePicker(this);
    }

    @Override
    public void onSetDate(int year, int mounth, int day) {

        int curMonth = mounth + 1;

        if(mode == MedicineConstants.START_DATE) {
            startCal.set(Calendar.YEAR, year);
            startCal.set(Calendar.MONTH, mounth);
            startCal.set(Calendar.DAY_OF_MONTH, day);
            etStartDate.setText(day + "." + curMonth + "." + year);
        }
        if(mode == MedicineConstants.END_DATE) {
            endCal.set(Calendar.YEAR, year);
            endCal.set(Calendar.MONTH, mounth);
            endCal.set(Calendar.DAY_OF_MONTH, day);
            etEndDate.setText(day + "." + curMonth + "." + year);
        }
    }

    private void setAlarm() {
        Toast.makeText(ReminderActivity.this,getString(R.string.alarm_set) + startCal.getTime(), Toast.LENGTH_LONG).show();
        intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), MedicineConstants.RQS_1, intent, 0);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, startCal.getTimeInMillis(), pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setWindow(AlarmManager.RTC_WAKEUP,startCal.getTimeInMillis(),endCal.getTimeInMillis(),pendingIntent);
        }
        else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, startCal.getTimeInMillis(), pendingIntent);
        }
        int hourPickerValue = Integer.parseInt(etRepeatHours.getText().toString());
        int repeatHours = hourPickerValue * 60;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startCal.getTimeInMillis(), repeatHours * 60 * 1000, pendingIntent);
        backToTodayActivity();
    }


    private void backToTodayActivity() {
        intent = new Intent(ReminderActivity.this, TodayActivity.class);
        startActivity(intent);
        finish();
    }

    private void insertAlarmIntoDB() {

            String medicName = etMedicName.getText().toString();
            String alarmTIme = etStartTime.getText().toString();
            String alarmStartDate = etStartDate.getText().toString();
            String alarmERepeatHours = etRepeatHours.getText().toString();
            String alarmExpireDate = etEndDate.getText().toString();
            Date dateAlarmTime = null;
            Date startDate = null;
            Date dateExpire = null;
            SimpleDateFormat sdf1 = new SimpleDateFormat(MedicineConstants.TIME_FORMAT, Locale.US);
            SimpleDateFormat sdf2 = new SimpleDateFormat(MedicineConstants.DATE_FORMAT, Locale.US);
            try {
                dateAlarmTime = sdf1.parse(alarmTIme);
                startDate = sdf2.parse(alarmStartDate);
                dateExpire = sdf2.parse(alarmExpireDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int repeatHours = Integer.parseInt(alarmERepeatHours);

            AlarmDatabaseModel alarmModel = new AlarmDatabaseModel(medicName, dateAlarmTime, startDate, repeatHours, dateExpire, new Date());
            AlarmDatabaseManager.insert(alarmModel, DBOpenHelper.getSQLiteDatabase(ReminderActivity.this));

            HistoryModel historyModel = new HistoryModel(medicName,startDate,repeatHours,dateExpire, new Date());
            HistoryManager.insert(historyModel, DBOpenHelper.getSQLiteDatabase(ReminderActivity.this));

//        if(!SharedPrefs.getPreferences().isVisitor()) {
//            final FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference ref = database.getReference("server/saving-data");
//            DatabaseReference userRef = ref.child("alarms");
//            DatabaseReference newUserRef = userRef.push();
////            Map<String, AlarmDatabaseModel> alarms = new HashMap<>();
////            alarms.put("alarm", new AlarmDatabaseModel(medicName,dateAlarmTime,startDate,repeatHours,dateExpire,new Date()));
//            newUserRef.setValue(new AlarmDatabaseModel(medicName,dateAlarmTime,startDate,repeatHours,dateExpire,new Date()));
//        }

    }

    private boolean validation() throws ParseException {
        if(etMedicName.getText().toString().isEmpty()) {
            etMedicName.setError(getString(R.string.med_name));
            return false;
        }
        if(etStartTime.getText().toString().isEmpty()) {
            etStartTime.setError(getString(R.string.med_st_time));
            return false;
        }
        else {
            etStartTime.setError(null);
        }
        if(etStartDate.getText().toString().isEmpty()) {
            etStartDate.setError(getString(R.string.med_st_date));
            return false;
        }
        else {
            etStartDate.setError(null);
        }
        if(etRepeatHours.getText().toString().isEmpty()) {
            etRepeatHours.setError(getString(R.string.med_rep));
            return false;
        }
        else {
            etRepeatHours.setError(null);
        }
        if(etEndDate.getText().toString().isEmpty()) {
            etEndDate.setError(getString(R.string.med_end_date));
            return false;
        }
        else {
            etEndDate.setError(null);
        }
        return true;
    }
}