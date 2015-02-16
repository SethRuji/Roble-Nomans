package edu.rosehulman.roblenomans.Activities;


import java.util.ArrayList;

import android.R.string;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import edu.rosehulman.roblenomans.Barracks;
import edu.rosehulman.roblenomans.Building;
import edu.rosehulman.roblenomans.Forester;
import edu.rosehulman.roblenomans.GameState;
import edu.rosehulman.roblenomans.IronMine;
import edu.rosehulman.roblenomans.LocationTrackerService;
import edu.rosehulman.roblenomans.NavigationDrawerFragment;
import edu.rosehulman.roblenomans.R;
import edu.rosehulman.roblenomans.ResourceBarFragment;
import edu.rosehulman.roblenomans.ResourceUIThread;
import edu.rosehulman.roblenomans.ServerManager;
import edu.rosehulman.roblenomans.contentfrags.MainResourceFragment;
import edu.rosehulman.roblenomans.contentfrags.MainSettingFragment;
import edu.rosehulman.roblenomans.contentfrags.MainUnitsFragment;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnMapReadyCallback, OnMyLocationChangeListener{
	
	public static final String BUILDINGS_LIST_KEY = "BuildingsList";
	public static final String UNITS_LIST_KEY = "UnitsList";
	
	private MapFragment mMapFragment;
	private String[] mBuildingNames;
	private GoogleMap mMap;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

	private ResourceBarFragment mResourceFrag;

	public GameState mGame;

	private Handler mResourceUIHandler;
	
	private Location mLastLocation;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        
        mMapFragment= new MapFragment();
        mResourceFrag= new ResourceBarFragment();
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));        
        
        FragmentManager fragMan= getFragmentManager();
        FragmentTransaction ft= fragMan.beginTransaction();                                                   
        
        ft.add(R.id.resource_bar, mResourceFrag);        
        ft.add(R.id.main_content_container, mMapFragment);
        
        ft.commit(); 
        
        mResourceUIHandler = new Handler();
        mResourceUIHandler.postDelayed(new ResourceUIThread(this, mResourceUIHandler), 1000);    
        
		mGame= new GameState(savedInstanceState);
        		
        
        mBuildingNames = getResources().getStringArray(R.array.buildingNames);
        
        mMapFragment.getMapAsync(this);
        
//        Intent locationIntent = new Intent(this, LocationTrackerService.class);
//        startService(locationIntent);
    }
    
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    super.onSaveInstanceState(savedInstanceState);
	    
		savedInstanceState.putParcelableArrayList(BUILDINGS_LIST_KEY, mGame.getBuildings());
	}


    @Override
    public void onNavigationDrawerItemSelected(int position) {
    	Fragment mainContentFrag= PlaceholderFragment.newInstance(position+1);        	
    	
    	switch(position){
    	case 0://buildings
    		Toast.makeText(MainActivity.this, "buildings", Toast.LENGTH_SHORT).show();
    		if(mMapFragment!=null){
    			mainContentFrag=mMapFragment;
    			mMapFragment.getMapAsync(this);
    		}
    		break;    		
    	case 1://attack
    		Toast.makeText(MainActivity.this, "attack", Toast.LENGTH_SHORT).show();    		
    		break;	
    	case 2://resources
    		Toast.makeText(MainActivity.this, "resources", Toast.LENGTH_SHORT).show();
    		mainContentFrag= new MainResourceFragment();
    		break;
    	case 3://messages
    		break;
    	case 4://friends
    		break;
    	case 5://settings
    		mainContentFrag= new MainSettingFragment();
    		break;
    	case 6://units
    		mainContentFrag= new MainUnitsFragment();
    		break;
    	}    	
    	
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_content_container, mainContentFrag)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.raw_buildings);
                break;
            case 2:
            	mTitle = getString(R.string.raw_attack);
                break;
            case 3:
            	mTitle = getString(R.string.raw_resources);
                break;
            case 4:
            	mTitle = getString(R.string.raw_messages);
                break;
            case 5:
            	mTitle = getString(R.string.raw_friends);
                break;
            case 6:
            	mTitle = getString(R.string.raw_settings);
                break;
            case 7:
            	mTitle = getString(R.string.raw_units);
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
		setUpMap(map);
		mMap = map;
		ArrayList<Building> buildings= mGame.getBuildings();
		for(Building b : buildings){
			b.setMarker(map.addMarker(b.getMarkerOptions()));
		}
		
		map.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				Building building = mGame.getBuildings().get(marker.getId().charAt(1)-'0');
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
		
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);        
        
        locationManager.addGpsStatusListener(new Listener() {
			
			@Override
			public void onGpsStatusChanged(int event) {
				setUpMap(mMap);
				ServerManager.sendLocation(getLocation());
			}
		});
	}
	

	private void setUpMap(GoogleMap googleMap) {
	    // Enable MyLocation Layer of Google Map
	    googleMap.setMyLocationEnabled(true);

	    // Show the current location in Google Map        
	    googleMap.moveCamera(CameraUpdateFactory.newLatLng(getLocation()));

	    // Zoom in the Google Map
	    googleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
	}
	
	public LatLng getLocation(){
	    // Get LocationManager object from System Service LOCATION_SERVICE
	    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

	    // Create a criteria object to retrieve provider
	    Criteria criteria = new Criteria();

	    // Get the name of the best provider
	    String provider = locationManager.getBestProvider(criteria, true);

	    // Get Current Location
	    Location myLocation = locationManager.getLastKnownLocation(provider);

	    // Get latitude of the current location
	    double latitude = myLocation.getLatitude();

	    // Get longitude of the current location
	    double longitude = myLocation.getLongitude();

	    // Create a LatLng object for the current location
	    LatLng latLng = new LatLng(latitude, longitude);      
	    
	    return latLng;
	}

	protected void displayBuildingDialog(final Building building) {
			DialogFragment df = new DialogFragment() {
				@SuppressLint("InflateParams")
				@Override
				public Dialog onCreateDialog(Bundle savedInstanceState) {
					AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

					String title = getString(building.getBuildingTitleResourceID()) + " Level " + building.getLevel();
					
					b.setTitle(title);
					
					b.setNegativeButton(string.cancel, null);
					
					b.setPositiveButton(string.ok, new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if(building.isUpgradable()){
								long cost[] = building.getCost();
								
								if(mGame.getmResourceEngine().canAfford(cost)){
									mGame.getmResourceEngine().payResources(cost);
									building.upgrade();			
									mGame.updateRates();
								}
							}
						}
					});

					if(building.isUpgradable()){
						long[] cost = building.getCost();
						b.setMessage("Gold: " + cost[0] + "\tWood: " + cost[1] + "\nIron: " + cost[2] + "\tWheat: " + cost[3]);
					}
					else
						b.setMessage("Building is at max level");
					
					return b.create();
				}
			};
			
			df.show(getFragmentManager(), "building");
	}
	
	protected void placeBuilding(final LatLng point) {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
				
				b.setTitle("Choose a building");
				

				b.setItems(getResources().getStringArray(R.array.buildingNames), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Building b;
						switch(which){
							case 0:
								b = new IronMine(point);
								break;
							case 1:
								b = new Forester(point);
								break;
							case 2:
								b = new Barracks(point);
								break;
							default:
								b = new Building();
								break;
						}
						
						mGame.addBuilding(b);
						b.setMarker(mMap.addMarker(b.getMarkerOptions()));						
					}
				});
				
				return b.create();
			}
		};
		
		df.show(getFragmentManager(), "build");
	}

	@Override
	public void onMyLocationChange(Location arg0) {
		if(mLastLocation == null){
			mLastLocation = arg0;
		}
		//Toast.makeText(MainActivity.this, ""+arg0.distanceTo(mLastLocation), Toast.LENGTH_SHORT).show();
		if(arg0.distanceTo(mLastLocation) > 5){
			mLastLocation = arg0;
			setUpMap(mMap);
		}
	}
	

}
