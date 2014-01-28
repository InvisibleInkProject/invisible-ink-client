package no.invisibleink.core;

import java.util.Observable;

import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;

import no.invisibleink.core.server_comm.ServerManager;
import no.invisibleink.model.InkList;
import no.invisibleink.model.UpdateView;
import android.location.Location;
import android.util.Log;

public class InkWell extends Observable implements OnMyLocationChangeListener {
	
	/** Singleton InkWell object */
	private static InkWell mInstance = null;
	
	/** List with all local inks */
	private InkList inkList;
	
	/** Server manager */
	private ServerManager serverManager;
	
	/** Current user location */
	private Location currentLocation;
	
	/**
	 * 
	 */
	private InkWell() {
		inkList = new InkList();
		serverManager = new ServerManager();
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
		if (inkList != null) {
			this.inkList.clear();
			this.inkList.addAll(inkList);
	//    	this.inkList.updateVisibility(getLocationManager().getMyLocation());
			// TODO: here shoudn't be null pointer!!
			try {
				this.setChanged();
				notifyObservers(new UpdateView(inkList, this.currentLocation));
			} catch (NullPointerException e) {
				Log.e(this.getClass().getName(), "Notify observers failed. Number of observers: " + this.countObservers());	
			}
		} else {
			Log.w(this.getClass().getName(), "Receive null inkList");			
		}
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
    public void update() {
//    	this.inkList.updateVisibility(this.currentLocation);
    	this.serverManager.request(this.currentLocation);    	
    }
    
	@Override
	public void onMyLocationChange(Location location) {
		this.currentLocation = location;
		this.update();
	}
 
}
