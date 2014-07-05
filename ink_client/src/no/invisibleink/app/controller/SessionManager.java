package no.invisibleink.app.controller;

import java.util.HashMap;

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
	private int MODE = 0; //=private
	private Context context;
	
	private static final String PREF_NAME = "invisible_ink";

	private static final String KEY_NAME = "name";
	private static final String KEY_ACCESS = "access_token";
	private static final String KEY_REFRESH = "refresh_token";
	private static final String KEY_ID = "id";
	private static final String KEY_SECRET = "secret";
	private static final String KEY_EXP = "expiration";
	
	public SessionManager(Context ctx){
		context = ctx;
		pref = context.getSharedPreferences(PREF_NAME, MODE);
		editor = pref.edit();
	}

	/**
	 * returns name and access token for the currently logged in user
	 * @return HashMap with username & access token
	 */
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> details = new HashMap<String, String>();
		details.put(KEY_NAME, pref.getString(KEY_NAME, ""));
		details.put(KEY_ACCESS, pref.getString(KEY_ACCESS, ""));
		return details;
		// return a user object instead ? 
	}
		
	/**
	 * returns the oAuth client id that was saved on the device
	 * @return
	 */
	public String getClientId(){
		String id = pref.getString(KEY_ID, "");
		return id; // check if there is a session id and throw and exception otherwise ? Oo 
	}
	
	/**
	 * returns the oAuth client secret that was saved on the device 
	 * @return
	 */
	public String getClientSecret(){
		String secret =  pref.getString(KEY_SECRET, "");
		return secret;
	}
	
	/**
	 * saves the oAuth client id and secret to the device's shared preferences
	 * @param client_id client_id
	 * @param client_secret client_secret
	 */
	public void register(String client_id, String client_secret){
		editor.putString(KEY_ID, client_id);
		editor.putString(KEY_SECRET, client_secret);
		editor.commit();
	}
	
	/**
	 * saves all relevant login information to the device's shared preferences
	 * @param accessToken the new access token
	 * @param refreshToken the new refresh token
	 * @param expiresIn time until expiration 
	 * @param username username
	 */
	public void login(String accessToken, String refreshToken, String expiresIn, String username) {
		editor.putString(KEY_NAME, username);	
		editor.putString(KEY_ACCESS, accessToken);
		editor.putString(KEY_REFRESH, refreshToken);
		editor.putString(KEY_EXP, expiresIn);
		editor.commit();
	}
	
	/**
	 * checks if an access token is saved to the device (i.e. someone has logged on to the device before without actively logging off)
	 * @return (not) logged in
	 */
	public boolean isLoggedIn(){
		return pref.contains(KEY_ACCESS);
	}


	/**
	 * clears the access and refresh tokens stored in the shared preferences
	 */
	public void logout() {
		//TODO: actually this should remove all tokens etc. in the SP ... check back with oAuth implementation
		editor.remove(KEY_ACCESS);
		editor.remove(KEY_REFRESH);
		editor.commit();
	}
	
	//TODO: check if session id is still valid if there is a connection to the internet
}
