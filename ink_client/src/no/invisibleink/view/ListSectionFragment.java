package no.invisibleink.view;

import no.invisibleink.R;
import no.invisibleink.core.InkWell;
import no.invisibleink.model.Ink;
import no.invisibleink.model.InkList;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListSectionFragment extends Fragment {

	private TextView selection;
	private Button button;
	private ProgressBar progressBar;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_list, container, false);
		this.selection = (TextView) rootView.findViewById(R.id.textView1);
		this.button = (Button) rootView.findViewById(R.id.secListButton);
		this.button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				progressBar.setVisibility(View.VISIBLE);
				// TODO: stub
				Location stubLocation = new Location("");
				stubLocation.setLongitude(0);
				stubLocation.setLatitude(59);
				InkWell.getInstance().onMyLocationChange(stubLocation);
			}
		});
		this.progressBar = (ProgressBar) rootView.findViewById(R.id.secListProgressBar);
		this.progressBar.setVisibility(View.GONE);
		
        return rootView;
    }
    
    /**
     * Update list view.
     * 
     * @param inkList
     * @param location
     */
	public void update(InkList inkList, Location location) {
		Log.d(MapSectionFragment.class.getName(), "update list");		
    	String output = new String();
    	output += "Location: " + location.getLatitude() + "," + location.getLongitude() + "\n";
    	output += "Received inks: " + inkList.size() + "\n";
    	for(Ink i : inkList) {
    		// Shorten message, if it is too long.
    		String messagePreview = i.getMessage().substring(0, i.getMessage().length() > 15 ? 15 : i.getMessage().length());
    		output += i.getID() + ", " + location.distanceTo(i.getLocation()) + "m, r" + i.getRadius() + "m, " + messagePreview + "\n";	
    	}
    	this.progressBar.setVisibility(View.GONE);
    	this.selection.setText(output);			
	}
}

