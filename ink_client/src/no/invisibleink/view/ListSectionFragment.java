package no.invisibleink.view;

import no.invisibleink.R;
import no.invisibleink.model.Ink;
import no.invisibleink.model.InkList;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ListSectionFragment extends Fragment {

	private OnListSectionFragmentListener mCallback;
	
	private TextView selection;
	private Button button_request;
	private ProgressBar progressBar_request;
	private ToggleButton toogleButton_requestUpdate;
	
	public interface OnListSectionFragmentListener {

		/**
		 * Request inks from server
		 */
	    public void onRequestInks();
	    
	    
	    public void doLocationUpdates(boolean yes);
	}	
	

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnListSectionFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_list, container, false);
		this.selection = (TextView) rootView.findViewById(R.id.textView1);
		this.button_request = (Button) rootView.findViewById(R.id.secListButton);
		this.button_request.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				progressBar_request.setVisibility(View.VISIBLE);
				mCallback.onRequestInks();
			}
		});
		this.progressBar_request = (ProgressBar) rootView.findViewById(R.id.secListProgressBar);
		this.progressBar_request.setVisibility(View.GONE);
		this.toogleButton_requestUpdate = (ToggleButton) rootView.findViewById(R.id.start_updates); 
		this.toogleButton_requestUpdate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCallback.doLocationUpdates(toogleButton_requestUpdate.isChecked());
			}
		});
		
        return rootView;
    }
    
    /**
     * Update list view.
     * 
     * @param inkList
     * @param location
     */
	public void update(InkList inkList, Location location) {
		inkList.updateVisibility(location);
    	String output = new String();
    	output += "Location: " + location.getLatitude() + "," + location.getLongitude() + "\n";
    	output += "Received inks: " + inkList.size() + "\n";
    	for(Ink i : inkList) {
    		// Shorten message, if it is too long.
    		String messagePreview = "";
    		if(i.getIsVisible()){
    			messagePreview = i.getMessage().substring(0, i.getMessage().length() > 15 ? 15 : i.getMessage().length());
    		}else{
    			messagePreview = "out of reach";
    		}
    		output += i.getID() + ", " + location.distanceTo(i.getLocation()) + "m, r" + i.getRadius() + "m, " + messagePreview + "\n";
    	}
    	this.progressBar_request.setVisibility(View.GONE);
    	this.selection.setText(output);			
	}
}

