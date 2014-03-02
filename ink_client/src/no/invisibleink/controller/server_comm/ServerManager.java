package no.invisibleink.controller.server_comm;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import no.invisibleink.controller.Settings;
import no.invisibleink.model.GsonInk;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServerManager {
	
	private static final String LOG = "ServerManager";

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
				URI url = new URI(Settings.API_URL + "message/"+ location.getLatitude() + "," + location.getLongitude() + "," + Settings.REQUEST_INKS_RADIUS_IN_METERS + "/");
				gmt.execute(url);
			} catch (URISyntaxException e) {
				Log.w(LOG, "URISyntaxException: " + e.getMessage());
			}
		} else {
			Log.w(LOG, "requst with null location");
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
	public void postInk(String message, int radius, Date expires, Location location, Context context){
		GsonInk ink = new GsonInk();
		ink.setText(message);
		ink.setRadius(radius);
		ink.setLocation_lat(location.getLatitude());
		ink.setLocation_lon(location.getLongitude());
		ink.setUser_id(1);
		ink.setExpires(expires);
		
		Gson gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
		String stringEntity = gsonBuilder.toJson(ink);
		
		HttpPostTask pmt = new HttpPostTask(context);
		pmt.execute(Settings.API_URL + "message/", stringEntity);
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
