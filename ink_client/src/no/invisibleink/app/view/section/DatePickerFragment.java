package no.invisibleink.app.view.section;


import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;



public class DatePickerFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		//Use the current date as default values:
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_YEAR);
		int month = c.get(Calendar.MONTH)-2;
		int year = c.get(Calendar.YEAR);

		return new DatePickerDialog(getActivity(), (OnDateSetListener) getActivity(), year, month, day);
	}

}