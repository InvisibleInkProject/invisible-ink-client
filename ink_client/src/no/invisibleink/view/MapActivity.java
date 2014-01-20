package no.invisibleink.view;

import java.util.Observable;
import java.util.Observer;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import no.invisibleink.R;
import no.invisibleink.core.InkWell;
import no.invisibleink.model.Ink;
import no.invisibleink.model.InkList;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class MapActivity extends FragmentActivity implements Observer {

	private InkWell inkWell;
	
    /**
     * Note that this may be null if the Google Play services APK is not available.
     */
    private GoogleMap mMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_map);
		setUpIfNeeded();
        setUpMapIfNeeded();	       
 	}

    @Override
    protected void onResume() {
        super.onResume();
		setUpIfNeeded();
        setUpMapIfNeeded();
    }

    private void setUpIfNeeded() {
//    	if (inkWell == null) {
   		inkWell = InkWell.getInstance();
        inkWell.addObserver(this);
 //   	}
    }
    
    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
    	mMap.setMyLocationEnabled(true);
		mMap.setIndoorEnabled(true);
// TODO:
    	mMap.setOnMyLocationChangeListener(inkWell);   	
    }

	@Override
	public void update(Observable observable, Object data) {
		Log.d(this.getClass().getName(), "update(..)");	
		mMap.clear();
		InkList inkList = (InkList) data;
		for(Ink i : inkList) {
			mMap.addCircle(i.getCircleOptions());
			mMap.addMarker(i.getMarkerOptions());
		}
	}    
    
}
