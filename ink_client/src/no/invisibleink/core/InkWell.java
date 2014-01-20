package no.invisibleink.core;

import no.invisibleink.core.inks.InkList;
import no.invisibleink.core.manager.LocationManager;
import no.invisibleink.core.manager.ServerManager;
import android.location.Location;

public class InkWell {
	
	/** Singleton InkWell object */
	private static InkWell mInstance = null;
	
	/** List with all local inks */
	private InkList inks;
	
	/** Location helper */
	private LocationManager locationHelper;
	
	/** Server manager */
	private ServerManager serverManager;
	
	/**
	 * 
	 */
	private InkWell() {
		inks = new InkList();
		locationHelper = new LocationManager(this);
		serverManager = new ServerManager(this);
	}
	
	/**
	 * Get singleton InkWell. Return InkWell object. If it not exits, create it
	 * first.
	 * 
	 * @return InkWell object
	 */
	public static InkWell getInstance(){
		if (mInstance == null) {
			mInstance = new InkWell();
		}
		return mInstance; 
	}
	
	/**
	 * Get list with all local inks.
	 * 
	 * @return List with all local inks.
	 */
	public InkList getInkList() {
		return inks;
	}
	
	/**
	 * Clears old list and added all new inks.
	 * 
	 * @param inkList List with inks
	 */
	public void setInkList(InkList inkList) {
		this.inks.clear();
		this.inks.addAll(inkList);
	}
	
	/**
	 * Get location manager.
	 * 
	 * @return Location manager.
	 */
	public LocationManager getLocationManager() {
		return locationHelper;
	}
	
	/**
	 * Get server manager.
	 * 
	 * @return Server manager;
	 */
	public ServerManager getServerManager() {
		return serverManager;
	}
	
    
    /**
     * TODO: Called by server
     */
/*    public void update() {
    	// TODO: Just stub inks
    	Location loc0 = new Location("");
    	loc0.setLatitude(59.942724);
    	loc0.setLongitude(10.717987);
    	Ink myInk0 = new Ink(0, loc0, 50, "HelloWorld", "This is a ink with a sample message", "Hans", 000000);

    	Location loc1 = new Location("");
    	loc1.setLatitude(59.944554);
    	loc1.setLongitude(10.716855);
    	Ink myInk1 = new Ink(0, loc1, 40, "HelloInk", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.", "Peter", 000001);
    	
    	this.inks.add(myInk0);
    	this.inks.add(myInk1);
    }*/
    
    /**
     * Update everything, which need a new location.
     * 
     * @param location Current location
     */
    public void update(Location location) {
    	this.inks.updateVisibility(location);
    	this.serverManager.request(location);    	
    }
 
}
