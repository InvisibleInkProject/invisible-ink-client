package no.invisibleink.core;

import no.invisibleink.core.inks.InkList;
import no.invisibleink.core.manager.LocationManager;
import no.invisibleink.core.manager.ServerManager;
import android.location.Location;

public class InkWell {
	
	/** Singleton InkWell object */
	private static InkWell mInstance = null;
	
	/** List with all local inks */
	private InkList inkList;
	
	/** Location helper */
	private LocationManager locationHelper;
	
	/** Server manager */
	private ServerManager serverManager;
	
	/**
	 * 
	 */
	private InkWell() {
		inkList = new InkList();
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
		return inkList;
	}
	
	/**
	 * Clears old list and added all new inks.
	 * 
	 * @param inkList List with inks
	 */
	public void setInkList(InkList inkList) {
		this.inkList.clear();
		this.inkList.addAll(inkList);
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
     * Update everything, which need a new location.
     * 
     * @param location Current location
     */
    public void update(Location location) {
    	this.inkList.updateVisibility(location);
    	this.serverManager.request(location);    	
    }
 
}
