package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by amit on 12/12/16.
 */

public class HomeTabLayoutAdapter extends FragmentPagerAdapter {
    Context context;
    public HomeTabLayoutAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return UserDashboard.newInstance(null,null);
            case 1:
                return UserPayments.newInstance(null,null,UserPayments.SINGLE_USER);
            case 2:
                return Notices.newInstance(null,null);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String[] tab_headers = context.getResources().getStringArray(R.array.user_profile_activity_tabs_headers);
        switch (position) {
            case 0:
                return tab_headers[0];
            case 1:
                return tab_headers[1];
            case 2:
                return tab_headers[2];
        }

        return super.getPageTitle(position);
    }
}