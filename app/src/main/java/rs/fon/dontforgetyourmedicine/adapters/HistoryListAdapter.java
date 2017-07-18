package rs.fon.dontforgetyourmedicine.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import rs.fon.dontforgetyourmedicine.R;
import rs.fon.dontforgetyourmedicine.data.DBOpenHelper;
import rs.fon.dontforgetyourmedicine.data.managers.HistoryManager;
import rs.fon.dontforgetyourmedicine.data.model.HistoryModel;
import rs.fon.dontforgetyourmedicine.util.MedicineConstants;

/**
 * Created by radovan.bogdanic on 3/21/2017.
 */

public class HistoryListAdapter extends ArrayAdapter<HistoryModel> {

    private ArrayList<HistoryModel> alarms;
    private Activity context;

    public HistoryListAdapter(Context context, ArrayList<HistoryModel> alarms) {
        super(context, R.layout.history_fragment_list_show, alarms);
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
    public HistoryModel getItem(int position) {
        return alarms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        final HistoryModel alarmModel = getItem(position);

        if(convertView == null) {

            view = LayoutInflater.from(getContext()).inflate(R.layout.history_fragment_list_show,parent,false);

            viewHolder = new ViewHolder();

            viewHolder.tvDate = (TextView) view.findViewById(R.id.histDate);
            viewHolder.tvMedicName = (TextView) view.findViewById(R.id.tvHistMedicName);
            viewHolder.tvMedicTime = (TextView) view.findViewById(R.id.tvHistTime);
            viewHolder.tvHistRepeat = (TextView) view.findViewById(R.id.histRepeat);
            viewHolder.tvFrom = (TextView) view.findViewById(R.id.histUntil);
            viewHolder.tvStartDate = (TextView) view.findViewById(R.id.histStartDate);
            viewHolder.tvTo = (TextView) view.findViewById(R.id.histTo);
            viewHolder.tvEndDate = (TextView) view.findViewById(R.id.histEndDate);
            viewHolder.btnDelete = (ImageButton) view.findViewById(R.id.imgHistDel);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(MedicineConstants.DATE_FORMAT, Locale.US);

        viewHolder.tvDate.setText(sdf.format(alarmModel.getDateCreated()));
        viewHolder.tvMedicName.setText(alarmModel.getHistmMedicName());
        viewHolder.tvMedicTime.setText(R.string.alarm_on_every);
        viewHolder.tvHistRepeat.setText(String.valueOf(alarmModel.getHistAlarmRepeat()));
        viewHolder.tvFrom.setText(R.string.alarm_hours_set);
        viewHolder.tvStartDate.setText(sdf.format(alarmModel.getHistStartDate()));
        viewHolder.tvTo.setText(R.string.alarm_hours_set2);
        viewHolder.tvEndDate.setText(sdf.format(alarmModel.getHistEndDate()));

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.d_delete);
                builder.setMessage(R.string.d_warning);
                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HistoryManager.delete(DBOpenHelper.getSQLiteDatabase(context),alarmModel.getHistid());
                        alarms.remove(position);
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

    private class ViewHolder {

        TextView tvDate, tvMedicName, tvMedicTime, tvHistRepeat, tvFrom, tvStartDate, tvTo, tvEndDate;
        ImageButton btnDelete;
    }
}
