package no.invisibleink.core;

import android.graphics.Color;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Ink {

	/** ID of the ink */
	private int id;
	
	/** Position of the ink */
	private Location position;
	
	/** Position of the ink in LatLng format */
	private LatLng positionLatLng;
	
	/** Visible radius of the ink in meters */
	private double radius;
	
	private String title;
	
	/** Message of the ink */
	private String message;
	
	/** Circle for the visible radius */
	private CircleOptions circleOptions;
	
	/** Marker for the message itself */
	private MarkerOptions markerOptions;
	
	/** Visibility of the ink (depends on user location) */
	private Boolean isVisible;
	
	public Ink(int id, Location position, double radius, String title, String message) {
		this.id = id;
		this.position = position;
		this.positionLatLng = new LatLng(position.getLatitude(), position.getLongitude());
		this.radius = radius;
		this.title = title;
		this.message = message;
		
		// TODO: change to false;
		this.isVisible = true;
		
		this.circleOptions = new CircleOptions()
			.center(positionLatLng)
			.radius(radius)
			.strokeColor(Color.GRAY)
			.strokeWidth(2)
			.fillColor(0x30000000); 
		
		this.markerOptions = new MarkerOptions()
			.position(positionLatLng)
			.title(title)
			.snippet(message)
			.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
	}

	/**
	 * Get the ID of the ink.
	 * 
	 * @return ID of the ink
	 */
	public int getID() {
		return this.id;
	}
	
	/**
	 * Get circle object.
	 * 
	 * @return circle object
	 */
	public CircleOptions getCircleOptions() {
		return this.circleOptions;
	}

	/**
	 * Get marker object.
	 * 
	 * @return marker object
	 */
	public MarkerOptions getMarkerOptions() {
		return this.markerOptions;
	}	
	
	
	// TODO: Necessary
	public void setCircleOptions(boolean isVisible) {
		if(!isVisible) {
			this.circleOptions.fillColor(0xffff0000); //RED
		}
	}

	// TODO: Necessary
	public void visible(boolean isVisible) {
		Log.d("Ink", "isVisible" + isVisible);
		this.markerOptions.visible(isVisible);
		this.circleOptions.visible(isVisible);
	}

	// TODO: Necessry ?	 
	 public void setIsVisible(Location currentLoc) {
/*		 double distance = distanceTo(currentLoc, this);
		 if (distance > radius) {
			 this.isVisible = false;
		 }
		 else {
			 this.isVisible = true;
		 }*/		 
	 }
	 
}
