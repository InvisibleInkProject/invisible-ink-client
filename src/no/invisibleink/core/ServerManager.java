package no.invisibleink.core;

import java.util.List;

import android.location.Location;
import android.util.Log;

public class ServerManager {

	/**
	 * Location, when the last request was send.
	 */
	private Location lastRequestLocation;
	
	/**
	 * Timestamp, when the last request was send.
	 */
	private long lastRequestTime;
	
	/**
	 * NOTE: For debug only.
	 */
	private static final boolean LOG_ON = true;
	
	/**
	 * If the distance between the current location and the last
	 * location is greater as this value, the server will be requested.
	 * 
	 * unit: meters
	 */
	public static final float REQUEST_DISTANCE_CHANGE = 30;
	
	/**
	 * If the time since the last server request is greater as this
	 * value, the server will be requested.
	 */
	public static final long REQUEST_TIME_PERIOD = 1000;
	
	public boolean isRequestNecessary(Location location) {
		float distanceInMeters = location.distanceTo(lastRequestLocation);
		__debug("distence=" + distanceInMeters + "m to old location");
		if(distanceInMeters > REQUEST_DISTANCE_CHANGE || checkTimerToRequestServer()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void requestServer(Location location, List<Integer> inkIDs) {		
		// TODO: instead of real request at the moment just a log output
		try {
			lastRequestLocation = location;

			__debug("requestServer():");
			__debug("myLocation=(" + location + "), lastRequestTime=" + this.lastRequestTime + "ms, localIDs=" + inkIDs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		lastRequestTime = System.currentTimeMillis();
	}
	
	
	/**
	 * 
	 * 
	 * @return True, if server should requested again.
	 */
	private boolean checkTimerToRequestServer() {
		return (System.currentTimeMillis() - this.lastRequestTime) > (REQUEST_TIME_PERIOD * 1000);
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
