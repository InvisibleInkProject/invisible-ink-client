package no.invisibleink.core.server_comm;

import java.net.URI;
import java.net.URISyntaxException;
import android.location.Location;
import android.util.Log;

public class ServerManager {

	private final String SERVER = "http://server.invisibleink.no/api/v1/message/";
	
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
	 */
	public void request(Location location) {
		// Check request is necessary
		float distanceInMeters = location.distanceTo(lastRequestLocation);
		__debug("distence=" + distanceInMeters + "m to old location");
		if(distanceInMeters > REQUEST_DISTANCE_CHANGE || checkTimerToRequestServer()) {
			GetInksTask gmt = new GetInksTask();
			try {
				URI url = new URI(SERVER + location.getLatitude() + "," + location.getLongitude() + "/");
				gmt.execute(url);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
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
