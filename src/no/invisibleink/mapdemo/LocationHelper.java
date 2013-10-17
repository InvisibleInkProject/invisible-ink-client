package no.invisibleink.mapdemo;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class LocationHelper implements OnMyLocationChangeListener, OnCameraChangeListener {

	private GoogleMap mMap;
	private Location myLastLocation;
	private long lastServerRequest;
	private List<Ink> inks;
	
	private static final boolean LOG_ON = true;
	
	/**
	 * If the distance between the current location and the last
	 * location is greater as this value, the server will be requested.
	 */
	public static final float DISTANCE_CHANGE_TO_SERVER_REQUEST_IN_METERS = 30;
	
	/**
	 * If the time since the last server request is greater as this
	 * value, the server will be requested.
	 */
	public static final long TIME_PERIOD_TO_SERVER_REQUEST_IN_SECONDS = 1000;
	
	public LocationHelper(GoogleMap mMap, List<Ink> inks) {
		this.mMap = mMap;
		this.myLastLocation = new Location("");
		this.lastServerRequest = 0;
		this.inks = inks;
	}

	@Override
	public void onMyLocationChange(Location location) {
		__debug("location lat: " + location.getLatitude() + ", lng:" + location.getLongitude());
		float distanceInMeters = location.distanceTo(myLastLocation);
		__debug("distence= " + distanceInMeters + "m to old location");
		this.myLastLocation = location;
		if(distanceInMeters > DISTANCE_CHANGE_TO_SERVER_REQUEST_IN_METERS || checkTimerToRequestServer()) {
			requestServer();
		}
/*		if(distanceInMeters > DISTANCE_CHANGE_TO_SERVER_REQUEST_IN_METERS) {
			__debug("yes2");
		}
		if(checkTimerToRequestServer()) {
			__debug("yes1");
		}*/
	}

	@Override
	public void onCameraChange(CameraPosition camera) {
		if (LOG_ON) Log.d("Map", "camera zoom: " + camera.zoom);		
	}
	
	private void requestServer() {		
		// TODO: instead of real request at the moment just a log output
		try {
			__debug("requestServer():");
			__debug("myLocation=(" + getMyLocationLatLng() + "), lastRequest=" + this.lastServerRequest + "ms, localIDs=" + this.getInkIds());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.lastServerRequest = System.currentTimeMillis();
	}
	
	/**
	 * FOR DEBUG ONLY. Function prints messages in the log.
	 * 
	 * @param message Log message
	 */
	private void __debug(String message) {
		if (LOG_ON) Log.d(this.getClass().getName(), message);		
	}
	
	/**
	 * 
	 * 
	 * @return True, if server should requested again.
	 */
	private boolean checkTimerToRequestServer() {
		return (System.currentTimeMillis() - this.lastServerRequest) > (TIME_PERIOD_TO_SERVER_REQUEST_IN_SECONDS * 1000);
	}
	
    /**
     * Get current location as LatLng object.
     * 
     * @return Current location is LatLng object
     * @throws Exception When current location is null
     */
    private LatLng getMyLocationLatLng() throws Exception {
    	Location myLocation = mMap.getMyLocation();
    	if (myLocation != null) {
        	return new LatLng(myLocation.getLatitude(), myLocation.getLongitude());    		
    	} else {
    		throw new Exception("Current location is null.");
    	}
    }
    
	/**
     * Zooms the map to the current location.
     * 
     * @param zoom Zoom level
     */
    private void zoomMapToCurrentLocation(float zoom) {
    	try {
			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(getMyLocationLatLng(), zoom));
		} catch (Exception e) {
			e.printStackTrace();
		}    	
    }
    
    
    /**
     * Get a list with all IDs of {@link #inks}.
     * 
     * @return List with all IDs of {@link #inks}
     */
    protected List<Integer> getInkIds() {
    	List<Integer> ids = new ArrayList<Integer>();
    	for(Ink ink : this.inks) {
    		ids.add(ink.getId());
    	}
    	return ids;
    }   
    
    
}
