package no.invisibleink.core.manager;

import no.invisibleink.core.InkWell;
import android.location.Location;
import android.util.Log;

/**
 * The Manager takes care of the location, implements the
 * OnMyLocationChangeListener and holds the GoogleMap object (we get the
 * location from this object).
 * 
 * @author Fabian
 * 
 */
public class LocationManager {

	/**
	 * NOTE: For debug only.
	 */
	private static final boolean LOG_ON = false;
	
	/** Ink well object to call update function */
	private InkWell inkWell;
	
	public LocationManager(InkWell inkWell) {
		this.inkWell = inkWell;
	}

    /**
     * Get current location.
     * 
     * NOTE: Maybe null!
     * 
     * @return Current location is LatLng object
     */
    public Location getMyLocation() {
    	Location locationStub = new Location("");
    	locationStub.setLatitude(60);
    	locationStub.setLongitude(10);
    	return locationStub;
    }

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
