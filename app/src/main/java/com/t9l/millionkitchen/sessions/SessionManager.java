package com.t9l.millionkitchen.sessions;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.t9l.millionkitchen.dao.User;
import com.t9l.millionkitchen.tools.Methods;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name


    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User (make variable public to access from outside)
    public static final String KEY_USER = "user";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(Methods.PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(User userInfo) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        Gson gson = new Gson();
        String json = gson.toJson(userInfo);
        // Storing user in pref
        editor.putString(KEY_USER, json);
        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status If false it will redirect
     * user to login page Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            // Intent i = new Intent(_context, LoginActivity.class);
            // // Closing all the Activities
            // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //
            // // Add new Flag to start new Activity
            // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //
            // // Staring Login Activity
            // _context.startActivity(i);
        }

    }

    /**
     * Get stored session data
     */
    public User getUser() {
        Gson gson = new Gson();
        // user
        String json = pref.getString(KEY_USER, "");
        User userInfo = gson.fromJson(json, User.class);

        // return user
        return userInfo;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // // After logout redirect user to Loing Activity
        // Intent i = new Intent(_context, LoginActivity.class);
        // // Closing all the Activities
        // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //
        // // Add new Flag to start new Activity
        // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //
        // // Staring Login Activity
        // _context.startActivity(i);
    }

    /**
     * Quick check for login
     * *
     */
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
