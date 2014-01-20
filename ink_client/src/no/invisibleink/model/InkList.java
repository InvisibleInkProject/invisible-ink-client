package no.invisibleink.model;

import java.util.ArrayList;
import android.location.Location;

/**
 * List which stores all local inks.
 * 
 * @author Fabian
 *
 */
public class InkList extends ArrayList<Ink> {

	private static final long serialVersionUID = 1L;

	/**
	 * Updates the visibility of all inks depend on the given location and the
	 * visibility radius of the inks.
	 * 
	 * @param location Current user location
	 */
    public void updateVisibility(Location location) {
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
