package no.invisibleink.view;


import java.util.Calendar;
import java.util.Date;

import no.invisibleink.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import android.widget.Toast;

public class PostSectionFragment extends Fragment {

	private OnPostSectionFragmentListener mCallback;

	private EditText form_message;
	private SeekBar form_radius;
	private Button form_confirm;
	private TextView form_radius_output;

	private CheckBox activate_expire;
	private TimePicker form_expire_time;
	private DatePicker form_expire_date;

	public interface OnPostSectionFragmentListener {

		/**
		 * 
		 * @param message
		 *            Message
		 * @param radius
		 *            Visibility radius in meters
		 * @param expires
		 * 			  null or expire data
		 * 				
		 */
	    public void onPostInkForm(String message, int radius, Date expires);
	}	
	

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnPostSectionFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_post, container, false);
        form_message = (EditText) rootView.findViewById(R.id.editText1);
        form_radius = (SeekBar) rootView.findViewById(R.id.seekBar1);
        form_confirm = (Button) rootView.findViewById(R.id.button1);
        form_radius_output = (TextView) rootView.findViewById(R.id.seekBarProgressOutput);   

        activate_expire = (CheckBox) rootView.findViewById(R.id.checkBox1);
        form_expire_time = (TimePicker) rootView.findViewById(R.id.timePicker1);
        form_expire_date = (DatePicker) rootView.findViewById(R.id.datePicker1);
    
        // ----------------- form_expire_time       
        activate_expire.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
			        form_expire_time.setVisibility(View.VISIBLE);
			        form_expire_date.setVisibility(View.VISIBLE);
				} else {
			        form_expire_time.setVisibility(View.INVISIBLE);
			        form_expire_date.setVisibility(View.INVISIBLE);
				}
			}
		});
        
        // ----------------- form_expire_time
        form_expire_time.setVisibility(View.INVISIBLE);
        form_expire_time.setIs24HourView(true);
 
        // ----------------- form_expire_date        
        form_expire_date.setVisibility(View.INVISIBLE);
        form_expire_date.setCalendarViewShown(false);
        
        // ----------------- form_radius

        form_radius.setMax(2000);
        form_radius.setProgress(500);
        form_radius_output.setText(String.format(rootView.getResources().getString(R.string.radius_output), form_radius.getProgress()));
        form_radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
	            form_radius_output.setText(String.format(getResources().getString(R.string.radius_output), progress));					
			}
		});
        

        // ----------------- form_confirm

        form_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	String message = form_message.getText().toString();
            	int radius = form_radius.getProgress();

            	Date expire = null;
            	if (activate_expire.isChecked()) {
	            	Calendar cal = Calendar.getInstance();
	            	cal.set(Calendar.YEAR, form_expire_date.getYear());
	            	cal.set(Calendar.MONTH, form_expire_date.getMonth());
	            	cal.set(Calendar.DATE, form_expire_date.getDayOfMonth());
	            	cal.set(Calendar.HOUR_OF_DAY, form_expire_time.getCurrentHour());
	            	cal.set(Calendar.MINUTE, form_expire_time.getCurrentMinute());
	            	expire = cal.getTime();
            	}

            	
            	if (message.isEmpty()) {
                	Toast.makeText(view.getContext(), "Message field is empty", Toast.LENGTH_SHORT).show();	
            	} else {

           			mCallback.onPostInkForm(message, radius, expire);

            	}
            }
        });             
        
        return rootView;
    }
}
