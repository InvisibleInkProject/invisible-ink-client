package no.invisibleink.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

import no.invisibleink.api.client.InkClientUsage;
import no.invisibleink.api.model.Login;
import no.invisibleink.api.model.Registration;
import no.invisibleink.app.R;
import no.invisibleink.app.core.SessionManager;
import no.invisibleink.app.view.dialog.DatePickerFragment;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Activity which displays a registration screen to the user
 *
 */
public class RegisterActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {
		
	// input field contents at the time of the registration attempt.
	private String mEmail;
	private String mPassword;
	private String mUsername;
	private String mGender;
	private String mNation;
	private String mDate;
	
	// UI references.
	private EditText mEmailView;
	private EditText mUsernameView;
	private EditText mPasswordView;
	private EditText mDateView;
	private Spinner mGenderView;
	private Spinner mNationView;
	
	//date related stuff:
	private int mDay;
	private int mMonth;
	private int mYear;
	private Map <String, String> nat;

	//TODO: add progress spinner while running registration asynchronous task !

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		mEmailView = (EditText) findViewById(R.id.reg_email);
		mPasswordView = (EditText) findViewById(R.id.reg_password);
		mUsernameView = (EditText) findViewById(R.id.reg_fullname);
		mDateView = (EditText) findViewById(R.id.date);
		mGenderView = (Spinner) findViewById(R.id.gender);
		mNationView = (Spinner) findViewById(R.id.nationality);
		
		//populate nationality spinner:
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, createNationalityCollection());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mNationView.setAdapter(adapter);
		mNationView.setSelection(adapter.getPosition(Locale.getDefault().getDisplayCountry()));
		//set the selection to the user's locale later on (currently: smth's wrong in finding the correct position ... )
//		int pos = adapter.getPosition(Locale.getDefault().getDisplayCountry());
//		mNationView.setSelection(pos);
		//mRegistrationFormView = findViewById(R.id.registration_form);
	
	
		Button btnRegister = (Button) findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						attemptRegistration();
					}
				});

		TextView link_to_login = (TextView) findViewById(R.id.link_to_login);
		link_to_login.setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						toLogin(); //return to login screen
					}
				});
	
		mDateView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment dialog = new DatePickerFragment();
				dialog.show(getFragmentManager(), DatePickerFragment.TAG);
			}
		});
				
	}

	/**
	 * creates a list of nationalities based on the locale bundle
	 * @return list of nationalities 
	 */
	private List<String> createNationalityCollection() {
		nat = new HashMap<String, String>();
		Locale[] locales = Locale.getAvailableLocales();
		for (Locale l : locales) {
			try {
				if (!l.getDisplayCountry().isEmpty())
					nat.put(l.getDisplayCountry(), l.getISO3Country());
			} catch (MissingResourceException e) {
				// simply don't add this country to the list ... for now, at
				// least
			}

			nat.put("--", ""); // offer no-country selection
		}
		List<String> nations = new ArrayList<String>(nat.keySet());
		Collections.sort(nations);

		return nations;
	}

	/**
	 * starts the login activity
	 */
	private void toLogin() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}	
	
	/**
	 * attempt to register a new user with the given information from the form
	 * directly performs the login action if the registration was successful
	 * TODO: add progress spinner
	 */
	public void attemptRegistration(){
		
		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);
		mUsernameView.setError(null);
		mDateView.setError(null);
		
		// Store values at the time of the registration attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mUsername = mUsernameView.getText().toString();
		mDate = mDateView.getText().toString();
		mGender = mGenderView.getSelectedItem().toString();
		mNation = nat.get(mNationView.getSelectedItem().toString());	
		
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
		if (TextUtils.isEmpty(mUsername)) {
			mUsernameView.setError(getString(R.string.error_field_required));
			focusView = mUsernameView;
			cancel = true;
		} 
		
		//TODO: check for valid age (min age, ...)
		if(TextUtils.isEmpty(mDate)){
			mDateView.setError(getString(R.string.error_field_required));
			focusView = mDateView;
			cancel = true;
		}
		
		if (cancel) {
			// There was an error; don't attempt registration and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {

			final SessionManager mg = new SessionManager(getApplicationContext());
			
			InkClientUsage.registration(mUsername, mPassword, mEmail, mDate, mGender, mNation, new Registration.PostHandler() {
				
				@Override
				public void onSuccess(String client_id, String client_secret) {
					mg.storeRegisterData(client_id, client_secret);
					
					InkClientUsage.login(mUsername, mPassword, mg.getClientID(), mg.getClientSecret(), new Login.PostHandler() {
						
						@Override
						public void onSuccess(String accessToken, String refreshToken,
								String expires_in) {
							mg.storeLoginData(accessToken, refreshToken, expires_in, mUsername);
							// then start main activity 
							Intent intent = new Intent(getApplicationContext(), MainActivity.class);
							startActivity(intent);
							finish();
						}
						
						@Override
						public void onFailureInvalid() {
							//TODO: error output
						}
						
						@Override
						public void onFailure(int statusCode) {
							//TODO: error output
						}
					});					
				}
				
				@Override
				public void onFailureUserAlreadyExits() {
					//TODO: error output 
				}
				
				@Override
				public void onFailure(int statusCode) {
					//TODO: error output 
				}
			});
			
		}
	}
	
	// display the selected date from the dialog
	private void updateDateDisplay() {
		mDateView.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mYear).append("-").append(mMonth + 1).append("-")
				.append(mDay).append(""));
	}
	

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		mYear = year;
		mMonth = monthOfYear;
		mDay = dayOfMonth;
		updateDateDisplay();
	}
}
