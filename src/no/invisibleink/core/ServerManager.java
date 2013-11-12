package no.invisibleink.core;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Context;
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
	 * 
	 * unit: seconds
	 */
	public static final long REQUEST_TIME_PERIOD = 1000;
	
	public ServerManager() {
		lastRequestLocation = new Location("");
		lastRequestTime = 0;
	}

	/**
	 * Request the server, if it is necessary.
	 * 
	 * @param location
	 *            Current location
	 * @param inkIDs
	 *            IDs of all local inks
	 * @return true, if server was requested
	 */
	public boolean request(Location location, List<Integer> inkIDs) {
		if (isRequestNecessary(location)) {

			// TODO: instead of real request at the moment just a log output	
			GetMessageTask gmt = new GetMessageTask();
			gmt.execute(location);
			try {
				List<Ink>inks = gmt.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lastRequestLocation = location;
			lastRequestTime = System.currentTimeMillis();

			__debug("requestServer():");
			__debug("myLocation=(" + location + "), lastRequestTime=" + this.lastRequestTime + "ms, localIDs=" + inkIDs);
			
			return true;
		} else {
			return false;
		}
	}
	

	public void createInk(Location location, String message, Context context){
		PostMessageTask pmt = new PostMessageTask(context);
		pmt.execute(location, message);
	}
	
	public void readAll(Location location){
		GetMessageTask gmt = new GetMessageTask();
		gmt.execute(location);
	}
	
	
	public boolean isRequestNecessary(Location location) {
		float distanceInMeters = location.distanceTo(lastRequestLocation);
		__debug("distence=" + distanceInMeters + "m to old location");
		if(distanceInMeters > REQUEST_DISTANCE_CHANGE || checkTimerToRequestServer()) {
			return true;
		} else {
			return false;
		}
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
