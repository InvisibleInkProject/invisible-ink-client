package no.invisibleink.core;

import java.util.Date;

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
	
	/** Location of the ink */
	private Location location;
	
	/** Location of the ink in LatLng format */
	private LatLng locationLatLng;
	
	/** Visible radius of the ink in meters */
	private double radius;
	
	/** Title of the message */
	private String title;
	
	/** Timestamp of the ink */
	private long timestamp;
	
	/** Message of the ink */
	private String message;
	
	/** Author name of the ink */ 
	private String author;
	
	/** Circle for the visible radius */
	private CircleOptions circleOptions;
	
	/** Marker for the message itself */
	private MarkerOptions markerOptions;
	
	/** Visibility of the ink (depends on user location) */
	private Boolean isVisible;
	
	/**
	 * Create an ink object.
	 * 
	 * @param id
	 *            ID
	 * @param location
	 *            Location
	 * @param radius
	 *            Radius in meters
	 * @param title
	 *            Title of the message
	 * @param message
	 *            Content of the ink
	 * @param author
	 *            Author name
	 * @param timestamp
	 *            Timestamp
	 */
	public Ink(int id, Location location, double radius, String title, String message, String author, long timestamp) {
		this.id = id;
		this.location = location;
		this.locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
		this.radius = radius;
		this.title = title;
		this.message = message;
		this.author = author;
		this.timestamp = timestamp;
		
		// TODO: change to false;
		this.isVisible = true;
		
		// Set map circle
		this.circleOptions = new CircleOptions()
			.center(locationLatLng)
			.radius(radius)
			.strokeColor(Color.GRAY)
			.strokeWidth(2)
			.fillColor(0x30000000); 
		// Set map marker
		this.markerOptions = new MarkerOptions()
			.position(locationLatLng)
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
	 * Get the location of the ink.
	 * 
	 * @return Location of the ink
	 */
	public Location getLocation() {
		return this.location;
	}
	
	/**
	 * Get the visibility radius in meters.
	 * 
	 * @return Visibility radius in meters.
	 */
	public double getRadius() {
		return this.radius;
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
	
	
	// TODO: Necessary?
	public void setCircleOptions(boolean isVisible) {
		if(!isVisible) {
			this.circleOptions.fillColor(0xffff0000); //RED
		}
	}

	// TODO: Necessary?
	public void visible(boolean isVisible) {
		Log.d("Ink", "isVisible" + isVisible);
		this.markerOptions.visible(isVisible);
		this.circleOptions.visible(isVisible);
	}

	// TODO: Necessary?	 
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
