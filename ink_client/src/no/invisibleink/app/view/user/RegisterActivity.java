package no.invisibleink.app.view.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import no.invisibleink.app.MainActivity;
import no.invisibleink.app.R;
import no.invisibleink.app.view.section.DatePickerFragment;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {
		
	// input field contents at the time of the registration attempt.
	private String mEmail;
	private String mPassword;
	private String mFullName;
	//private String mGender;
	//private String mNation;
	private String mDate;
	
	// UI references.
	private EditText mEmailView;
	private EditText mNameView;
	private EditText mPasswordView;
	private EditText mDateView;
	//private Spinner mGenderView;
	private Spinner mNationView;
	
	//date related stuff:
	private int mDay;
	private int mMonth;
	private int mYear;
		
	//private View mRegistrationFormView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		mEmailView = (EditText) findViewById(R.id.reg_email);
		mPasswordView = (EditText) findViewById(R.id.reg_password);
		mNameView = (EditText) findViewById(R.id.reg_fullname);
		mDateView = (EditText) findViewById(R.id.date);
		//mGenderView = (Spinner) findViewById(R.id.gender);
		mNationView = (Spinner) findViewById(R.id.nationality);
		
		//populate nationality spinner:
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,createNationalityCollection());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mNationView.setAdapter(adapter);
		//set the selection to the user's locale later on (currently: smth's wrong in finding the correct position ... )
//		int pos = adapter.getPosition(Locale.getDefault().getDisplayCountry());
//		mNationView.setSelection(pos);
		//mRegistrationFormView = findViewById(R.id.registration_form);
		
		
		findViewById(R.id.btnRegister).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						attemptRegistration();
					}
				});

		findViewById(R.id.link_to_login).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		
		mDateView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment dialog = new DatePickerFragment();
				dialog.show(getFragmentManager(), "date picker");

			}
		});
				
	}

	private List<String> createNationalityCollection() {
		Set <String> nationalities = new HashSet<String>();
		Locale[] locales = Locale.getAvailableLocales();
		for(Locale l:locales){
			if(!l.getDisplayCountry().isEmpty()) nationalities.add(l.getDisplayCountry());
		}
		List<String> nations = new ArrayList<String>(nationalities);
		Collections.sort(nations);
		
		return nations;
	}


	public void attemptRegistration(){
		//TODO: check for Task, see LoginActivity attemptLogin()
		
		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);
		mNameView.setError(null);
		mDateView.setError(null);
		
		// Store values at the time of the registration attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mFullName = mNameView.getText().toString();
		mDate = mDateView.getText().toString();
		
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
		
		if(TextUtils.isEmpty(mDate)){
			mDateView.setError(getString(R.string.error_field_required));
			focusView = mDateView;
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
	
	private void updateDisplay(){
		mDateView.setText(
	            new StringBuilder()
	                    // Month is 0 based so add 1
	                    .append(mMonth + 1).append("-")
	                    .append(mDay).append("-")
	                    .append(mYear).append(" "));
	}
	

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		mYear = year;
		mMonth = monthOfYear;
		mDay = dayOfMonth;
		updateDisplay();
		
	}
}
