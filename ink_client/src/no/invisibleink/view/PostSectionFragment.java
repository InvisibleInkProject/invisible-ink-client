package no.invisibleink.view;

import no.invisibleink.R;
import no.invisibleink.listener.OnPostSectionFragmentListener;
import android.app.Activity;
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

	private OnPostSectionFragmentListener mCallback;

	private EditText form_message;
	private SeekBar form_radius;
	private Button form_confirm;
	private TextView form_radius_output;

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
                	Toast.makeText(view.getContext(), "Message field is empty", Toast.LENGTH_SHORT).show();	
            	} else {
           			mCallback.onPostInkForm(message, radius);
            	}
            }
        });             
        
        return rootView;
    }
}
