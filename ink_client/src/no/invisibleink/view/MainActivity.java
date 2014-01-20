package no.invisibleink.view;

import no.invisibleink.R;
import no.invisibleink.core.InkWell;
import no.invisibleink.core.inks.Ink;
import no.invisibleink.core.inks.InkList;
import android.location.Location;
import android.media.MediaRecorder.OutputFormat;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private InkWell inkWell;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpIfNeeded();
		
		__test();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    private void setUpIfNeeded() {
    	if (inkWell == null) {
    		inkWell = InkWell.getInstance();
    	}
    }	
	
    private void __test() {
    	Location stubLocation = inkWell.getLocationManager().getMyLocation();
    	inkWell.getLocationManager().onMyLocationChange(stubLocation);

    	InkList inkList = inkWell.getInkList();
    	
    	TextView selection = (TextView) findViewById(R.id.textView1);
    	String output = new String();
    	output += "Location: " + stubLocation.getLatitude() + "," + stubLocation.getLongitude() + "\n";
    	output += "Received inks: " + inkList.size() + "\n";
    	for(Ink i : inkList) {
    		// Shorten message, if it is too long.
    		String messagePreview = i.getMessage().substring(0, i.getMessage().length() > 15 ? 15 : i.getMessage().length());
    		output += i.getID() + ", " + stubLocation.distanceTo(i.getLocation()) + "m, " + messagePreview + "\n";	
    	}    	
    	selection.setText(output);
    }
    
}