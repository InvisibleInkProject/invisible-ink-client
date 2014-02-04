package no.invisibleink.view;

import no.invisibleink.R;
import no.invisibleink.model.Ink;
import no.invisibleink.model.InkList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;


public class MapSectionFragment extends Fragment {

    /**
     * Note that this may be null if the Google Play services APK is not available.
     */
    private GoogleMap mMap;
    private FragmentManager fragmentManager;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_map, container, false);
        
        setUpMapIfNeeded();
        
        return rootView;
    }
    
    protected void setFragmentManager(FragmentManager fragmentManager) {
    	this.fragmentManager = fragmentManager;
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
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if ( this.fragmentManager != null && this.mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) this.fragmentManager.findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
            	mMap.setMyLocationEnabled(true);
        		mMap.setIndoorEnabled(true);
            }
        }
    } 
    
    /**
     * Update map
     * 
     * @param inkList
     */
	public void update(InkList inkList) {
		Log.d(MapSectionFragment.class.getName(), "update map");	
		setUpMapIfNeeded();
		mMap.clear();
		for(Ink i : inkList) {
			mMap.addCircle(i.getCircleOptions());
			mMap.addMarker(i.getMarkerOptions());
		}
	}        
    
}
