package no.invisibleink.test_api;

import java.util.Calendar;

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
	
	InkClientUsage inkClientUsage = new InkClientUsage("77db8444e1dc145575f0b168dc2aad715fc1e0c0"); 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
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
       	inkClientUsage.getInk(UserStub.getLocation(), new Ink.GetHandler() {
			
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
		inkClientUsage.postInk(InkStub.message, InkStub.radius, InkStub.expires, UserStub.getLocation(), new Ink.PostHandler() {

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
       	inkClientUsage.registration(UserStub.username, UserStub.password, UserStub.email, UserStub.birthday, UserStub.gender, UserStub.nationality, new Registration.PostHandler() {
			
			@Override
			public void onSuccess(String client_id, String client_secret) {
				Log.i(TAG, "registration success");
				Log.i(TAG, client_id + ", " + client_secret);
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
		inkClientUsage.login(UserStub.username, UserStub.password, UserStub.client_id, UserStub.client_secret, new Login.PostHandler() {
			
			@Override
			public void onSuccess(String accessToken, String refreshToken) {
				Log.i(TAG, "login success: accessToken, refreshToken");
				Log.i(TAG, accessToken + ", " + refreshToken);
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
		inkClientUsage.loginRefresh(UserStub.client_id, UserStub.client_secret, UserStub.refresh_token, new Login.PostHandler() {
			@Override
			public void onSuccess(String accessToken, String refreshToken) {
				Log.i(TAG, "loginRefresh success: accessToken, refreshToken");
				Log.i(TAG, accessToken + ", " + refreshToken);
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
