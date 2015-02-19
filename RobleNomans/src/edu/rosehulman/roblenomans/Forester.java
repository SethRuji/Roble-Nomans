package edu.rosehulman.roblenomans;

import android.os.Parcel;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

public class Forester extends Building {
	
	public final long[] BASE_INCOME = {0, 1, 0, 1};
	public final int MAX_LEVEL = 4;
	private final long[][] COSTS = new long[][]{{10, 20, 10, 20},
												{20, 40, 20, 40},
												{30, 60, 30, 60},
												{40, 80, 40, 80}};
	
	
	public Forester(Parcel in) {
		super(in);
	}
	
	public Forester(LatLng position) {
		mID = BuildingTypeID.Forester;
		mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_off));
		mMarkerOptions.position(position);
	}
	
	@Override
	public int getBuildingTitleResourceID() {
		return edu.rosehulman.roblenomans.R.string.forester_name;
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

