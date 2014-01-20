package no.invisibleink.core.manager;

import java.util.List;
import java.util.concurrent.ExecutionException;

import no.invisibleink.core.InkWell;
import no.invisibleink.core.inks.Ink;
import no.invisibleink.core.inks.InkList;
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
	
	private InkWell inkWell;
	
	public ServerManager(InkWell inkWell) {
		lastRequestLocation = new Location("");
		lastRequestTime = 0;
		this.inkWell = inkWell;
	}

	/**
	 * Request the server, if it is necessary.
	 * 
	 * @param location
	 *            Current location
	 * @return true, if server was requested (even when it was not successful)
	 */
	public boolean request(Location location) {
		if (isRequestNecessary(location)) {
			__debug("requestServer():");
			GetInksTask gmt = new GetInksTask();
			gmt.execute(location);
			try {
				// TODO: instead of catch NullPointer check sucess request?
				InkList inkList = gmt.get();
				__debug("Received " + inkList.size() + " inks");
				
				lastRequestLocation = location;
				lastRequestTime = System.currentTimeMillis();
				__debug("myLocation=(" + location + "), lastRequestTime=" + this.lastRequestTime);
// TODO: check request was sucessfull?
				inkWell.setInkList(inkList);
			} catch (NullPointerException e) {
				Log.w(this.getClass().getName(), e.getCause());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}			
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
