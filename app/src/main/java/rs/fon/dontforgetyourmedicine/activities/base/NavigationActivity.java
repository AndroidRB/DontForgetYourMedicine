package rs.fon.dontforgetyourmedicine.activities.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import rs.fon.dontforgetyourmedicine.R;
import rs.fon.dontforgetyourmedicine.activities.menu.HistoryActivity;
import rs.fon.dontforgetyourmedicine.activities.menu.InformationActivity;
import rs.fon.dontforgetyourmedicine.activities.menu.MapsActivity;
import rs.fon.dontforgetyourmedicine.activities.menu.ReminderActivity;
import rs.fon.dontforgetyourmedicine.activities.menu.SettingsActivity;
import rs.fon.dontforgetyourmedicine.activities.menu.TodayActivity;
import rs.fon.dontforgetyourmedicine.util.MedicineConstants;

public abstract class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected static final String TAG = NavigationActivity.class.getSimpleName();

    FloatingActionButton fab;
    NavigationView navigationView;
    private boolean doubleBackToExitPressedOnce;
    Activity activity;
    DrawerLayout drawer;
    private ProgressDialog mProgressDlg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setContentInside();

        activity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getActivityTitle());


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnotherActivity(ReminderActivity.class,R.id.nav_reminder);
            }
        });


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.setItemIconTintList(null);

    }

    protected FloatingActionButton getFab() {
        return fab;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.setCheckedItem(getMenuId());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }

        if (this.doubleBackToExitPressedOnce) {
            softExitFromApp();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1000);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                startAnotherActivity(TodayActivity.class, R.id.nav_home);
                break;
            case R.id.nav_reminder:
                startAnotherActivity(ReminderActivity.class,R.id.nav_reminder);
                break;
            case R.id.nav_map:
                startAnotherActivity(MapsActivity.class,R.id.nav_map);
                break;
            case R.id.nav_history:
                startAnotherActivity(HistoryActivity.class,R.id.nav_settings);
                break;
            case R.id.nav_settings:
                startAnotherActivity(SettingsActivity.class,R.id.nav_settings);
                break;
            case R.id.nav_info:
                startAnotherActivity(InformationActivity.class,R.id.nav_info);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setContentInside() {
        View.inflate(this, getContentAreaLayoutId(), (ViewGroup) findViewById(R.id.content_container));
    }

    protected abstract int getContentAreaLayoutId();

    protected void startAnotherActivity(Class<?> cls, @IdRes int id) {
        if (cls.getSimpleName().equals(this.getClass().getSimpleName())) return;
        Intent intent = new Intent(this, cls);
        intent.putExtra(MedicineConstants.MENU_ID_PARAMETER, id);
        startActivity(intent);
        finish();
    }

    private int getMenuId() {
        if (this.getClass().getSimpleName().equals(TodayActivity.class.getSimpleName())) {
            return R.id.nav_home;
        }
        if (this.getClass().getSimpleName().equals(ReminderActivity.class.getSimpleName())) {
            return R.id.nav_reminder;
        }
        if (this.getClass().getSimpleName().equals(MapsActivity.class.getSimpleName())) {
            return R.id.nav_map;
        }
        if (this.getClass().getSimpleName().equals(HistoryActivity.class.getSimpleName())) {
            return R.id.nav_history;
        }
        if (this.getClass().getSimpleName().equals(SettingsActivity.class.getSimpleName())) {
            return R.id.nav_settings;
        }
        if (this.getClass().getSimpleName().equals(InformationActivity.class.getSimpleName())) {
            return R.id.nav_info;
        }
        return R.id.nav_home;
    }

    private int getActivityTitle() {
        if (this.getClass().getSimpleName().equals(TodayActivity.class.getSimpleName())) {
            return R.string.app_name;
        }
        if (this.getClass().getSimpleName().equals(ReminderActivity.class.getSimpleName())) {
            return R.string.nav_profile;
        }
        if (this.getClass().getSimpleName().equals(MapsActivity.class.getSimpleName())) {
            return R.string.nav_map;
        }
        if (this.getClass().getSimpleName().equals(HistoryActivity.class.getSimpleName())) {
            return R.string.nav_history;
        }
        if (this.getClass().getSimpleName().equals(SettingsActivity.class.getSimpleName())) {
            return R.string.nav_settings;
        }
        if (this.getClass().getSimpleName().equals(InformationActivity.class.getSimpleName())) {
            return R.string.nav_info;
        }
        return R.string.app_name;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showWaitDialog(String title, String msg) {
        if (this.mProgressDlg == null) {
            this.mProgressDlg = new ProgressDialog(this);
            this.mProgressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            if (!TextUtils.isEmpty(title)) this.mProgressDlg.setTitle(title);
            if (!TextUtils.isEmpty(msg)) this.mProgressDlg.setMessage(msg);
            this.mProgressDlg.setIndeterminate(true);
            this.mProgressDlg.setCancelable(false);
            this.mProgressDlg.show();
        }
    }

    public void dismissWaitDialog() {
        if (this.mProgressDlg != null) {
            if (this.mProgressDlg.isShowing()) {
                this.mProgressDlg.dismiss();
            }
            this.mProgressDlg = null;
        }
    }

    public boolean isWaitDialogVisible() {
        return this.mProgressDlg != null && mProgressDlg.isShowing();
    }

    public void softExitFromApp() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }
}
