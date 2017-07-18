package rs.fon.dontforgetyourmedicine.alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

import rs.fon.dontforgetyourmedicine.R;
import rs.fon.dontforgetyourmedicine.activities.menu.TodayActivity;
import rs.fon.dontforgetyourmedicine.data.DBOpenHelper;
import rs.fon.dontforgetyourmedicine.data.managers.AlarmDatabaseManager;
import rs.fon.dontforgetyourmedicine.data.model.AlarmDatabaseModel;
import rs.fon.dontforgetyourmedicine.util.SharedPrefs;

/**
 * Created by radovan.bogdanic on 3/27/2017.
 */

public class NotifyService extends Service {

    AlarmDatabaseModel model;

    /**
     * Class for clients to access
     */
    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }

    private static final int NOTIFICATION = 123;
    public static final String INTENT_NOTIFY = "INTENT_NOTIFY";
    private NotificationManager mNM;

    @Override
    public void onCreate() {
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (SharedPrefs.getPreferences().isNotificationnOn())
            // If this service was started by out AlarmTask intent then we want to show our notification
            if (intent.getBooleanExtra(INTENT_NOTIFY, false))
                showNotification();
        // We don't care if this service is stopped as we have already delivered our notification
        return START_NOT_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients
    private final IBinder mBinder = new ServiceBinder();

    private void showNotification() {
        ArrayList<AlarmDatabaseModel> alarms = AlarmDatabaseManager.getAllAlarms(DBOpenHelper.getSQLiteDatabase(this));
        for (int i = 0; i < alarms.size(); i++) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_alarm_on_white_48dp)
                            .setContentTitle(getString(R.string.app_name))
                            .setContentText(alarms.get(i).getMedicName());

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, TodayActivity.class), 0);
        mBuilder.setContentIntent(contentIntent);
        // Send the notification to the system.
        mNM.notify(NOTIFICATION, mBuilder.build());

        // Stop the service when we are finished
        stopSelf();
        }
    }
}
