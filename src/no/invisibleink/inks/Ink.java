package no.invisibleink.inks;

import no.invisibleink.mapdemo.R;
import no.invisibleink.mapdemo.R.drawable;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Ink {

	private int id;
	private LatLng latLng;
	private double radiusInMeters;
	private String title;
	private String message;
	private CircleOptions circleOptions;
	private MarkerOptions markerOptions;
	private Boolean isVisible;
	
	public Ink(int id, LatLng latLng, double radiusInMeters, String title, String message) {
		this.id = id;
		this.latLng = latLng;
		this.radiusInMeters = radiusInMeters;
		this.title = title;
		this.message = message;
		
		this.circleOptions = new CircleOptions()
		.center(latLng)
		.radius(radiusInMeters)
		.strokeColor(Color.GRAY)
		.strokeWidth(2)
		.fillColor(0x30000000); 
		
		this.markerOptions = new MarkerOptions()
		.position(latLng)
		.title(title)
		.snippet(message)
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		
		this.markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
	}

	public int getId() {
		return this.id;
	}
	
	public CircleOptions getCircleOptions() {
		return this.circleOptions;
	}
	
	public void setCircleOptions(boolean isVisible) {
		if(!isVisible) {
			this.circleOptions.fillColor(0xffff0000); //RED
		}
	}

	public MarkerOptions getMarkerOptions() {
		return this.markerOptions;
	}
	
	public void visible(boolean isVisible) {
		Log.d("Ink", "isVisible" + isVisible);
		this.markerOptions.visible(isVisible);
		this.circleOptions.visible(isVisible);
	}
	
	public Double distanceTo(Location currentLoc, Ink ink) {
			int R = 6371000; // approximate radius of the earth
		 	Double lat1 = currentLoc.getLatitude();
	        Double lon1 = currentLoc.getLongitude();
	        Double lat2 = ink.latLng.latitude;
	        Double lon2 = ink.latLng.longitude;
	        Double latDistance = toRad(lat2-lat1);
	        Double lonDistance = toRad(lon2-lon1);
	        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
	                   Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
	                   Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	        Double distance = R * c;
	        
	        return distance;
	}
	
	 private static Double toRad(Double value) {
	        return value * Math.PI / 180;
	 }
	 
	 public void setIsVisible(Location currentLoc) {
		 double distance = distanceTo(currentLoc, this);
		 if (distance > radiusInMeters) {
			 this.isVisible = false;
		 }
		 else {
			 this.isVisible = true;
		 }		 
	 }
	 
	 public boolean getIsVisible() {
		 return this.isVisible;
	 }
	
}
