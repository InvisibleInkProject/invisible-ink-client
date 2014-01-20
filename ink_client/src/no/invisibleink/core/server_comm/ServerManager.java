package no.invisibleink.core.server_comm;

import no.invisibleink.core.InkWell;
import no.invisibleink.model.Ink;
import no.invisibleink.model.InkList;
import android.location.Location;

public class ServerManager {

	/**
	 * Request the server, if it is necessary.
	 * 
	 * @param location
	 *            Current location
	 */
	public void request(Location location) {
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

		InkWell.getInstance().setInkList(inkList);
	}
	
}
