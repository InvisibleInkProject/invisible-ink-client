package no.invisibleink.app.controller.server_comm;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import no.invisibleink.app.R;
import no.invisibleink.app.controller.SessionManager;
import no.invisibleink.app.view.user.LoginActivity;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Represents an asynchronous login task used to authenticate
 * the user.
 */
public class UserLoginTask extends AsyncTask<String, Void, Boolean> {
	
	private LoginActivity activity;
	
	private String sessionID;
	
	public UserLoginTask(LoginActivity activity){
		this.activity = activity;
	}
	
	@Override
	protected Boolean doInBackground(String... params){
		HttpClient client = new DefaultHttpClient();
		String uri = (String) params[0];
		
		// TODO: attempt authentication against a network service.
		activity.showProgress(true);
		//perform request while progress bar is showing
		
		//check response -> success = return true, else false (reminder for registration: switch(error) e.g. username/email already in use, ... 
		//throw exception instead of setting error info in onPostExecute ? (therefore remove LoginActivity)
		//SessionID ?! 
		sessionID = "123456";
		return true;
	}

	@Override
	protected void onPostExecute(final Boolean success){
		// ? mAuthTask = null;
		activity.showProgress(false);

		if (success) {
			SessionManager mg = new SessionManager(activity);
			mg.storeSessionDetails(activity.getmEmail(), activity.getmPassword(), this.sessionID);
			
			activity.loginSuccess();
		} else {
			activity.getmPasswordView().setError(activity.getString(R.string.error_incorrect_password));
			activity.getmPasswordView().requestFocus();
		}
	}
}