package no.invisibleink.app.core;

import org.json.JSONArray;

import no.invisibleink.api.client.InkClientUsage;
import no.invisibleink.api.model.Ink;
import android.content.Context;
import android.location.Location;
import android.util.Log;

public class ServerManager {
	
	public static final String TAG = ServerManager.class.getSimpleName();
	
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
	public static final long REQUEST_TIME_PERIOD = 20;
	
	
	/**
	 * The server returns all inks, which are in this radius.
	 * 
	 * unit: meters
	 */
	public static final float REQUEST_INKS_RADIUS_IN_METERS = 2000;
	

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
					
					/*				JSONObject o;
					
					if (inks.length() < 0) {
						Log.i(TAG, "Empty inks");
						return;
					}
					
					try {
						o = inks.getJSONObject(0);
						Log.w(TAG, o.getString(Ink.TEXT));
						
						
						String filename = "myfile";
						String string = "Hello world!";
						
						File file = new File(getFilesDir(), filename);
						FileOutputStream outputStream = openFileOutput(filename, MODE_PRIVATE);
						outputStream.write(string.getBytes());
						outputStream.close();
						
						Log.w(TAG, "" + file.getAbsoluteFile());
						Log.w(TAG, "" + file.getAbsolutePath());
						
//						fileList()
						
					} catch (JSONException e) {
						Log.w(TAG, e.getMessage());
					} catch (Exception e) {
						e.printStackTrace();
					}*/					
				}
				
				@Override
				public void onFailure(int statusCode) {
					// TODO Auto-generated method stub
					
				}
			});
		} else {
			Log.w(TAG, "requst with null location");
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
		if(distanceInMeters > REQUEST_DISTANCE_CHANGE || checkTimerToRequestServer()) {
			this.request(context, location);
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
	
}
