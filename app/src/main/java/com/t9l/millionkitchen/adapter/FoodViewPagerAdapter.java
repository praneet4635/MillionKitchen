package com.t9l.millionkitchen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.t9l.millionkitchen.DabbaFragment;
import com.t9l.millionkitchen.AuntyFragment;
import com.t9l.millionkitchen.ChefsFragment;

public class FoodViewPagerAdapter extends FragmentPagerAdapter {

    // Declare the number of ViewPager pages
    final int PAGE_COUNT = 3;
    private String titles[] = new String[]{"Dabba", "Aunty", "Chefs"};

    public FoodViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position) {
            case 0:
                fragment = new DabbaFragment();
                break;
            case 1:
                fragment = new AuntyFragment();
                break;
            case 2:
                fragment = new ChefsFragment();
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