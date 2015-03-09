package com.t9l.millionkitchen.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.facebook.Session;
import com.t9l.millionkitchen.dao.User;
import com.t9l.millionkitchen.sessions.SessionManager;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Methods {
    public static AlertDialog d;

    public static final String PROPERTY_APP_VERSION = "appVersion";

    public static final String PREF_NAME = "MillionKitchenPrefSession";

    static final String EXTRA_MESSAGE = "message";


    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                .matches();
    }


    public static HttpParams setTimeout(int timeoutConnection, int timeoutSocket) {
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters,
                timeoutConnection);
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        return httpParameters;
    }

    // check for internet connectivity
    public static boolean checkInternetConnection(Context ctx) {
        ConnectivityManager mManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mManager.getActiveNetworkInfo();
        if ((mNetworkInfo != null) && (mNetworkInfo.isConnected())) {
            return true;
        }
        return false;
    }

    // display a message dialog with custom message
    public static void openUtilityDialog(final Context ctx, final String message) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
        dialog.setMessage(message);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // dialog.show();
        d = dialog.create();
        d.show();
    }

    // display a no Internet toast if no connectivity is available
    public static void noInternetDialog(Context ctx) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
        dialog.setTitle("No Connection");
        dialog.setMessage("It seems that you are offline. Please check your internet connection and try again.");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // dialog.show();
        d = dialog.create();
        d.show();
    }// noInternetDialog

    public static void doLogin(Context ctx, User user) {
        String email = user.getEmail();
        if (Methods.valid(email)) {
            // new GetCustomerDetailsApiAsyncTask(context, customer).execute();
            SessionManager session = new SessionManager(ctx);
            session.createLoginSession(user);
        } else {
            Methods.openUtilityDialog(ctx, "Please enter username and password");
        }

    }

    public static void doLogout(Context context) {
        SessionManager session = new SessionManager(context);
        session.logoutUser();
        Session facebookSession = Session.getActiveSession();
        if (facebookSession != null) {
            facebookSession.closeAndClearTokenInformation();
        }
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    // public static void setHeading(Activity activity, Boolean back,
    // Boolean title, Boolean signup, String titleString) {
    // TextView titleTV = (TextView) activity.findViewById(R.id.heading);
    // Button backBtn = (Button) activity.findViewById(R.id.back);
    // Button signupBtn = (Button) activity.findViewById(R.id.signupBtn);
    // if (title) {
    // titleTV.setText(titleString);
    // }
    // if (back)
    // backBtn.setVisibility(View.VISIBLE);
    // else
    // backBtn.setVisibility(View.GONE);
    // if (signup)
    // signupBtn.setVisibility(View.VISIBLE);
    // else
    // signupBtn.setVisibility(View.GONE);
    // }

    public static String getBase64String(String pathToImage) {
        if (valid(pathToImage)) {
            Bitmap bm = BitmapFactory.decodeFile(pathToImage);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the
            // bitmap
            // object
            byte[] b = baos.toByteArray();
            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
            return encodedImage;
        }
        return null;
    }

    public static boolean valid(String text) {
        // TODO Auto-generated method stub
        if (text == null || text.trim().equals(""))
            return false;
        else
            return true;
    }

    public static String fbReg(String token) {

        String link = Vars.FB_ME_URL + token;
        HttpClient hc = new DefaultHttpClient();
        ResponseHandler<String> res = new BasicResponseHandler();
        HttpGet httpGet = new HttpGet(link);

        String response = null;
        String ms = "";

        try {
            response = hc.execute(httpGet, res);
            ms = response.toString().trim();
        } catch (ClientProtocolException e) {
            ms = e.getMessage();
        } catch (IOException e) {

        }

        return ms;

    }

    public static Bitmap doParse(String response, int w, int h) {
        // byte[] data = response.data;
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        Bitmap bitmap = null;

        // If we have to resize this image, first get the natural bounds.
        decodeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(response, decodeOptions);
        // BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
        int actualWidth = decodeOptions.outWidth;
        int actualHeight = decodeOptions.outHeight;

        // Then compute the dimensions we would ideally like to decode to.
        int desiredWidth = getResizedDimension(w, h, actualWidth, actualHeight);
        int desiredHeight = getResizedDimension(w, h, actualHeight, actualWidth);

        // Decode to the nearest power of two scaling factor.
        decodeOptions.inJustDecodeBounds = false;
        // TODO(ficus): Do we need this or is it okay since API 8 doesn't
        // support it?
        // decodeOptions.inPreferQualityOverSpeed = PREFER_QUALITY_OVER_SPEED;
        decodeOptions.inSampleSize = findBestSampleSize(actualWidth,
                actualHeight, desiredWidth, desiredHeight);
        Bitmap tempBitmap = BitmapFactory.decodeFile(response, decodeOptions);

        // If necessary, scale down to the maximal acceptable size.
        if (tempBitmap != null
                && (tempBitmap.getWidth() > desiredWidth || tempBitmap
                .getHeight() > desiredHeight)) {
            bitmap = Bitmap.createScaledBitmap(tempBitmap, desiredWidth,
                    desiredHeight, true);
            tempBitmap.recycle();
        } else {
            bitmap = tempBitmap;
        }

        return bitmap;
    }

    public static int getResizedDimension(int maxPrimary, int maxSecondary,
                                          int actualPrimary, int actualSecondary) {
        // If no dominant value at all, just return the actual.
        if (maxPrimary == 0 && maxSecondary == 0) {
            return actualPrimary;
        }

        // If primary is unspecified, scale primary to match secondary's scaling
        // ratio.
        if (maxPrimary == 0) {
            double ratio = (double) maxSecondary / (double) actualSecondary;
            return (int) (actualPrimary * ratio);
        }

        if (maxSecondary == 0) {
            return maxPrimary;
        }

        double ratio = (double) actualSecondary / (double) actualPrimary;
        int resized = maxPrimary;
        if (resized * ratio > maxSecondary) {
            resized = (int) (maxSecondary / ratio);
        }
        return resized;
    }

    public static int findBestSampleSize(int actualWidth, int actualHeight,
                                         int desiredWidth, int desiredHeight) {
        double wr = (double) actualWidth / desiredWidth;
        double hr = (double) actualHeight / desiredHeight;
        double ratio = Math.min(wr, hr);
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }
        return (int) n;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view != null) {
            IBinder windowToken = view.getWindowToken();
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
        }
    }

    public static void setupUI(final Activity activity, View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }

            });
        }

        // If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(activity, innerView);
            }
        }
    }

    public static String getDateFromUnixTimeStamp(String unixTimeStamp,
                                                  String dateFormat) {
        if (!unixTimeStamp.isEmpty()) {
            Date date = new Date(Long.parseLong(unixTimeStamp) * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            String formattedDate = sdf.format(date);
            return formattedDate;
        } else
            return "";
    }


    public static void openCallIntent(Context context,
                                      String propertyOwnerPhoneString) {
        try {
            String uri = "tel:" + propertyOwnerPhoneString.trim();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(uri));
            context.startActivity(intent);
        } catch (ParseException e) {
            openUtilityDialog(context, "Unable to get contact number");
        }
    }

    public static void openEmailIntent(Context context, String toEmail,
                                       String subject, String message) {
        final Intent emailIntent = new Intent(
                Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                new String[]{toEmail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(emailIntent,
                "Send e-mail..."));
    }


    public static void setMenuItems(Menu menu, boolean notification, boolean search, boolean edit) {
        MenuItem notificationMenu = menu.findItem(Vars.MENU_ITEM_ID_NOTIFICATIONS);
        if (notificationMenu != null)
            notificationMenu.setVisible(notification).setEnabled(notification);
        MenuItem searchMenu = menu.findItem(Vars.MENU_ITEM_ID_SEARCH);
        if (searchMenu != null)
            searchMenu.setVisible(search).setEnabled(search);
        MenuItem editMenu = menu.findItem(Vars.MENU_ITEM_ID_EDIT);
        if (editMenu != null)
            editMenu.setVisible(edit).setEnabled(edit);
    }
}
