package com.shuitianyun.kassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.csmall.log.LogHelper;
import com.csmall.report.ReportManager;
import com.csmall.version.VersionManager;
import com.shuitianyun.component.report.EventId;
import com.shuitianyun.kassistant.R;
import com.shuitianyun.kassistant.activity.web.WebActivityStart;
import com.shuitianyun.kassistant.presenter.MainPresenter;


public class DrawerActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private static final java.lang.String TAG = "DrawerActivity";

    private TextView tvBulletin;

    private MainPresenter mainPresenter;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LogHelper.d(TAG, "onClick");
            Intent intent;
            switch (v.getId()){
                case R.id.bn_main_localsong:
                    ReportManager.getInstance().reportClick(EventId.Click_DrawerActivity_LocalSong);
                    intent = new Intent(DrawerActivity.this, LocalSongActivity.class);
                    DrawerActivity.this.startActivity(intent);
                    break;
                case R.id.bn_publish:
                    ReportManager.getInstance().reportClick(EventId.Click_DrawerActivity_RemoteSong);
                    intent = new Intent(DrawerActivity.this, RemoteSongActivity.class);
                    DrawerActivity.this.startActivity(intent);
                    break;
            }
        }
    };

    public void updateBulletin(String s){
        tvBulletin.setText(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogHelper.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView tvVersion = (TextView) findViewById(R.id.tv_version);
        findViewById(R.id.bn_main_localsong).setOnClickListener(clickListener);
        findViewById(R.id.bn_publish).setOnClickListener(clickListener);
        tvVersion.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                HiddenActivity.strat();
                return true;
            }
        });

        String versionName = VersionManager.getVersionName();
        tvVersion.setText(String.format("版本：%s", versionName));

        tvBulletin = (TextView) findViewById(R.id.tv_bulletin);

        mainPresenter = new MainPresenter(this);
        mainPresenter.loadMain();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent intent = new Intent(this, SettingsActivity.class);
//            startActivity(intent);
//            return true;
//        }else
        if (id == R.id.action_to_feedback) {
            Intent intent = new Intent(this, FeedbackActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_to_bbs){
            WebActivityStart.startBbs(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
