package com.t9l.millionkitchen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.t9l.millionkitchen.adapter.FoodViewPagerAdapter;
import com.t9l.millionkitchen.customwidgets.SlidingTabLayout;
import com.t9l.millionkitchen.tools.Methods;

import java.lang.reflect.Field;

public class FoodListingsFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Methods.setMenuItems(menu, false, false, false);
        return;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_main, container, false);
        // Locate the ViewPager in viewpager_main.xml
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        // Set the ViewPagerAdapter into ViewPager
        mViewPager.setAdapter(new FoodViewPagerAdapter(getChildFragmentManager()));
        mViewPager.setOffscreenPageLimit(2); // Prevents the view pager fragments to destroy the contents and recreate it for all the 3 screens in view pager.
        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(android.R.color.white));
        mSlidingTabLayout.setCustomTabView(R.layout.custom_tab_title, R.id.tabtext);
        mSlidingTabLayout.setViewPager(mViewPager);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
