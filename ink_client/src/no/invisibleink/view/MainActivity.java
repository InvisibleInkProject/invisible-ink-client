package no.invisibleink.view;

import no.invisibleink.R;
import no.invisibleink.core.InkWell;
import android.location.Location;
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

    	TextView selection = (TextView) findViewById(R.id.textView1);
    	selection.setText("Hey, size" + inkWell.getInks().size());
    }
    
}