package edu.rosehulman.roblenomans;

import android.os.Parcel;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

public class IronMine extends Building {
	
	public final long[] BASE_INCOME = {0, 0, 1, 0};
	public final int MAX_LEVEL = 4;
	private final long[][] COSTS = new long[][]{{10, 10, 10, 0},
												{20, 20, 20, 0},
												{30, 30, 30, 0},
												{40, 40, 40, 0}};
	
	
	public IronMine(Parcel in) {
		super(in);
	}
	
	public IronMine(LatLng position) {
		mID = BuildingTypeID.IronMine;
		mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_on));
		mMarkerOptions.position(position);
	}
	
	@Override
	public int getBuildingTitleResourceID() {
		return edu.rosehulman.roblenomans.R.string.iron_mine_name;
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

