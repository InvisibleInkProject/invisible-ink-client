package no.invisibleink.app.controller;

import java.util.Date;

import org.json.JSONArray;

import no.invisibleink.api.client.InkClientUsage;
import no.invisibleink.api.model.Ink;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

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
	
			InkClientUsage.getInk(location, new Ink.GetHandler() {
				
				@Override
				public void onFailureUnauthorized() {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void onSucess(JSONArray inks) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFailure(int statusCode) {
					// TODO Auto-generated method stub
					
				}
			});
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
	public void postInk(final String message, final int radius, final Date expires, final Location location, final Context context){
		InkClientUsage.postInk(message, radius, expires, location, new Ink.PostHandler() {
			
			@Override
			public void onFailureUnauthorized() {
				Toast.makeText(context, "Fail (Unauthorized)", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onSucess() {
				Toast.makeText(context, "Sucessfull", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onFailure(int statusCode) {
				Toast.makeText(context, "Fail (" + statusCode + ")", Toast.LENGTH_LONG).show();				
			}
		});
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
