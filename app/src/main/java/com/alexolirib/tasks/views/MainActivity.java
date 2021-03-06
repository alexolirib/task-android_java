package com.alexolirib.tasks.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.alexolirib.tasks.R;
import com.alexolirib.tasks.constants.TaskConstants;
import com.alexolirib.tasks.infra.operation.OperationListener;
import com.alexolirib.tasks.manager.PersonManager;
import com.alexolirib.tasks.manager.PriorityManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private Context mContext;
    private PersonManager mPersonManager;
    private PriorityManager mPriorityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mContext = this;

        mPriorityManager = new PriorityManager(mContext);
        mPersonManager = new PersonManager(mContext);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.initLoad();

        // Incia a fragment padrão
        this.startDefaultFragment();
    }

    private void initLoad() {
        this.mPriorityManager.getList(priorityListener());
    }

    private OperationListener priorityListener(){
        return new OperationListener<Boolean>(){
            @Override
            public void onSuccess(Boolean result) {
                super.onSuccess(result);
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                super.onError(errorCode, errorMessage);
            }
        };
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        int id = item.getItemId();

        try {
            if (id == R.id.nav_all_tasks) {
                fragment = TaskListFragment.newInstance(TaskConstants.TASK_FILTER.NO_FILTER);
            } else if (id == R.id.nav_next_seven_days) {
                fragment = TaskListFragment.newInstance(TaskConstants.TASK_FILTER.NEXT_7_DAYS);
            } else if (id == R.id.nav_overdue) {
                fragment = TaskListFragment.newInstance(TaskConstants.TASK_FILTER.OVERDUE);
            } else if (id == R.id.nav_logout) {

                handleLogout();

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insere fragment substituindo qualquer existente
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_content, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleLogout() {
        mPersonManager.logout();
        startActivity(new Intent(mContext, LoginActivity.class));
        finish();
    }

    /**
     * Incia a fragment padrão
     */
    private void startDefaultFragment() {

        Fragment fragment = null;
        try {
            fragment = TaskListFragment.newInstance(TaskConstants.TASK_FILTER.NO_FILTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insere fragment substituindo qualquer existente
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_content, fragment).commit();
    }

    /**
     * ViewHolder
     */
    private static class ViewHolder {
    }
}
