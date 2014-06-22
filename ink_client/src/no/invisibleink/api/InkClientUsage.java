package no.invisibleink.api;

import java.text.SimpleDateFormat;
import java.util.Date;

import no.invisibleink.api.model.Ink;
import no.invisibleink.api.model.Login;
import no.invisibleink.api.model.Registration;
import no.invisibleink.api.model.User;
import no.invisibleink.app.controller.SessionManager;

import org.apache.http.client.ResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class InkClientUsage {
	
	public static final String TAG = InkClientUsage.class.getName();
	
	public static void getInk(final Location location, final Ink.GetHandler getHandler) {
		final RequestParams rq = new RequestParams(InkClient.PARAMETER_NO_META, true);
		InkClient.get(Ink.ENDPOINT + location.getLatitude() + "," + location.getLongitude() + ",2000.0/", rq, new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray inks) {
				getHandler.onSucess(inks);
			}

			@Override
			public void onFailure(int statusCode, Throwable e, JSONObject errorResponse) {
				Log.e(TAG, statusCode + ", " + e.getMessage() + ", " + errorResponse.toString());
				getHandler.onFailure(statusCode);
			}
			
		});
	}
	
	public static void postInk(final String message, final int radius, final Date expires, final Location location, final Ink.PostHandler postHandler) {
		RequestParams rp = new RequestParams();
		rp.put(Ink.TEXT, message);
		rp.put(Ink.RADIUS, radius);
		rp.put(Ink.USER_ID, 1);
		rp.put(Ink.LOCATION_LON, location.getLongitude());
		rp.put(Ink.LOCATION_LAT, location.getLatitude());
		Log.w("lllll", rp.toString());
		SimpleDateFormat sdf = new SimpleDateFormat(InkClient.DATE_FORMAT);
		rp.put(Ink.EXIRES, sdf.format(expires));
		InkClient.post(Ink.ENDPOINT, null, new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, JSONArray respone) {
//				HttpURLConnection.HTTP_CREATED
				Log.w(TAG, "receive: " + statusCode + "," + respone.toString());
				postHandler.onSucess();
			}

			@Override
			public void onFailure(int statusCode, Throwable e, JSONObject errorResponse) {
				Log.e(TAG, statusCode + ", " + e.getMessage() + ", " + errorResponse.toString());
				postHandler.onFailure(statusCode);
			}		
		
		});
	}
	
	/**
	 * turns the given Strings into a json formatted string that can be send to the server 
	 * @param username username
	 * @param password password
	 * @param email email address
	 * @param birthday birthday
	 * @param gender gender
	 * @param nationality nationality
	 * @return json-String
	 */
	public void registration(final String username, final String password, final String email, final String birthday, final String gender, final String nationality, final Registration.PostHandler postHandler){
		RequestParams rp = new RequestParams();
		rp.put(User.USERNAME, username);
		rp.put(User.PASSWORD, password);
		rp.put(User.EMAIL, email);
		rp.put(User.BIRTHDAY, birthday);
		rp.put(User.GENDER, gender);
		rp.put(User.NATIONAITY, nationality);
		
		InkClient.post(Registration.ENDPOINT, rp, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				// TODO: required to check?
				// statusCode == 201
				try {
					postHandler.onSuccess(response.getString(Registration.RESPONSE_CLIENT_ID), response.getString(Registration.RESPONSE_CLIENT_SECRET));
					// TODO:
					//SessionManager.register(ID, SECRET);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
			// TODO: on fail (400 or 500)?
			//TODO: handle failure codes here and in the UI! 			
			
		});
	}
	
	public void userLogin(final String username, final String password, final String client_id, final String client_secret, final Login.PostHandler postHandler) {
		
		RequestParams rp = new RequestParams();
		rp.put(Login.GRANT_TYPE, Login.GRANT_TYPE_PASSWORD);
		rp.put(Login.USERNAME, username);
		rp.put(Login.PASSWORD, password);
		rp.put(Login.CLIENT_ID, client_id);
		rp.put(Login.CLIENT_SECRET, client_secret);
		rp.put(Login.SCOPE, Login.SCOPE_READ);
		AuthClient.post(Login.ENDPOINT, rp, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				// TODO: required to check?
				// statusCode == 200
				try {
					postHandler.onSuccess(response.getString(Login.RESPONSE_ACCESS_TOKEN), response.getString(Login.RESPONSE_REFRESH_TOKEN));
					// TODO:
					//SessionManager.login(ACCESS_TOKEN, REFRESH_TOKEN);

				} catch (JSONException e) {
					e.printStackTrace();
				}				
			}

			// TODO: on fail (400 or 500)?
			
		});
	}
}
