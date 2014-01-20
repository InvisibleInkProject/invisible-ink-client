package no.invisibleink.view;

import java.util.Observable;
import java.util.Observer;

import no.invisibleink.R;
import no.invisibleink.core.InkWell;
import no.invisibleink.core.inks.Ink;
import no.invisibleink.core.inks.InkList;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity implements Observer {

	private InkWell inkWell;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUp();
		
		Location stubLocation = new Location("");
		stubLocation.setLongitude(60);
		stubLocation.setLatitude(0);
		inkWell.onMyLocationChange(stubLocation);
	}

    private void setUp() {
   		inkWell = InkWell.getInstance();
   		inkWell.addObserver(this);
    }
    
	@Override
	public void update(Observable observable, Object data) {
		if (data instanceof UpdateView) {
			Log.d(this.getClass().getName(), "update(..)");	

	    	InkList inkList = ((UpdateView) data).getInkList();
	    	Location location = ((UpdateView) data).getLocation();
	    	
	    	TextView selection = (TextView) findViewById(R.id.textView1);
	    	String output = new String();
	    	output += "Location: " + location.getLatitude() + "," + location.getLongitude() + "\n";
	    	output += "Received inks: " + inkList.size() + "\n";
	    	for(Ink i : inkList) {
	    		// Shorten message, if it is too long.
	    		String messagePreview = i.getMessage().substring(0, i.getMessage().length() > 15 ? 15 : i.getMessage().length());
	    		output += i.getID() + ", " + location.distanceTo(i.getLocation()) + "m, " + messagePreview + "\n";	
	    	}    	
	    	selection.setText(output);			
		}
	}    
    
    
}