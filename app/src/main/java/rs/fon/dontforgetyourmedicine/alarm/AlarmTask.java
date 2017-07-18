package rs.fon.dontforgetyourmedicine.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import rs.fon.dontforgetyourmedicine.util.SharedPrefs;

/**
 * Created by radovan.bogdanic on 3/27/2017.
 */

public class AlarmTask implements Runnable {

    private final Calendar date;
    private final AlarmManager am;
    private final Context context;

    public AlarmTask(Context context, Calendar date) {
        this.context = context;
        this.am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.date = date;
    }

    @Override
    public void run() {
        // Request to start are service when the alarm date is upon us
        // We don't start an activity as we just want to pop up a notification into the system bar not a full activity
        if(SharedPrefs.getPreferences().isNotificationnOn()) {
            Intent intent = new Intent(context, NotifyService.class);
            intent.putExtra(NotifyService.INTENT_NOTIFY, true);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

            am.set(AlarmManager.RTC, date.getTimeInMillis(), pendingIntent);
        }
    }
}
