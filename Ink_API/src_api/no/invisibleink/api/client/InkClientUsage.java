package no.invisibleink.api.client;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import no.invisibleink.api.model.Ink;
import no.invisibleink.api.model.Login;
import no.invisibleink.api.model.Registration;
import no.invisibleink.api.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class InkClientUsage {
	
	public static final String TAG = InkClientUsage.class.getSimpleName();
	
	public static final int STATUS_CODE_CONVERTING_ERROR = -1;
	
	public static void getInk(final Location location, final Ink.GetHandler getHandler) {
		final RequestParams rq = new RequestParams(InkClient.PARAMETER_NO_META, true);
		InkClient.get(Ink.ENDPOINT + location.getLatitude() + "," + location.getLongitude() + ",2000.0/", rq, new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray inks) {
				getHandler.onSucess(inks);
			}

			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, String responseString, Throwable e) {
				if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
					getHandler.onFailureUnauthorized();
					return;
				}
				//Log.e(TAG, statusCode + ", " + e.getMessage() + ", " + errorResponse.toString());
				getHandler.onFailure(statusCode);
			}
			
		});
	}
	
	public static void postInk(final String message, final int radius, final Date expires, final Location location, final Ink.PostHandler postHandler) {
		try {
			JSONObject jo = new JSONObject();
			jo.put(Ink.TEXT, message);
			jo.put(Ink.RADIUS, radius);
			jo.put(Ink.LOCATION_LON, location.getLongitude());
			jo.put(Ink.LOCATION_LAT, location.getLatitude());
			SimpleDateFormat sdf = new SimpleDateFormat(InkClient.DATE_FORMAT);
			jo.put(Ink.EXPIRES, sdf.format(expires));
			InkClient.postJson(null, Ink.ENDPOINT, jo, new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
					postHandler.onSucess();
				}

				@Override
				public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
					if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
						postHandler.onFailureUnauthorized();
						return;
					}
					Log.w(TAG, statusCode + ", " + error.getMessage() + ", " + responseBody.toString());
					postHandler.onFailure(statusCode);
				}		
			
			});
			
		} catch (Exception e) {
			Log.e(TAG, "postInk:" + e.getMessage());
			postHandler.onFailure(STATUS_CODE_CONVERTING_ERROR);
		}
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
	public static void registration(final String username, final String password, final String email, final String birthday, final String gender, final String nationality, final Registration.PostHandler postHandler){
		try {
			JSONObject jo = new JSONObject();
			jo.put(User.USERNAME, username);
			jo.put(User.PASSWORD, password);
			jo.put(User.EMAIL, email);
			jo.put(User.BIRTHDAY, birthday);
//			jo.put(User.GENDER, gender);
//			jo.put(User.NATIONAITY, nationality);
			InkClient.postJson(null, Registration.ENDPOINT, jo, new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, JSONObject response) {
					try {
						postHandler.onSuccess(response.getString(Registration.RESPONSE_CLIENT_ID), response.getString(Registration.RESPONSE_CLIENT_SECRET));
						// TODO:
						//SessionManager.register(ID, SECRET);
					} catch (JSONException e) {
						Log.w(TAG, "postJson: " + e.getMessage());
						postHandler.onFailure(STATUS_CODE_CONVERTING_ERROR);
					}
				}
				
				@Override
				public void onFailure(int statusCode, org.apache.http.Header[] headers, String responseString, Throwable e) {
					//Log.e(TAG, statusCode + ", " + e.getMessage() + ", " + responseString);
					if (statusCode == HttpURLConnection.HTTP_BAD_REQUEST) {
						// Message: (1062, "Duplicate entry 'USERNAME' for key 'username'")
						if (responseString.contains("1062")) {
							postHandler.onFailureUserAlreadyExits();
							return;
						}
					}
					postHandler.onFailure(statusCode);
				}				
				
			});
		} catch (Exception e) {
			e.printStackTrace();
			postHandler.onFailure(-1);
		}
	}

	public static void login(final String username, final String password, final String clientID, final String clientSecret, final Login.PostHandler postHandler) {
		RequestParams rp = new RequestParams();
		rp.put(Login.HEADER_GRANT_TYPE, Login.GRANT_TYPE_PASSWORD);
		rp.put(Login.HEADER_USERNAME, username);
		rp.put(Login.HEADER_PASSWORD, password);
		rp.put(Login.HEADER_CLIENT_ID, clientID);
		rp.put(Login.HEADER_CLIENT_SECRET, clientSecret);
		rp.put(Login.HEADER_SCOPE, Login.SCOPE_READ);
		AuthClient.post(Login.ENDPOINT, rp, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				try {
					postHandler.onSuccess(response.getString(Login.RESPONSE_ACCESS_TOKEN), response.getString(Login.RESPONSE_REFRESH_TOKEN), response.getString(Login.RESPONSE_EXPIRES_IN));
					// TODO:
					//SessionManager.login(ACCESS_TOKEN, REFRESH_TOKEN);

				} catch (JSONException e) {
					Log.w(TAG, "login:" + e.getMessage());
					postHandler.onFailure(-1);
				}				
			}
			
			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, String responseString, Throwable e) {
				//Log.e(TAG, statusCode + ", " + e.getMessage() + ", " + responseString);
				if (statusCode == HttpURLConnection.HTTP_BAD_REQUEST) {
					postHandler.onFailureInvalid();
					return;
				}
				postHandler.onFailure(statusCode);
			}

		});
	}
	
	public static void loginRefresh(final String client_id, final String clientSecret, final String refreshToken, final Login.PostHandler postHandler) {
		RequestParams rp = new RequestParams();
		rp.put(Login.HEADER_GRANT_TYPE, Login.GRANT_TYPE_REFRESH_TOKEN);
		rp.put(Login.HEADER_CLIENT_ID, client_id);
		rp.put(Login.HEADER_CLIENT_SECRET, clientSecret);
		rp.put(Login.HEADER_REFRESH_TOKEN, refreshToken);
		AuthClient.post(Login.ENDPOINT, rp, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				try {
					postHandler.onSuccess(response.getString(Login.RESPONSE_ACCESS_TOKEN), response.getString(Login.RESPONSE_REFRESH_TOKEN), response.getString(Login.RESPONSE_EXPIRES_IN));
					// TODO:
					//SessionManager.login(ACCESS_TOKEN, REFRESH_TOKEN);
				} catch (JSONException e) {
					Log.w(TAG, "loginRefresh:" + e.getMessage());
					postHandler.onFailure(-1);
				}				
			}
			
			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, String responseString, Throwable e) {
				//Log.e(TAG, statusCode + ", " + e.getMessage() + ", " + responseString);
				if (statusCode == HttpURLConnection.HTTP_BAD_REQUEST) {
					postHandler.onFailureInvalid();
					return;
				}
				postHandler.onFailure(statusCode);
			}

		});

	}
	
	public static void setAuthorizationToken(String accessToken) {
		InkClient.setAuthorizationToken(accessToken);
	}	
}
