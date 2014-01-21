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

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

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
/*			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;*/        	
        }
    }

    /**
     * A fragment that launches other parts of the demo application.
     */
    public static class ListSectionFragment extends Fragment implements Observer {

    	private InkWell inkWell;
    	private TextView selection;
    	
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_section_list, container, false);
    		selection = (TextView) rootView.findViewById(R.id.textView1);

            setUp();
    		
    		Location stubLocation = new Location("");
    		stubLocation.setLongitude(60);
    		stubLocation.setLatitude(0);
    		inkWell.onMyLocationChange(stubLocation);
            return rootView;
        }

        private void setUp() {
       		inkWell = InkWell.getInstance();
       		inkWell.deleteObservers(); // TODO: just workaround to fix bug
       		inkWell.addObserver(this);	// this on a static class
        }        
        
		@Override
		public void update(Observable observable, Object data) {
			if (data instanceof UpdateView) {
		    	InkList inkList = ((UpdateView) data).getInkList();
		    	Location location = ((UpdateView) data).getLocation();
		    	
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
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class MapSectionFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_section_map, container, false);
            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                    getString(R.string.dummy_section_text));
            
//    		Location stubLocation = new Location("");
//    		stubLocation.setLongitude(60);
//    		stubLocation.setLatitude(0);
//          InkWell.getInstance().getServerManager().postInk(stubLocation, "hi", null);
            
            return rootView;
        }
    }
    
    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class PostSectionFragment extends Fragment {

    	private EditText form_message;
    	private SeekBar form_radius;
    	private Button form_confirm;
    	
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_section_post, container, false);
            form_message = (EditText) rootView.findViewById(R.id.editText1);
            form_radius = (SeekBar) rootView.findViewById(R.id.seekBar1);
            form_confirm = (Button) rootView.findViewById(R.id.button1);
            
            form_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                	String message = form_message.getText().toString();
                	int radius = form_radius.getProgress();
                	Toast.makeText(getView().getContext(), "r:" + radius + "m:" + message, Toast.LENGTH_LONG).show();
                }
            });            
                        
            
            return rootView;
        }
    }    
}
