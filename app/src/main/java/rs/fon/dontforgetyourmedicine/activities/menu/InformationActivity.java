package rs.fon.dontforgetyourmedicine.activities.menu;

import android.os.Bundle;
import android.widget.TextView;

import rs.fon.dontforgetyourmedicine.BuildConfig;
import rs.fon.dontforgetyourmedicine.R;
import rs.fon.dontforgetyourmedicine.activities.base.NavigationActivity;

/**
 * Created by radovan.bogdanic on 4/6/2017.
 */

public class InformationActivity extends NavigationActivity {

    @Override
    protected int getContentAreaLayoutId() {
        return R.layout.activity_information;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView appVersion = (TextView) findViewById(R.id.app_version);

        String versionName = BuildConfig.VERSION_NAME;
        String text = String.format(getString(R.string.info_version),versionName);
        if(appVersion != null) {
            appVersion.setText(text);
        }
    }
}
