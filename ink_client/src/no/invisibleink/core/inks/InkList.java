package no.invisibleink.core.inks;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;

/**
 * List with hold all local inks.
 * 
 * @author Fabian
 *
 */
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
