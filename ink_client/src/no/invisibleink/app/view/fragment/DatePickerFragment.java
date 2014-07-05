package no.invisibleink.app.view.fragment;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Fragment that displays a date picker dialog
 *
 */
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
