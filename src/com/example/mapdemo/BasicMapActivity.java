/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.mapdemo;

import java.util.LinkedList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * This shows how to create a simple activity with a map and a marker on the map.
 * <p>
 * Notice how we deal with the possibility that the Google Play services APK is not
 * installed/enabled/updated on a user's device.
 */
public class BasicMapActivity extends FragmentActivity implements OnMyLocationChangeListener, OnCameraChangeListener {
	
    /**
     * Note that this may be null if the Google Play services APK is not available.
     */
    private GoogleMap mMap;
    private List<Ink> inks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_demo);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not have been
     * completely destroyed during this process (it is likely that it would only be stopped or
     * paused), {@link #onCreate(Bundle)} may not be called again so we should call this method in
     * {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
    	inks = new LinkedList<Ink>();
    	
    	mMap.setMyLocationEnabled(true);
		mMap.setIndoorEnabled(true);
    	mMap.setOnMyLocationChangeListener(this);
    	mMap.setOnCameraChangeListener(this);

        this.addInk(new LatLng(59.942724, 10.717987), 50, "HelloWorld", "This is a ink with a sample message");
        this.addInk(new LatLng(59.944554, 10.716855), 40, "HelloInk", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
    }

	/**
     * Zooms the map to the current location.
     * 
     * @param zoom Zoom level
     */
    private void zoomMapToCurrentLocation(float zoom) {
    	try {
			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(getMyLocationLatLng(), zoom));
		} catch (Exception e) {
			e.printStackTrace();
		}    	
    }
    
    /**
     * Get current location as LatLng object.
     * 
     * @return Current location is LatLng object
     * @throws Exception When current location is null
     */
    private LatLng getMyLocationLatLng() throws Exception {
    	Location myLocation = mMap.getMyLocation();
    	if (myLocation != null) {
        	return new LatLng(myLocation.getLatitude(), myLocation.getLongitude());    		
    	} else {
    		throw new Exception("Current location is null.");
    	}
    }
    
    private void addInk(LatLng position, double radiusInMeters, String title, String message) {
    	Ink newInk = new Ink(position, radiusInMeters, title, message);
    	this.inks.add(newInk);
    	mMap.addCircle(newInk.getCircleOptions());
    	mMap.addMarker(newInk.getMarkerOptions());
/*		mMap.addCircle(new CircleOptions()
			.center(position)
			.radius(radiusInMeters)
			.strokeColor(Color.GRAY)
			.strokeWidth(2)
			.fillColor(0x30000000));   	
    	mMap.addMarker(new MarkerOptions()
			.position(position)
			.title(title)
			.snippet(message)
			.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
			*/
    }

	@Override
	public void onMyLocationChange(Location location) {
//		zoomMapToCurrentLocation(16);
		Log.d("Map", "location lat: " + location.getLatitude() + ", lng:" + location.getLongitude());
	}

	@Override
	public void onCameraChange(CameraPosition camera) {
		Log.d("Map", "camera zoom: " + camera.zoom);		
	}
}
