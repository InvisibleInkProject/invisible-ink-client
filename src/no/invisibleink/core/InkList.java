package no.invisibleink.core;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;

public class InkList extends ArrayList<Ink> {

	private static final long serialVersionUID = 1L;

	/**
     * Get a list with all IDs.
     * 
     * @return List with all IDs
     */
    public List<Integer> getInkIds() {
    	List<Integer> ids = new ArrayList<Integer>();
    	for(Ink ink : this) {
    		ids.add(ink.getID());
    	}
    	return ids;
    }
    
    public void add(int id, Location position, double radius, String title, String message) {
    	Ink newInk = new Ink(id, position, radius, title, message);
    	this.add(newInk);
    	// TODO: stuff:
//    	newInk.setIsVisible(mMap.getMyLocation());
//    	boolean isVisible = newInk.getIsVisible();    
//    	newInk.setCircleOptions(isVisible);

//    	mMap.addCircle(newInk.getCircleOptions());
//    	mMap.addMarker(newInk.getMarkerOptions());
    }
    
    public void updateVisibility(Location location) {
    	// TODO: implement function
    }
    
}
