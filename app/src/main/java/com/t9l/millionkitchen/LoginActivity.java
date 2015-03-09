package com.t9l.millionkitchen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.t9l.millionkitchen.dao.User;
import com.t9l.millionkitchen.sessions.SessionManager;
import com.t9l.millionkitchen.tools.Methods;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends Activity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;
    Animation animTranslate;
    TextView app_logo_name;
    ImageView imgLogo;
    LinearLayout buttonsLayout;

    ProgressDialog pd;
    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;

    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
    private boolean mIntentInProgress;
    /* Track whether the sign-in button has been clicked so that we know to resolve
 * all issues preventing sign-in without waiting.
 */
    private boolean mSignInClicked;

    /* Store the connection result from onConnectionFailed callbacks so that we can
     * resolve them when the user clicks sign-in.
     */
    private ConnectionResult mConnectionResult;
    User user;

    public Session.StatusCallback statusCallback = new SessionStatusCallback();
    /* Client used to interact with Google APIs. */
    public GoogleApiClient mGoogleApiClient;
    SessionManager sessionManager;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        sessionManager = new SessionManager(this);
        setContentView(R.layout.activity_login);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
        pd = new ProgressDialog(LoginActivity.this);
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.setMessage("Please Wait...");
        app_logo_name = (TextView) findViewById(R.id.app_logo_name);
        imgLogo = (ImageView) findViewById(R.id.logo);
        buttonsLayout = (LinearLayout) findViewById(R.id.buttonsLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        animTranslate = AnimationUtils.loadAnimation(this, R.anim.translate);
        animTranslate.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                if (!sessionManager.isLoggedIn()) {
                    Animation animFadeIn = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fade_in);
                    Animation animFadeOut = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fade_out);
                    app_logo_name.setVisibility(View.GONE);
                    app_logo_name.startAnimation(animFadeOut);
                    LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
                    View inflatedLayout = inflater.inflate(R.layout.login_buttons_layout, null, false);
                    inflatedLayout.findViewById(R.id.fbLoginBtn).setOnClickListener(LoginActivity.this);
                    inflatedLayout.findViewById(R.id.googlePlusBtn).setOnClickListener(LoginActivity.this);
                    inflatedLayout.findViewById(R.id.skipBtn).setOnClickListener(LoginActivity.this);
                    buttonsLayout.addView(inflatedLayout);
                    inflatedLayout.startAnimation(animFadeIn);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    }, 2000);
                }
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                imgLogo.startAnimation(animTranslate);
            }
        }, SPLASH_TIME_OUT);
        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

        Session session = Session.getActiveSession();
        if (session == null) {
            if (savedInstanceState != null) {
                session = Session.restoreSession(this, null,
                        statusCallback, savedInstanceState);
            }
            if (session == null) {
                session = new Session(this);
            }
            Session.setActiveSession(session);
        }
    }

    public class FBMeAsyncTask extends AsyncTask<Void, Void, String> {
        private String token;

        public FBMeAsyncTask(String token) {
            this.token = token;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            return Methods.fbReg(token);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("Facebook", "Login Reponse :::: " + result);
            user = new User();
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("id"))
                        user.setFbUserId(jsonObject.getString("id"));
                    if (jsonObject.has("email"))
                        user.setEmail(jsonObject.getString("email"));
                    if (jsonObject.has("first_name"))
                        user.setFirstName(jsonObject.getString("first_name"));
                    if (jsonObject.has("last_name"))
                        user.setLastName(jsonObject.getString("last_name"));
                    if (jsonObject.has("name"))
                        user.setName(jsonObject.getString("name"));
                    if (jsonObject.has("picture")) {
                        String pictureData = jsonObject.getString("picture");
                        JSONObject jsonObjectImage = new JSONObject(pictureData);
                        {
                            if (jsonObjectImage.has("data")) {
                                String imageData = jsonObjectImage
                                        .getString("data");
                                JSONObject jsonObjectImg = new JSONObject(
                                        imageData);
                                if (jsonObjectImg.has("url"))
                                    user.setRemoteImage(jsonObjectImg
                                            .getString("url"));
                            }
                        }
                    }
                    checkCustomerExistence(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if ((pd != null) && (pd.isShowing()))
                pd.dismiss();
        }
    }

    public void checkCustomerExistence(User user) {
        if (Methods.checkInternetConnection(this)) {
//            new CheckCustomerExistenceApiAsyncTask(getActivity(), user, this)
//                    .execute();
            performLogin();
        } else
            Methods.noInternetDialog(this);
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            new FBMeAsyncTask(session.getAccessToken()).execute();
        } else if (state.isClosed()) {
        }
    }

    public void performLogin() {
        if (user != null) {
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.createLoginSession(user);
            Toast.makeText(this, "Welcome " + user.getName() + " !!!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Session.getActiveSession().addCallback(statusCallback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }
        Session.getActiveSession().removeCallback(statusCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != Activity.RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;
            if (mGoogleApiClient != null) {
                if (!mGoogleApiClient.isConnecting()) {
                    mGoogleApiClient.connect();
                }
            }
        }
        Session.getActiveSession().onActivityResult(this, requestCode,
                resultCode, data);
    }

    /* A helper method to resolve the current ConnectionResult error. */
    private void resolveSignInError() {
        if (mConnectionResult != null && mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
//                getActivity().startIntentSenderForResult(mConnectionResult.getResolution().getIntentSender(),
//                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress) {
            // Store the ConnectionResult so that we can use it later when the user clicks
            // 'sign-in'.
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
    }

    public void onConnectionSuspended(int cause) {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
//            updateUI(false);
        }
    }

    public void onConnected(Bundle connectionHint) {
        // We've resolved any connection errors.  mGoogleApiClient can be used to
        // access Google APIs on behalf of the user.
        getProfileInformation();
//        updateUI(true);
        performLogin();
    }

    private void getProfileInformation() {
        try {
            if (mGoogleApiClient != null) {
                if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                    Person currentPerson = Plus.PeopleApi
                            .getCurrentPerson(mGoogleApiClient);
                    String personName = currentPerson.getDisplayName();
                    String personPhotoUrl = currentPerson.getImage().getUrl();
                    String personGooglePlusProfile = currentPerson.getUrl();
                    String email = Plus.AccountApi.getAccountName(mGoogleApiClient);


                    // by default the profile url gives 50x50 px image only
                    // we can replace the value with whatever dimension we want by
                    // replacing sz=X
                    personPhotoUrl = personPhotoUrl.substring(0,
                            personPhotoUrl.length() - 2)
                            + PROFILE_PIC_SIZE;
                    user = new User();
                    user.setName(currentPerson.getDisplayName());

                } else {
                    Toast.makeText(this,
                            "Person information is null", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(final Session session, SessionState state,
                         Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fbLoginBtn:
                if (Methods.checkInternetConnection(this)) {
                    Session session = Session.getActiveSession();
                    if (!session.isOpened() && !session.isClosed()) {
                        session.openForRead(new Session.OpenRequest(this)
                                .setPermissions(Arrays.asList("email"))
                                .setCallback(statusCallback));
                    } else {
                        Session.openActiveSession(this, true,
                                statusCallback);
                    }
                } else
                    Methods.noInternetDialog(this);
                break;
            case R.id.googlePlusBtn:
                if (mGoogleApiClient != null) {
                    if (!mGoogleApiClient.isConnecting()) {
                        mSignInClicked = true;
                        mGoogleApiClient.connect();
//                        resolveSignInError();
                    }
                }
//                Toast.makeText(getApplicationContext(),"Currently G+ is under development. Please use Facebook to Login.",Toast.LENGTH_LONG).show();
                break;
            case R.id.skipBtn:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

//    private void updateUI(boolean isSignedIn) {
//        isLoggedIn = isSignedIn;
//        String buttonText;
//        if (isSignedIn) {
//            buttonText = logout;
//            // Logged in with G+
//            performLogin();
//        } else {
//            buttonText = login;
//            // Logged out from G+
//        }
//
//        for (int i = 0; i < signInButton.getChildCount(); i++) {
//            View v = signInButton.getChildAt(i);
//
//            if (v instanceof TextView) {
//                TextView tv = (TextView) v;
//                tv.setText(buttonText);
//                return;
//            }
//        }
//    }
}
