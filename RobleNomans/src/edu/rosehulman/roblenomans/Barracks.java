package edu.rosehulman.roblenomans;

import android.os.Parcel;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

public class Barracks extends Building {
	
	public final long[] BASE_INCOME = {0, 0, 0, 0};
	public final int MAX_LEVEL = 4;
	private final long[][] COSTS = new long[][]{{150, 200, 150, 200},
												{300, 400, 300, 400},
												{450, 600, 450, 600},
												{600, 800, 600, 800}};
	
	
	public Barracks(Parcel in) {
		super(in);
	}
	
	public Barracks(LatLng position) {
		mID = BuildingTypeID.Barracks;
		mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.ic_menu_save));
		mMarkerOptions.position(position);
	}
	
	@Override
	public int getBuildingTitleResourceID() {
		return edu.rosehulman.roblenomans.R.string.barracks_name;
	}
	
	@Override
	public long[] getCost() {
		return COSTS[mLevel-1];
	}
	
	@Override
	public boolean isUpgradable() {
		return mLevel < MAX_LEVEL;
	}
	
	@Override
	public long[] getIncome() {
		long[] income = new long[4];
		
		for(int i = 0; i < 4; i++){
			income[i] = BASE_INCOME[i] * mLevel;
		}
		
		return income;
	}
}

