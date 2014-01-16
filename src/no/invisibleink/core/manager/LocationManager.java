package no.invisibleink.core.manager;

import no.invisibleink.core.InkWell;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;

public class LocationManager implements OnMyLocationChangeListener {

	/** Map object */
	private GoogleMap map;

	/**
	 * NOTE: For debug only.
	 */
	private static final boolean LOG_ON = true;
	
	/** Ink well object to call update function */
	private InkWell inkWell;
	
	public LocationManager(InkWell inkWell) {
		this.inkWell = inkWell;
	}

	/**
	 * Get map object.
	 * 
	 * @return Map object
	 */
	public GoogleMap getMap() {
		return map;
	}
	
    /**
     * Get current location.
     * 
     * NOTE: Maybe null!
     * 
     * @return Current location is LatLng object
     */
    public Location getMyLocation() {
    	return map.getMyLocation();
    }

	@Override
	public void onMyLocationChange(Location location) {
		__debug("location lat: " + location.getLatitude() + ", lng:" + location.getLongitude());
		this.inkWell.update(location);
	}
    
	/**
	 * FOR DEBUG ONLY. Function prints messages in the log.
	 * 
	 * @param message Log message
	 */
	private void __debug(String message) {
		if (LOG_ON) Log.d(this.getClass().getName(), message);		
	}
    
}
