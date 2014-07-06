package no.invisibleink.test_api;

import no.invisibleink.api.client.InkClientUsage;
import no.invisibleink.api.model.Ink;
import no.invisibleink.api.model.Login;
import no.invisibleink.api.model.Registration;
import no.invisibleink.test_api.stub.InkStub;
import no.invisibleink.test_api.stub.UserStub;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	
	public static final String TAG = "Test";
	private UserStub userStub;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		/*
		 * Before test
		 */
		userStub = new UserStub(this);
		
		/*
		 * Test
		 */
		testGetInk();
		//testPostInk();
		//testRegistration();
		//testLogin();
		//testLoginRefresh();
	}
	
	private void testGetInk() {
		InkClientUsage.setAuthorizationToken(userStub.getAccessToken());
		InkClientUsage.getInk(UserStub.getLocation(), new Ink.GetHandler() {
			
			@Override
			public void onSucess(JSONArray inks) {
				Log.i(TAG, "getInk success");
/*				JSONObject o;
				
				if (inks.length() < 0) {
					Log.i(TAG, "Empty inks");
					return;
				}
				
				try {
					o = inks.getJSONObject(0);
					Log.w(TAG, o.getString(Ink.TEXT));
					
					
					String filename = "myfile";
					String string = "Hello world!";
					
					File file = new File(getFilesDir(), filename);
					FileOutputStream outputStream = openFileOutput(filename, MODE_PRIVATE);
					outputStream.write(string.getBytes());
					outputStream.close();
					
					Log.w(TAG, "" + file.getAbsoluteFile());
					Log.w(TAG, "" + file.getAbsolutePath());
					
//					fileList()
					
				} catch (JSONException e) {
					Log.w(TAG, e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}*/
			}
			
			@Override
			public void onFailure(int statusCode) {
				Log.e(TAG, "getInk failure: " + statusCode);
			}

			@Override
			public void onFailureUnauthorized() {
				Log.w(TAG, "getInk failure: " + "unauthorized");				
			}
		});
	}
	
	private void testPostInk() {
		InkClientUsage.setAuthorizationToken(userStub.getAccessToken());		
		InkClientUsage.postInk(InkStub.message, InkStub.radius, InkStub.expires, UserStub.getLocation(), new Ink.PostHandler() {

			@Override
			public void onSucess() {
				Log.i(TAG, "postInk success");
			}

			@Override
			public void onFailure(int statusCode) {
				Log.e(TAG, "postInk failed: " + statusCode);
			}

			@Override
			public void onFailureUnauthorized() {
				Log.w(TAG, "getInk failure: " + "unauthorized");
			}     		
		});		
	}

	private void testRegistration() {
		InkClientUsage.registration(UserStub.username, UserStub.password, UserStub.email, UserStub.birthday, UserStub.gender, UserStub.nationality, new Registration.PostHandler() {
			
			@Override
			public void onSuccess(String clientID, String clientSecret) {
				Log.i(TAG, "registration success");
				Log.i(TAG, clientID + ", " + clientSecret);
				userStub.storeValues(clientID, clientSecret, null, null);
			}
			
			@Override
			public void onFailure(int statusCode) {
				Log.e(TAG, "registration failure:" + statusCode);
			}
			
			@Override
			public void onFailureUserAlreadyExits() {
				Log.w(TAG, "registration failure:" + "onFailureUserAlreadyExits");				
			}
		});		
	}
	
	private void testLogin() {
		InkClientUsage.login(UserStub.username, UserStub.password, userStub.getClientID(), userStub.getClientSecret(), new Login.PostHandler() {
			
			@Override
			public void onSuccess(String accessToken, String refreshToken, String expires_in) {
				Log.i(TAG, "login success: accessToken, refreshToken, expires_in");
				Log.i(TAG, accessToken + ", " + refreshToken + ", " + expires_in);
				userStub.storeValues(null, null, accessToken, refreshToken);
			}
			
			@Override
			public void onFailure(int statusCode) {
				Log.e(TAG, "login failure:" + statusCode);
			}
			
			@Override
			public void onFailureInvalid() {
				Log.w(TAG, "login failure:" + "invalid");				
			}
		});
	}
	
	private void testLoginRefresh() {
		InkClientUsage.loginRefresh(userStub.getClientID(), userStub.getClientSecret(), userStub.getRefreshToken(), new Login.PostHandler() {
			@Override
			public void onSuccess(String accessToken, String refreshToken, String expires_in) {
				Log.i(TAG, "loginRefresh success: accessToken, refreshToken, expires_in");
				Log.i(TAG, accessToken + ", " + refreshToken + ", " + expires_in);
				userStub.storeValues(null, null, accessToken, refreshToken);
			}
			
			@Override
			public void onFailure(int statusCode) {
				Log.e(TAG, "loginRefresh failure:" + statusCode);
			}
			
			@Override
			public void onFailureInvalid() {
				Log.w(TAG, "loginRefresh failure:" + "invalid");				
			}
		});
	}
	
}
