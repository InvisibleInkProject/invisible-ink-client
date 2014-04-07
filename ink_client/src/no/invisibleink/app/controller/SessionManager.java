package no.invisibleink.app.controller;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	
	
	private SharedPreferences pref;
	private Editor editor;
	private int MODE = 0; //=private
	private Context context;
	
	private static final String PREF_NAME = "invisible_ink";

	private static final String KEY_NAME = "name";
	private static final String KEY_MAIL = "mail";
	private static final String KEY_ACCESS = "access_token";
	private static final String KEY_REFRESH = "refresh_token";
	private static final String KEY_ID = "id";
	private static final String KEY_SECRET = "secret";
	
	public SessionManager(Context ctx){
		context = ctx;
		pref = context.getSharedPreferences(PREF_NAME, MODE);
		editor = pref.edit();
	}
	
	public void storeSessionDetails(String name, String email, String sessionId){
		editor.putString(KEY_NAME, name);
		editor.putString(KEY_MAIL, email);
		editor.putString(KEY_ACCESS, sessionId);
		editor.commit();
	}
	
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> details = new HashMap<String, String>();
		details.put(KEY_NAME, pref.getString(KEY_NAME, ""));
		details.put(KEY_MAIL, pref.getString(KEY_MAIL, ""));
		details.put(KEY_ACCESS, pref.getString(KEY_ACCESS, ""));
		return details;
		// return a user object instead ? 
	}
	
	public boolean isLoggedIn(){
		String id = pref.getString(KEY_ACCESS, "");
		if(id.isEmpty()) return false;
		return true;
	}
	
	public String getSessionId(){
		return pref.getString(KEY_ACCESS, ""); // check if there is a session id and throw and exception otherwise ? Oo 
	}
	
	public void register(String id, String secret){
		editor.putString(KEY_ID, id);
		editor.putString(KEY_SECRET, secret);
		editor.commit();
	}
	
	public void login(){
		
	}
	
	//TODO: check if session id is still valid if there is a connection to the internet
}
