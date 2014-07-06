package no.invisibleink.test_api.stub;

import no.invisibleink.api.model.Login;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;

public class UserStub {

	public static String username = "sample";
	public static String password = "sample";
	public static String email = "myMail@myDomain.com";
	public static String birthday = "2007-11-2";
	public static String gender = "Female";
	public static String nationality = "VNM";

	private SharedPreferences prefs;
	private Editor editor;	
	
	public UserStub(Activity activity) {
		prefs = activity.getPreferences(Context.MODE_PRIVATE);
		editor = prefs.edit();
	}
	
	public static Location getLocation() {
       	Location loc = new Location("");
       	loc.setLatitude(59.94);
       	loc.setLongitude(10.72);
       	return loc;
	}
	
	public String getClientID() {
		return prefs.getString(Login.HEADER_CLIENT_ID, "");
	}
	
	public String getClientSecret() {
		return prefs.getString(Login.HEADER_CLIENT_SECRET, "");		
	}

	public String getAccessToken() {
		return prefs.getString(Login.RESPONSE_ACCESS_TOKEN, "");		
	}

	public String getRefreshToken() {
		return prefs.getString(Login.RESPONSE_REFRESH_TOKEN, "");		
	}
	
	public void storeValues(String clientID, String clientSecret, String accessToken, String refreshToken) {		
		if (clientID != null) editor.putString(Login.HEADER_CLIENT_ID, clientID);
		if (clientSecret != null) editor.putString(Login.HEADER_CLIENT_SECRET, clientSecret);
		if (accessToken != null) editor.putString(Login.RESPONSE_ACCESS_TOKEN, accessToken);
		if (refreshToken != null) editor.putString(Login.RESPONSE_REFRESH_TOKEN, refreshToken);
		editor.commit();
	}	

}
