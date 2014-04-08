package no.invisibleink.app.view.user;

import java.util.concurrent.ExecutionException;

import no.invisibleink.app.MainActivity;
import no.invisibleink.app.R;
import no.invisibleink.app.controller.SessionManager;
import no.invisibleink.app.controller.Settings;
import no.invisibleink.app.controller.server_comm.UserLoginTask;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user
 */
public class LoginActivity extends Activity {
	

	// Values for username and password at the time of the login attempt.
	private String mUsername;
	private String mPassword;

	// UI references.
	private EditText mNameView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//check if user is already logged in, if so directly continue to MainActivity
		if(new SessionManager(this).isLoggedIn()){
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish(); //call finish here to make sure the back button closes the app instead of redirecting to the login screen
		}
		
		setContentView(R.layout.activity_login);

		// Set up the login form.
		mNameView = (EditText) findViewById(R.id.name);
		mPasswordView = (EditText) findViewById(R.id.password);
		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
		findViewById(R.id.link_to_register).setOnClickListener(
			new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					register();					
				}
			}
		);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
		//TODO: take care of lost password! 
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {

		// Reset errors.
		mNameView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mUsername = mNameView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid username
		if (TextUtils.isEmpty(mUsername)) {
			mNameView.setError(getString(R.string.error_field_required));
			focusView = mNameView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			
			SessionManager sm = new SessionManager(getApplicationContext());
			
			UserLoginTask login = new UserLoginTask(sm);
			login.execute(Settings.OAUTH_URL, mUsername, mPassword);
			
			try {
				if(login.get()){ //task completed successfully
					showProgress(false);
					Intent intent = new Intent(this, MainActivity.class);
					startActivity(intent);
					finish();
				}else{
					showProgress(false);
					//TODO: show error
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * start the register activity
	 */
	public void register(){
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
		finish(); // -> do not return here on back-button press 
	}
	
	/**
	 * Shows the progress UI and hides the login form.
	 * (auto-generated)
	 */
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

}
