package no.invisibleink.core.manager;

import java.util.List;
import java.util.concurrent.ExecutionException;

import no.invisibleink.core.inks.Ink;
import no.invisibleink.core.server_comm.GetInksTask;
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
	 * @return true, if server was requested
	 */
	public boolean request(Location location) {
		if (isRequestNecessary(location)) {
			__debug("requestServer():");
			GetInksTask gmt = new GetInksTask();
			gmt.execute(location);
			try {
				List<Ink> inks = gmt.get();
				__debug("Received inks" + inks.toString());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			lastRequestLocation = location;
			lastRequestTime = System.currentTimeMillis();
			// TODO: instead of real request at the moment just a log output
			__debug("myLocation=(" + location + "), lastRequestTime=" + this.lastRequestTime);
			
			return true;
		} else {
			return false;
		}
	}
	
	// TODO: necessary: no one uses this function? (Fabian)
//	public void createInk(Location location, String message, Context context){
//		PostInkTask pmt = new PostInkTask(context);
//		pmt.execute(location, message);
//	}
	
	// TODO: necessary: no one uses this function? (Fabian)
//	public void readAll(Location location){
//		GetInksTask gmt = new GetInksTask();
//		gmt.execute(location);
//	}
	
	/**
	 * TODO: comment
	 * 
	 * @param location
	 * @return
	 */
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
