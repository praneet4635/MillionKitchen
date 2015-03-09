package com.t9l.millionkitchen.tools;

import com.t9l.millionkitchen.R;

public class Vars {
	
	public static final String FB_ME_URL = "https://graph.facebook.com/me?fields=id,first_name,middle_name,last_name,email,location,name,birthday,picture.width(500).height(500)&access_token=";

	public static final String BASE_URL="http://pbolist.ca/api/apicontrol.php";
	
	public static final String RESPONSE_OK="OK";     	// request successful.
	public static final String RESPONSE_NA="NA";     	// account not active. 
	public static final String RESPONSE_UN="UN";		// user does not exist.
	public static final String RESPONSE_FAIL="FAIL";	// some error occurred.
	public static final String RESPONSE_ERROR="error";	// some error occurred.
	public static final String RESPONSE_UE="UE";  		// user already exists.
	public static final String RESPONSE_WC="WC";		// wrong code.

	public static final String DEVICE_TYPE="A";
	public static final String DEFAULT_PASSWORD="@#123456789";
	
//	public static final String DEVICE_ID = "deviceid";
	
	public static final int PICK_FROM_CAMERA = 1001, LOAD_FROM_GALLERY = 1002;
	public static final String DEFAULT_IMAGE_PATH="tmp_avatar_"+ String.valueOf(System.currentTimeMillis())+ ".jpg";
	public static final String ASSET_DIRECTORY = "file:///android_asset/";

    public static final int MENU_ITEM_ID_NOTIFICATIONS=R.id.action_notification;
    public static final int MENU_ITEM_ID_SEARCH=R.id.action_search;
    public static final int MENU_ITEM_ID_EDIT=R.id.action_edit;
}
