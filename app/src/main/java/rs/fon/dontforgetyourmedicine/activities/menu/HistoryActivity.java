package rs.fon.dontforgetyourmedicine.activities.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import rs.fon.dontforgetyourmedicine.R;
import rs.fon.dontforgetyourmedicine.activities.base.NavigationActivity;
import rs.fon.dontforgetyourmedicine.adapters.HistoryListAdapter;
import rs.fon.dontforgetyourmedicine.data.DBOpenHelper;
import rs.fon.dontforgetyourmedicine.data.managers.AlarmDatabaseManager;
import rs.fon.dontforgetyourmedicine.data.managers.HistoryManager;
import rs.fon.dontforgetyourmedicine.data.model.AlarmDatabaseModel;
import rs.fon.dontforgetyourmedicine.data.model.HistoryModel;

/**
 * Created by radovan.bogdanic on 3/17/2017.
 */

public class HistoryActivity extends NavigationActivity {

    ListView alarmList;
    TextView tvHistText;

    @Override
    protected int getContentAreaLayoutId() {
        return R.layout.history_fragment_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alarmList = (ListView) findViewById(R.id.historyList);
        tvHistText = (TextView) findViewById(R.id.tvHistText);
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshData();
    }

    private void refreshData() {
        ArrayList<HistoryModel> alarms = HistoryManager.getAllAlarmsForHistory(DBOpenHelper.getSQLiteDatabase(this));
        HistoryListAdapter adapter = new HistoryListAdapter(this,alarms);
        alarmList.setAdapter(adapter);

        if(alarms.isEmpty()) {
            alarmList.setVisibility(View.GONE);
            tvHistText.setVisibility(View.VISIBLE);
        }
        else {
            tvHistText.setVisibility(View.GONE);
        }
    }
}
