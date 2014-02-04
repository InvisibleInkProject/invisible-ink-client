package no.invisibleink.view;

import no.invisibleink.R;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PostSectionFragment extends Fragment {

	private EditText form_message;
	private SeekBar form_radius;
	private Button form_confirm;
	private TextView form_radius_output;
	
	// TODO: crap, just a fast workaround
	private MainActivity mainActivity;
	
	public void setMainActivity(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_post, container, false);
        form_message = (EditText) rootView.findViewById(R.id.editText1);
        form_radius = (SeekBar) rootView.findViewById(R.id.seekBar1);
        form_confirm = (Button) rootView.findViewById(R.id.button1);
        form_radius_output = (TextView) rootView.findViewById(R.id.seekBarProgressOutput);   
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
        
        form_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	String message = form_message.getText().toString();
            	int radius = form_radius.getProgress();
            	
            	if (message.isEmpty()) {
                	Toast.makeText(getView().getContext(), "Message field is empty", Toast.LENGTH_SHORT).show();	
            	} else {
// TODO: crap code, improve it!!
            		Location location = mainActivity.locationManager.getLocation();
            		if (location == null) {
                    	Toast.makeText(getView().getContext(), "No location. Turn on GPS.", Toast.LENGTH_SHORT).show();	            			
            		} else {
            			mainActivity.inkWell.getServerManager().postInk(message, radius, location, getView().getContext());
            		}
            	}
            }
        });             
        
        return rootView;
    }
}
