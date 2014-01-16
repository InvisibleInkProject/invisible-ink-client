package no.invisibleink.core.inks;

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
    
//    public boolean add(Ink newInk) {
//    	this.add(newInk);
//    	// TODO: stuff:
//    	newInk.setIsVisible(mMap.getMyLocation());
//    	boolean isVisible = newInk.getIsVisible();    
//    	newInk.setCircleOptions(isVisible);

//    	mMap.addCircle(newInk.getCircleOptions());
//    	mMap.addMarker(newInk.getMarkerOptions());
//    }
    
	/**
	 * Updates the visibility of all inks depend on the given location and the
	 * visibility radius of the inks.
	 * 
	 * @param location Current user location
	 */
    public void updateVisibility(Location location) {
    	// TODO: more efficient implementation?
    	for (Ink i : this) {
    		// Check if distance to center of the ink is smaller then its visibility radius
    		if (i.getLocation().distanceTo(location) <= i.getRadius() ) {
    			i.visible(true);
    		} else {
    			i.visible(false);
    		}
    	}
    }
    
}
