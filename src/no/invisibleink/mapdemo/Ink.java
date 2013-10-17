package no.invisibleink.mapdemo;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Ink {

	private LatLng latLng;
	private double radiusInMeters;
	private String title;
	private String message;
	private CircleOptions circleOptions;
	private MarkerOptions markerOptions;
	
	public Ink(LatLng latLng, double radiusInMeters, String title, String message) {
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

	public CircleOptions getCircleOptions() {
		return this.circleOptions;
	}

	public MarkerOptions getMarkerOptions() {
		return this.markerOptions;
	}
	
	public void visible(boolean isVisible) {
		Log.d("Ink", "isVisible" + isVisible);
		this.markerOptions.visible(isVisible);
		this.circleOptions.visible(isVisible);
	}
	
	
	
}
