package com.t9l.millionkitchen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.Session;
import com.t9l.millionkitchen.sessions.SessionManager;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment implements
        View.OnClickListener {
    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    public DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    private RelativeLayout
            relNotification, relMyProfile,
            relSignOut;

    private RelativeLayout relLogin,relRegister, relFoodItems;
    private LinearLayout linUser, linLogin;


    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState
                    .getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }
// Select either the default item (0) or the last selected item.
        SessionManager sessionManager = new SessionManager(getActivity());
        if (sessionManager != null && sessionManager.isLoggedIn()) {
            selectItem(0);
        } else
            selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer,
                container, false);
        relNotification = (RelativeLayout) view
                .findViewById(R.id.rel_Notification);
        relMyProfile = (RelativeLayout) view.findViewById(R.id.rel_Profile);
        relSignOut = (RelativeLayout) view.findViewById(R.id.rel_SignOut);

        relFoodItems = (RelativeLayout) view.findViewById(R.id.rel_food_items);
        relLogin = (RelativeLayout) view.findViewById(R.id.rel_login);
        relRegister = (RelativeLayout) view.findViewById(R.id.rel_register);

        linLogin = (LinearLayout) view.findViewById(R.id.linLogin);
        linUser = (LinearLayout) view.findViewById(R.id.linUser);

        updateDrawerOnSession();

        relNotification.setOnClickListener(this);
        relMyProfile.setOnClickListener(this);
        relSignOut.setOnClickListener(this);
        relFoodItems.setOnClickListener(this);
        relLogin.setOnClickListener(this);
        relRegister.setOnClickListener(this);


        return view;
    }

    private void updateDrawerOnSession() {
        SessionManager sessionManager = new SessionManager(getActivity());
        if (sessionManager != null && sessionManager.isLoggedIn()) {
            linLogin.setVisibility(View.GONE);
            linUser.setVisibility(View.VISIBLE);
        } else {
            linLogin.setVisibility(View.VISIBLE);
            linUser.setVisibility(View.GONE);
        }
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null
                && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer
        // opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.drawable.left_menu, /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open, /*
                                         * "open drawer" description for
										 * accessibility
										 */
                R.string.navigation_drawer_close /*
                                         * "close drawer" description for
										 * accessibility
										 */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }
                updateDrawerOnSession();

                getActivity().invalidateOptionsMenu(); // calls
                // onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }
                updateDrawerOnSession();

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to
                    // prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true)
                            .apply();
                }

                getActivity().invalidateOptionsMenu(); // calls
                // onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce
        // them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {


        mCurrentSelectedPosition = position;

        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }

        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    public void setDrawer(int val) {
        mDrawerLayout.setDrawerLockMode(val);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

//        if (item.getItemId() == R.id.action_notification) {
//            Toast.makeText(getActivity(), "Notifications Screen.", Toast.LENGTH_SHORT).show();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false); // Hide title text even on drawer opened
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(relFoodItems)) {
            selectItem(0);
        } else if (v.equals(relNotification)) {
            selectItem(1);
        } else if (v.equals(relMyProfile)) {
            selectItem(2);
        } else if (v.equals(relSignOut)) {
            openDialog(getActivity());
        } else if (v.equals(relLogin)) {
            selectItem(3);
        } else if(v.equals(relRegister)){
            selectItem(4);
        }
    }

    public void openDialog(final Context ctx) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
        dialog.setTitle("Alert!");
        dialog.setCancelable(true);
        dialog.setMessage("Are you sure want to sign out?");
        dialog.setPositiveButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        mDrawerLayout.closeDrawers();
                    }
                });
        dialog.setNegativeButton("Sign Out",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                        dialog.dismiss();

                    }
                });
        dialog.show();

    }

    public void logout() {
        SessionManager sessionManager = new SessionManager(getActivity());
        if (sessionManager != null)
            sessionManager.logoutUser();

        Session session = Session.getActiveSession();
        if (session != null)
            session.closeAndClearTokenInformation();
//        MainLoginFragment f=(MainLoginFragment)getActivity().getSupportFragmentManager().findFragmentByTag("Login");
//        if(f!=null) {
//            if (f.mGoogleApiClient != null && f.mGoogleApiClient.isConnected()) {
//                Plus.AccountApi.clearDefaultAccount(f.mGoogleApiClient);
//                f.mGoogleApiClient.disconnect();
////                f.mGoogleApiClient.connect();
//            }
//        }

        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}