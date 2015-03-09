package com.t9l.millionkitchen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.t9l.millionkitchen.AboutMeFragment;
import com.t9l.millionkitchen.DishesFragment;
import com.t9l.millionkitchen.OrdersFragment;

public class ProfileViewPagerAdapter extends FragmentPagerAdapter {

    // Declare the number of ViewPager pages
    final int PAGE_COUNT = 3;
    private String titles[] = new String[]{"About Me", "Dishes", "Orders"};

    public ProfileViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position) {
            case 0:
                fragment = new AboutMeFragment();
                break;
            case 1:
                fragment = new DishesFragment();
                break;
            case 2:
                fragment = new OrdersFragment();
                break;
        }
        return fragment;
    }

    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}