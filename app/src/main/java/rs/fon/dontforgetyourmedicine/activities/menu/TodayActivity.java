package rs.fon.dontforgetyourmedicine.activities.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import rs.fon.dontforgetyourmedicine.R;
import rs.fon.dontforgetyourmedicine.activities.base.NavigationActivity;
import rs.fon.dontforgetyourmedicine.adapters.TodayAdapter;
import rs.fon.dontforgetyourmedicine.data.DBOpenHelper;
import rs.fon.dontforgetyourmedicine.data.managers.AlarmDatabaseManager;
import rs.fon.dontforgetyourmedicine.data.model.AlarmDatabaseModel;

/**
 * Created by radovan.bogdanic on 3/15/2017.
 */

public class TodayActivity extends NavigationActivity{

    ListView alarmList;
    TextView tdyNoMedic;

    @Override
    protected int getContentAreaLayoutId() {
        return R.layout.activity_today;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alarmList = (ListView) findViewById(R.id.medicationList);
        tdyNoMedic = (TextView) findViewById(R.id.tdyNoMedicine);

    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshData();
    }

    private void refreshData() {
        ArrayList<AlarmDatabaseModel> alarms = AlarmDatabaseManager.getAllAlarms(DBOpenHelper.getSQLiteDatabase(this));
        TodayAdapter adapter = new TodayAdapter(this, alarms);
        alarmList.setAdapter(adapter);

        if(alarms.isEmpty()) {
            alarmList.setVisibility(View.GONE);
            tdyNoMedic.setVisibility(View.VISIBLE);
        }
        else {
            tdyNoMedic.setVisibility(View.GONE);
        }
    }
}
