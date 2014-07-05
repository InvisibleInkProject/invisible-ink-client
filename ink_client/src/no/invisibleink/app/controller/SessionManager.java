package no.invisibleink.app.controller;

import java.util.HashMap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * stores and retrieves information, regarding the log in & user, from the device's shared preferences
 *
 */
public class SessionManager {
	
	
	private SharedPreferences pref;
	private Editor editor;
	
	private static final String PREF_NAME = "invisible_ink";

	private static final String PREF_KEY_USERNAME = "username";
	private static final String PREF_KEY_ACCESS_TOKEN = "access_token";
	private static final String PREF_KEY_REFRESH_TOKEN = "refresh_token";
	private static final String PREF_KEY_CLIENT_ID = "client_id";
	private static final String PREF_KEY_CLIENT_SECRET = "client_secret";
	private static final String PREF_KEY_EXPIRE_IN = "expire_in";
	
	@SuppressLint("CommitPrefEdits")
	public SessionManager(Context context){
		pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		editor = pref.edit();
	}

	/**
	 * returns name and access token for the currently logged in user
	 * @return HashMap with username & access token
	 */
//	public HashMap<String, String> getUserDetails(){
//		HashMap<String, String> details = new HashMap<String, String>();
//		details.put(PREF_KEY_USERNAME, pref.getString(PREF_KEY_USERNAME, ""));
//		details.put(PREF_KEY_ACCESS_TOKEN, pref.getString(PREF_KEY_ACCESS_TOKEN, ""));
//		return details;
//		// return a user object instead ? 
//	}
		
	/**
	 * Get the client ID.
	 * 
	 * @return The OAuth client ID, that was saved on the device
	 */
	public String getClientID(){
		// check if there is a session id and throw and exception otherwise ? Oo
		return pref.getString(PREF_KEY_CLIENT_ID, ""); 
	}
	
	/**
	 * Get the client secret.
	 *
	 * @return The OAuth client secret, that was saved on the device 
	 */
	public String getClientSecret(){
		return pref.getString(PREF_KEY_CLIENT_SECRET, "");
	}
	
	/**
	 * Saves the oAuth client id and secret to the device's shared preferences.
	 * 
	 * @param clientID client_id
	 * @param clientsSecret client_secret
	 */
	public void storeRegisterData(String clientID, String clientsSecret){
		editor.putString(PREF_KEY_CLIENT_ID, clientID);
		editor.putString(PREF_KEY_CLIENT_SECRET, clientsSecret);
		editor.commit();
	}
	
	/**
	 * Saves all relevant login information to the device's shared preferences.
	 * 
	 * @param accessToken the new access token
	 * @param refreshToken the new refresh token
	 * @param expiresIn time until expiration 
	 * @param username username
	 */
	public void storeLoginData(String accessToken, String refreshToken, String expiresIn, String username) {
		editor.putString(PREF_KEY_USERNAME, username);	
		editor.putString(PREF_KEY_ACCESS_TOKEN, accessToken);
		editor.putString(PREF_KEY_REFRESH_TOKEN, refreshToken);
		editor.putString(PREF_KEY_EXPIRE_IN, expiresIn);
		editor.commit();
	}
	
	/**
	 * checks if an access token is saved to the device (i.e. someone has logged on to the device before without actively logging off)
	 * @return (not) logged in
	 */
	public boolean isLoggedIn(){
		return pref.contains(PREF_KEY_ACCESS_TOKEN);
	}


	/**
	 * clears the access and refresh tokens stored in the shared preferences
	 */
	public void logout() {
		//TODO: actually this should remove all tokens etc. in the SP ... check back with oAuth implementation
		editor.remove(PREF_KEY_ACCESS_TOKEN);
		editor.remove(PREF_KEY_REFRESH_TOKEN);
		editor.commit();
	}
	
	//TODO: check if session id is still valid if there is a connection to the internet
}
