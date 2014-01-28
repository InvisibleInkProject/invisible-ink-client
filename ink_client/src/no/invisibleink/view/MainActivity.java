/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package no.invisibleink.view;

import java.util.Observable;
import java.util.Observer;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import no.invisibleink.R;
import no.invisibleink.core.InkWell;
import no.invisibleink.model.Ink;
import no.invisibleink.model.InkList;
import no.invisibleink.model.UpdateView;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener, Observer {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;
    
    static FragmentManager fragmentManager;
    static InkWell inkWell;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        fragmentManager = getSupportFragmentManager();        
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(fragmentManager);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        
        inkWell = InkWell.getInstance();
        inkWell.addObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_settings:
            	 Toast.makeText(getApplicationContext(), "Settings is selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_about:
            	 Toast.makeText(getApplicationContext(), "About is selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
    	super.onResume();
    }
    
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
            	case 1:
                    return new MapSectionFragment();
            	case 2:
                    return new PostSectionFragment();
                default:
                    return new ListSectionFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "List";
			case 1:
				return "Map";
			case 2:
				return "Post";				
			}
			return null;
        }
    }

    /**
     * A fragment that launches other parts of the demo application.
     */
    public static class ListSectionFragment extends Fragment {

    	private static TextView selection;
    	
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_section_list, container, false);
    		selection = (TextView) rootView.findViewById(R.id.textView1);

    		// TODO: stub
    		Location stubLocation = new Location("");
    		stubLocation.setLongitude(0);
    		stubLocation.setLatitude(59);
    		InkWell.getInstance().onMyLocationChange(stubLocation);
            return rootView;
        }
        
		public static void update(InkList inkList, Location location) {
	    	String output = new String();
	    	output += "Location: " + location.getLatitude() + "," + location.getLongitude() + "\n";
	    	output += "Received inks: " + inkList.size() + "\n";
	    	for(Ink i : inkList) {
	    		// Shorten message, if it is too long.
	    		String messagePreview = i.getMessage().substring(0, i.getMessage().length() > 15 ? 15 : i.getMessage().length());
	    		output += i.getID() + ", " + location.distanceTo(i.getLocation()) + "m, r" + i.getRadius() + "m, " + messagePreview + "\n";	
	    	}    	
	    	selection.setText(output);			
		}
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class MapSectionFragment extends Fragment {

        /**
         * Note that this may be null if the Google Play services APK is not available.
         */
        private static GoogleMap mMap;    	
    	
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_section_map, container, false);
            
            setUpMapIfNeeded();
            
            return rootView;
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
        private static void setUpMapIfNeeded() {
            // Do a null check to confirm that we have not already instantiated the map.
            if (mMap == null) {
                // Try to obtain the map from the SupportMapFragment.
                mMap = ((SupportMapFragment) MainActivity.fragmentManager.findFragmentById(R.id.map))
                        .getMap();
                // Check if we were successful in obtaining the map.
                if (mMap != null) {
                	mMap.setMyLocationEnabled(true);
            		mMap.setIndoorEnabled(true);
            // TODO:
//                	mMap.setOnMyLocationChangeListener(InkWell.getInstance());  
                }
            }
        } 
        
    	public static void update(InkList inkList) {
    		Log.d(MapSectionFragment.class.getName(), "update(..)");	
    		setUpMapIfNeeded();
    		mMap.clear();
    		for(Ink i : inkList) {
    			mMap.addCircle(i.getCircleOptions());
    			mMap.addMarker(i.getMarkerOptions());
    		}
    	}        
        
    }
    
    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class PostSectionFragment extends Fragment {

    	private EditText form_message;
    	private SeekBar form_radius;
    	private Button form_confirm;
    	private TextView form_radius_output;
    	
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_section_post, container, false);
            form_message = (EditText) rootView.findViewById(R.id.editText1);
            form_radius = (SeekBar) rootView.findViewById(R.id.seekBar1);
            form_confirm = (Button) rootView.findViewById(R.id.button1);
            form_radius_output = (TextView) rootView.findViewById(R.id.seekBarProgressOutput);
            
            form_radius.setMax(2000);
            form_radius.setProgress(500);
            form_radius_output.setText(String.format(rootView.getResources().getString(R.string.radius_output), form_radius.getProgress()));
            form_radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
		            form_radius_output.setText(String.format(getResources().getString(R.string.radius_output), progress));					
				}
			});
            
            form_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                	String message = form_message.getText().toString();
                	int radius = form_radius.getProgress();
                	
                	if (message.isEmpty()) {
                    	Toast.makeText(getView().getContext(), "Message field is empty", Toast.LENGTH_SHORT).show();	
                	} else {
	                	
	                	// TODO: stub only            
	            		Location stubLocation = new Location("");
	            		stubLocation.setLongitude(60);
	            		stubLocation.setLatitude(0);
	            		InkWell.getInstance().getServerManager().postInk(message, radius, stubLocation, getView().getContext());
                	}
                }
            });            
                        
            
            return rootView;
        }
    }

	@Override
	public void update(Observable observable, Object data) {
		if (data instanceof UpdateView) {
	    	InkList inkList = ((UpdateView) data).getInkList();
	    	Location location = ((UpdateView) data).getLocation();
	    	ListSectionFragment.update(inkList, location);
	    	MapSectionFragment.update(inkList);
		}
	}    
}
