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
	 * Location, when the last request was send.
	 */
	private Location lastRequestLocation;
	
	/**
	 * Timestamp, when the last request was send.
	 */
	private long lastRequestTime;
	
	public ServerManager() {
		lastRequestLocation = new Location("");
		lastRequestTime = 0;
	}

	/**
	 * Request the server
	 * 
	 * @param location
	 *            Current location
	 */
	public void request(Context context, Location location) {
		if (location != null) {
			this.lastRequestLocation = location;
			this.lastRequestTime = System.currentTimeMillis();
	
			GetInksTask gmt = new GetInksTask(context);
			try {
				URI url = new URI(Settings.SERVER_URL + location.getLatitude() + "," + location.getLongitude() + "/");
				gmt.execute(url);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			Log.w(this.getClass().getName(), "requst with null location");
		}
	}

	/**
	 * Request the server, if it is necessary.
	 * 
	 * @param location
	 *            Current location
	 */	
	public void requestIfNecessary(Context context, Location location) {
		float distanceInMeters = location.distanceTo(this.lastRequestLocation);
		if(distanceInMeters > Settings.REQUEST_DISTANCE_CHANGE || checkTimerToRequestServer()) {
			this.request(context, location);
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
		// TODO: improve
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
		return (System.currentTimeMillis() - this.lastRequestTime) > (Settings.REQUEST_TIME_PERIOD * 1000);
	}
	
}
