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

package no.invisibleink.app;


import java.util.Date;

import no.invisibleink.app.controller.SessionManager;
import no.invisibleink.app.controller.location.LocationManager;
import no.invisibleink.app.controller.location.NoLocationException;
import no.invisibleink.app.controller.server_comm.ServerManager;
import no.invisibleink.app.model.DatabaseHelper;
import no.invisibleink.app.model.InkList;
import no.invisibleink.app.view.section.ListViewFragment;
import no.invisibleink.app.view.section.MapViewFragment;
import no.invisibleink.app.view.section.PostViewFragment;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.MapsInitializer;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener, LocationListener, PostViewFragment.OnPostSectionFragmentListener, ListViewFragment.OnListSectionFragmentListener {

	private static final String TAG = "MainActivity";
    
    static FragmentManager fragmentManager;
    
    private ListViewFragment listSectionFragment;
    private MapViewFragment mapSectionFragment;
    private PostViewFragment postSectionFragment;
    
    /* -------------------------------- Swipe view with taps ---------------- */
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
    /* -------------------------------- End Swipe view with taps ---------------- */

    /* -------------------------------- Stuff ---------------- */
    /** Location manager */
    private LocationManager locationManager;
    /** Server manager */
    private ServerManager serverManager;
	/** List with all local inks */
	private InkList inkList;
    
	private DatabaseHelper db;
	
	final Context context = this;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /* -------------------------------- Init ---------------- */
        serverManager = new ServerManager();
        inkList = new InkList();
        locationManager = new LocationManager(this);
        locationManager.onCreate();
        db = new DatabaseHelper(getApplicationContext());
        
        /* -------------------------------- Swipe view with taps ---------------- */
        fragmentManager = getSupportFragmentManager();
        
        listSectionFragment = new ListViewFragment();
        mapSectionFragment = new MapViewFragment();
        postSectionFragment = new PostViewFragment();
        
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
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
		/*
		 * Set the number of pages that should be retained to either side of the
		 * current page in the view hierarchy in an idle state. Pages beyond
		 * this limit will be recreated from the adapter when needed.
		 */
        mViewPager.setOffscreenPageLimit(3);

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
        
        
        
        
        try {
			MapsInitializer.initialize(getApplicationContext());
		} catch (GooglePlayServicesNotAvailableException e) {
			Log.e(TAG, "Google play services is not available.");
		}
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
            case R.id.menu_logout:
           	 	SessionManager mg = new SessionManager(getApplicationContext());	
           	 	mg.logout();
           	 	finish();
               return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
     * Called when the Activity is no longer visible at all.
     * Stop updates and disconnect.
     */
    @Override
    public void onStop() {
    	locationManager.onStop();
        super.onStop();
    }
    /*
     * Called when the Activity is going into the background.
     * Parts of the UI may be visible, but the Activity is inactive.
     */
    @Override
    public void onPause() {
    	locationManager.onPause();
        super.onPause();
    }   
    /*
     * Called when the Activity is restarted, even before it becomes visible.
     */
    @Override
    public void onStart() {
        super.onStart();
        locationManager.onStart();
    }
    /*
     * Called when the system detects that this Activity is now visible.
     */   
    @Override
    public void onResume() {
    	super.onResume();
    	locationManager.onResume();
    }
        
    
    /* -------------------------------- Swipe view with taps ---------------- */
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
    public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /*
         * (non-Javadoc)
         * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
         */
        @Override
        public Fragment getItem(int i) {
            switch (i) {
            	case 1:
                    return mapSectionFragment;
            	case 2:
                    return postSectionFragment;
                default:
                    return listSectionFragment;
            }
        }

        /*
         * (non-Javadoc)
         * @see android.support.v4.view.PagerAdapter#getCount()
         */
        @Override
        public int getCount() {
            return 3;
        }

        /*
         * (non-Javadoc)
         * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
         */
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
	
	public void onReceivedInkList(InkList inkList, Location oldLocation) {
		if (inkList != null) {
			Log.d(TAG, "received: " + inkList.size() + " inks");

			this.inkList.clear();
			this.inkList.addAll(inkList);
			
			Location location;
			try {
				if (oldLocation == null) {
					location = locationManager.getLocation();
				} else {
					location = oldLocation;
				}
				Log.w(TAG, location.toString());
				inkList.updateVisibility(location);
		    	listSectionFragment.update(inkList, location);
		    	mapSectionFragment.update(inkList);		
			} catch (NoLocationException e) {
				Log.w(TAG, "onReceivedInkList: no location");				
			}
		} else {
			Log.d(TAG, "onReceivedInkList: Receive null inkList");
			
			// TODO: comment
	        InkList tmpInkList = db.getInkList();
	        //for(Ink i : tmpInkList) {
	        //	Log.i(LOG, i.toString());
	        //}
	        if (!tmpInkList.isEmpty()) {
	        	Log.i(TAG, "recover " + tmpInkList.size() + " inks form db");
	        	try {
					onReceivedInkList(tmpInkList, locationManager.getSavedLocation());
				} catch (NoLocationException e) {
		        	Log.d(TAG, "recover " + e.getMessage());
				}
	        }

	        
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.android.gms.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	@Override
	public void onLocationChanged(Location location) {
		serverManager.requestIfNecessary(this, location);
	}

	/*
	 * (non-Javadoc)
	 * @see no.invisibleink.listener.OnPostSectionFragmentListener#onPostInkForm(java.lang.String, int, android.content.Context)
	 */
	@Override
	public void onPostInkForm(String message, int radius, Date expires) {
		try {
			Location location = locationManager.getLocation();
			serverManager.postInk(message, radius, expires, location, this);
		} catch (NoLocationException e) {
        	Toast.makeText(this, "No location. Turn on GPS.", Toast.LENGTH_SHORT).show();	            			
		}
	}

	/*
	 * (non-Javadoc)
	 * @see no.invisibleink.listener.OnListSectionFragmentListener#onRequestInks()
	 */
	@Override
	public void onRequestInks() {
		try {
			Location location = locationManager.getLocation();
			serverManager.request(this, location);
		} catch (NoLocationException e) {
			Log.w(TAG, "onRequestInks: " + e.getMessage());
		}		
	}

	/*
	 * (non-Javadoc)
	 * @see no.invisibleink.listener.OnListSectionFragmentListener#doLocationUpdates(boolean)
	 */
	@Override
	public void doLocationUpdates(boolean yes) {
		if (yes) {
			locationManager.startUpdates();
		} else {
			locationManager.stopUpdates();
		}
		
	}


	public void onReceiveInks(InkList inkList) {
		// TODO Auto-generated method stub
		
	}
    
}
