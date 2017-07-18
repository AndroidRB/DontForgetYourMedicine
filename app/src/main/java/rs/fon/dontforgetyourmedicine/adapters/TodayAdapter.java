package rs.fon.dontforgetyourmedicine.adapters;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import rs.fon.dontforgetyourmedicine.R;
import rs.fon.dontforgetyourmedicine.activities.menu.ReminderActivity;
import rs.fon.dontforgetyourmedicine.alarm.AlarmReceiver;
import rs.fon.dontforgetyourmedicine.data.DBOpenHelper;
import rs.fon.dontforgetyourmedicine.data.managers.AlarmDatabaseManager;
import rs.fon.dontforgetyourmedicine.data.model.AlarmDatabaseModel;
import rs.fon.dontforgetyourmedicine.util.MedicineConstants;

/**
 * Created by radovan.bogdanic on 3/16/2017.
 */

public class TodayAdapter extends ArrayAdapter<AlarmDatabaseModel>{

    private ArrayList<AlarmDatabaseModel> alarms;
    private Activity context;

    public TodayAdapter(Context context, ArrayList<AlarmDatabaseModel>alarms) {
        super(context, R.layout.activity_today_show, alarms);
        this.context = (Activity) context;
        this.alarms = alarms;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public int getCount() {
        return alarms.size();
    }

    @Nullable
    @Override
    public AlarmDatabaseModel getItem(int position) {
        return alarms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        final AlarmDatabaseModel alarmModel = getItem(position);

        if(convertView == null) {

            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_today_show,parent,false);

            viewHolder = new ViewHolder();

            viewHolder.tvMedicName = (TextView) view.findViewById(R.id.todayMedicName);
            viewHolder.tdyOnEvery = (TextView) view.findViewById(R.id.tdyOnEvery);
            viewHolder.tdyHours = (TextView) view.findViewById(R.id.tdyRepeat);
            viewHolder.tdyUntil = (TextView) view.findViewById(R.id.tdyUntil);
            viewHolder.tdyEndDate = (TextView) view.findViewById(R.id.tdyEndDate);
            viewHolder.btnStopAlarm = (ImageButton) view.findViewById(R.id.btnStopAlarm);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SimpleDateFormat sdfDate = new SimpleDateFormat(MedicineConstants.DATE_FORMAT, Locale.US);

        viewHolder.tvMedicName.setText(alarmModel.getMedicName());
        viewHolder.tdyOnEvery.setText(R.string.alarm_on_every);
        viewHolder.tdyHours.setText(String.valueOf(alarmModel.getAlarmRepeat()));
        viewHolder.tdyUntil.setText(R.string.alarm_hours_set);
        viewHolder.tdyEndDate.setText(sdfDate.format(alarmModel.getAlarmExpireDate()));

        viewHolder.btnStopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.d_stop_alarm);
                builder.setMessage(R.string.d_stop_message);
                builder.setPositiveButton(R.string.d_stop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stopAlarm();
                        alarms.remove(position);
                        alarmModel.setDateDeleted(new Date());
                        AlarmDatabaseManager.update(alarmModel,DBOpenHelper.getSQLiteDatabase(context));
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }

    private void stopAlarm() {
        Toast.makeText(context,R.string.alarm_stoped,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context.getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getBaseContext(), MedicineConstants.RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }

    private class ViewHolder {

        TextView tvMedicName, tdyOnEvery,tdyHours,tdyUntil,tdyEndDate;
        ImageButton btnStopAlarm;
    }
}
