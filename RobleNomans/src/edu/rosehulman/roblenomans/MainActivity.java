package edu.rosehulman.roblenomans;

import java.util.ArrayList;

import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnMapReadyCallback{
	
	private MapFragment mMapFragment;
	private ArrayList<Building> mBuildings;
	private String[] mBuildingNames;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        
        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
        
        mBuildings = new ArrayList<Building>();
        
        mBuildingNames = getResources().getStringArray(R.array.buildingNames);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

	@Override
	public void onMapReady(GoogleMap map) {
		for(Building b : mBuildings){
			b.setMarker(map.addMarker(b.getMarkerOptions()));
			b.setRandomLocation();
		}
		
		map.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				Building building = mBuildings.get(marker.getId().charAt(1)-'0');
				displayBuildingDialog(building);
				
				return true;
			}
		});
		
		map.setOnMapLongClickListener(new OnMapLongClickListener() {
			
			@Override
			public void onMapLongClick(LatLng point) {
				placeBuilding(point);
			}
		});
	}

	protected void displayBuildingDialog(final Building building) {
			DialogFragment df = new DialogFragment() {
				@SuppressLint("InflateParams")
				@Override
				public Dialog onCreateDialog(Bundle savedInstanceState) {
					AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

					String title = "Building " + building.getMarker().getId();
					
					b.setTitle(title);
					
					b.setNegativeButton(string.cancel, null);
					
					b.setPositiveButton(string.ok, null);
					
					return b.create();
				}
			};
			
			df.show(getFragmentManager(), "building");
	}
	
	protected void placeBuilding(LatLng point) {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
				
				b.setTitle("Choose a building");
				

				b.setItems(getResources().getStringArray(R.array.buildingNames), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getBaseContext(), mBuildingNames[which], Toast.LENGTH_SHORT).show();
					}
				});
				
				return b.create();
			}
		};
		
		df.show(getFragmentManager(), "build");
	}
}
