package com.example.amit.dhareshwar_maintenance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserProfileActivity extends AppCompatActivity
        implements KeyEvent.Callback, NavigationView.OnNavigationItemSelectedListener, UserDashboard.OnFragmentInteractionListener, UserPayments.OnFragmentInteractionListener, Notices.OnFragmentInteractionListener, NoticeFragment.OnFragmentInteractionListener, Home.OnFragmentInteractionListener, At_a_glance.OnFragmentInteractionListener, MakeNotice.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Flat no: " + getSharedPreferences(MainActivity.LOGIN_INFO_SHARED_PREFS,MODE_PRIVATE).getString("username",null).substring(6));

        final FrameLayout container = (FrameLayout) findViewById(R.id.container);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) container.getLayoutParams();
        layoutParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,105,getResources().getDisplayMetrics());
        container.setLayoutParams(layoutParams);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Home()).commit();

        FloatingActionsMenu fab_menu = (FloatingActionsMenu) findViewById(R.id.fab_menu);
        fab_menu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                container.setAlpha(0.5f);
            }

            @Override
            public void onMenuCollapsed() {
                container.setAlpha(1);
            }
        });
        
        AddFloatingActionButton fab_cash = (AddFloatingActionButton) findViewById(R.id.fab_cash);
        fab_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new MakePayment().execute(getSharedPreferences(MainActivity.LOGIN_INFO_SHARED_PREFS, MODE_PRIVATE).getString("username", null), getString(R.string.pay_PHP), "Cash", "")
            }
        });
        AddFloatingActionButton fab_cheque = (AddFloatingActionButton) findViewById(R.id.fab_cheque);
        fab_cheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserProfileActivity.this, "Cheque", Toast.LENGTH_SHORT).show();
            }
        });

//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//        mViewPager = (ViewPager) findViewById(R.id.viewPager);
//        mViewPager.setAdapter(mSectionsPagerAdapter);

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        getMenuInflater().inflate(R.menu.user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        FrameLayout container = (FrameLayout) findViewById(R.id.container);

        if (id == R.id.home) {
            getSupportActionBar().setTitle("Flat no: " + getSharedPreferences(MainActivity.LOGIN_INFO_SHARED_PREFS,MODE_PRIVATE).getString("username",null).substring(6));
            tabLayout.setVisibility(TabLayout.VISIBLE);
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) container.getLayoutParams();
            layoutParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,105,getResources().getDisplayMetrics());
            container.setLayoutParams(layoutParams);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Home()).commit();
        }
        else if (id == R.id.at_a_glance) {
            getSupportActionBar().setTitle("At a glance");
            tabLayout.setVisibility(TabLayout.GONE);
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) container.getLayoutParams();
            layoutParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,getResources().getDisplayMetrics());
            container.setLayoutParams(layoutParams);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new At_a_glance()).addToBackStack(null).commit();

//            //======================================================================================
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            DateFormat dt = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
//            try {
//                Toast.makeText(this, dt.format(Calendar.getInstance().getTime()), Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, dt.format(Calendar.getInstance().getTime()), Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, Calendar.getInstance().getTime().toString(), Toast.LENGTH_LONG).show();
//            //======================================================================================
        }
        else if (id == R.id.payments_to_be_confirmed) {
            getSupportActionBar().setTitle("To be confirmed.");
            tabLayout.setVisibility(TabLayout.GONE);
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) container.getLayoutParams();
            layoutParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,getResources().getDisplayMetrics());
            container.setLayoutParams(layoutParams);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, UserPayments.newInstance(null, null, UserPayments.TO_BE_CONFIRMED)).addToBackStack(null).commit();
        }
        else if (id == R.id.details_of_all_payments) {
            getSupportActionBar().setTitle("All payments.");
            tabLayout.setVisibility(TabLayout.GONE);
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) container.getLayoutParams();
            layoutParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,getResources().getDisplayMetrics());
            container.setLayoutParams(layoutParams);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, UserPayments.newInstance(null, null, UserPayments.ALL_USERS)).addToBackStack(null).commit();
        }
        else if (id == R.id.receipts_to_be_sent) {
            getSupportActionBar().setTitle("Receipts to be sent.");
            tabLayout.setVisibility(TabLayout.GONE);
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) container.getLayoutParams();
            layoutParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,getResources().getDisplayMetrics());
            container.setLayoutParams(layoutParams);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, UserPayments.newInstance(null, null, UserPayments.RECEIPTS_TO_BE_SENT)).addToBackStack(null).commit();
        }
        else if (id == R.id.requests_for_receipts) {
            getSupportActionBar().setTitle("Receipts to be sent.");
            tabLayout.setVisibility(TabLayout.GONE);
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) container.getLayoutParams();
            layoutParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,getResources().getDisplayMetrics());
            container.setLayoutParams(layoutParams);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, UserPayments.newInstance(null, null, UserPayments.REQUESTS_FOR_RECEIPTS)).addToBackStack(null).commit();
        }
        else if (id == R.id.send_notice) {
            getSupportActionBar().setTitle("Send a notice.");
            tabLayout.setVisibility(TabLayout.GONE);
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) container.getLayoutParams();
            layoutParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,getResources().getDisplayMetrics());
            container.setLayoutParams(layoutParams);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, MakeNotice.newInstance(null, null)).addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e("a","aa");
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportActionBar().setTitle("Flat no: " + getSharedPreferences(MainActivity.LOGIN_INFO_SHARED_PREFS,MODE_PRIVATE).getString("username",null).substring(6));

                FrameLayout container = (FrameLayout) findViewById(R.id.container);
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) container.getLayoutParams();
                layoutParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,105,getResources().getDisplayMetrics());
                container.setLayoutParams(layoutParams);
                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setVisibility(TabLayout.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Home()).commit();
            }
            else {
                return super.onKeyUp(keyCode, event);
            }
        }
        else {
            return super.onKeyUp(keyCode, event);
        }
        return true;

    }
}
