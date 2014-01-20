package no.invisibleink.core.manager;

import no.invisibleink.core.InkWell;
import no.invisibleink.model.Ink;
import no.invisibleink.model.InkList;
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
		// TODO: STUB only
    	Location loc0 = new Location("");
    	loc0.setLatitude(59.942724);
    	loc0.setLongitude(10.717987);
    	Ink myInk0 = new Ink(0, loc0, 50, "HelloWorld", "This is a ink with a sample message", "Hans", 000000);

    	Location loc1 = new Location("");
    	loc1.setLatitude(59.944554);
    	loc1.setLongitude(10.716855);
    	Ink myInk1 = new Ink(2, loc1, 40, "HelloInk", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.", "Peter", 000001);
    	
    	InkList inkList = new InkList();
    	inkList.add(myInk0);
    	inkList.add(myInk1);  
		inkWell.setInkList(inkList);
		return true;
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
	
}
