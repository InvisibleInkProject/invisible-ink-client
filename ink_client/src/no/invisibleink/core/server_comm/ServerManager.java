package no.invisibleink.core.server_comm;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.util.Log;

public class ServerManager {

	/**
	 * Server URL for communication
	 */
	protected static final String SERVER_URL = "http://server.invisibleink.no/api/v1/message/";
	
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
	private static final float REQUEST_DISTANCE_CHANGE = 30;
	
	/**
	 * If the time since the last server request is greater as this
	 * value, the server will be requested.
	 * 
	 * unit: seconds
	 */
	private static final long REQUEST_TIME_PERIOD = 0;
	
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
			this.lastRequestLocation = location;
			this.lastRequestTime = System.currentTimeMillis();

			GetInksTask gmt = new GetInksTask();
			try {
				URI url = new URI(SERVER_URL + location.getLatitude() + "," + location.getLongitude() + "/");
				gmt.execute(url);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * TODO: comment
	 * @param message
	 * @param radius
	 * @param location
	 * @param context To post response
	 */
	public void postInk(String message, int radius, Location location, Context context){
		JSONObject obj = new JSONObject();
		try {
			obj.put("text", message);
			obj.put("radius", radius);
			obj.put("user_id", 1);
			obj.put("location_lat", location.getLatitude());
			obj.put("location_lon", location.getLongitude());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		PostInkTask pmt = new PostInkTask(context);
		pmt.execute(obj);
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
