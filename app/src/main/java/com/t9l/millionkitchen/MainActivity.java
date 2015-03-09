package com.t9l.millionkitchen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                if (mNavigationDrawerFragment.isDrawerOpen()) {
//                    mNavigationDrawerFragment.mDrawerLayout.closeDrawers();
//                }
//            }
//        }, 1500);
    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }
        switch (position) {
            case 0:
                if (fragmentManager.findFragmentByTag("Food Items") == null) {
                    fragmentManager
                            .beginTransaction()
                            .add(R.id.container,
                                    new FoodListingsFragment(),
                                    "Food Items")
                            .commit();
                }
                setTitle("Food Items");
                break;
            case 1:
                if (fragmentManager.findFragmentByTag("Notifications") == null) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.container, new EmptyFragment(),
                                    "Notifications").addToBackStack("Notifications").commit();
                }
                setTitle("Notifications");
                break;
            case 2:
                if (fragmentManager.findFragmentByTag("My Profile") == null) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.container, new ProfileFragment(),
                                    "My Profile").addToBackStack("My Profile")
                            .commit();
                }
                setTitle("My Profile");
                break;
            case 3:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
            case 4:
                if (fragmentManager.findFragmentByTag("Register") == null) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.container, new RegisterFragment(),
                                    "Register").addToBackStack("Register")
                            .commit();
                }
                setTitle("Register");
                break;
        }
        restoreActionBar();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(false); // Set this to true/false to show/hide the title of the action bar
//        actionBar.setTitle(mTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            menu.findItem(R.id.action_notification).setActionView(R.layout.notification_custom);
            restoreActionBar();
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}