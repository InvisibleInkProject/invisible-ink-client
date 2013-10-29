package no.invisibleink.core;

import java.util.LinkedList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

public class InkWell {
	private static InkWell mInstance = null;
	private List<Ink> inks;
	private LocationManager locationHelper;
	
	private InkWell() {
		inks = new LinkedList<Ink>();
//		locationHelper = new LocationHelper(mMap, inks);
	}
	
	public static InkWell getInstance(){
		if (mInstance == null) {
			mInstance = new InkWell();
		}
		return mInstance; 
	}
	
	public List<Ink> getInks() {
		return inks;
	}
	
	public LocationManager getLocationHelper() {
		return locationHelper;
	}
	
    
    /**
     * TODO: Called by server
     */
    public void update() {
        this.addInk(0, new LatLng(59.942724, 10.717987), 50, "HelloWorld", "This is a ink with a sample message");
        this.addInk(1, new LatLng(59.944554, 10.716855), 40, "HelloInk", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");    	
    }
    
    private void addInk(int id, LatLng position, double radiusInMeters, String title, String message) {
/*    	Ink newInk = new Ink(id, position, radiusInMeters, title, message);
    	newInk.setIsVisible(mMap.getMyLocation());
    	boolean isVisible = newInk.getIsVisible();    
    	newInk.setCircleOptions(isVisible);

    	inks.add(newInk); */
    }

}
