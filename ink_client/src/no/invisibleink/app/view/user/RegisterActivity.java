package no.invisibleink.app.view.user;

import no.invisibleink.app.MainActivity;
import no.invisibleink.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends Activity {
	
	// Values for email and password at the time of the registration attempt.
	private String mEmail;
	private String mPassword;
	private String mFullName;
	
	// UI references.
	private EditText mEmailView;
	private EditText mNameView;
	private EditText mPasswordView;
	//private View mRegistrationFormView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		mEmailView = (EditText) findViewById(R.id.reg_email);
		mPasswordView = (EditText) findViewById(R.id.reg_password);
		mNameView = (EditText) findViewById(R.id.reg_fullname);
		//mRegistrationFormView = findViewById(R.id.registration_form);
		
		
		findViewById(R.id.btnRegister).setOnClickListener(
				new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						attemptRegistration();					
					}
				}
			);
		
		findViewById(R.id.link_to_login).setOnClickListener(
				new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						finish();					
					}
				}
			);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	public void attemptRegistration(){
		//TODO: check for Task, see LoginActivity attemptLogin()
		
		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);
		mNameView.setError(null);
		
		// Store values at the time of the registration attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mFullName = mNameView.getText().toString();
		
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
		
		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}
		
		// Check for a valid name.
		if (TextUtils.isEmpty(mFullName)) {
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
			// perform the user registration attempt.
//			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
//			showProgress(true);
			//TODO: authentication
//			mAuthTask = new UserLoginTask();
//			mAuthTask.execute((Void) null);
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		
	}
}
