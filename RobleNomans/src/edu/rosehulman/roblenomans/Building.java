package edu.rosehulman.roblenomans;

import java.util.Random;

import android.R.drawable;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.rosehulman.roblenomans.R.string;

public class Building{
	
	private MarkerOptions mMarkerOptions;
	private Marker mMarker;
	private int mID;

	public Building() {
		LatLng NEWARK = new LatLng(0,0);
		mMarkerOptions = new MarkerOptions();
		Random r = new Random();
		int resource = r.nextInt(3);
		switch(resource){
			case 0:
				resource = drawable.star_big_on;
				break;
			case 1:
				resource = drawable.ic_media_play;
				break;
			case 2:
				resource = drawable.ic_menu_rotate;
				break;
			default:
				resource = drawable.arrow_up_float;
				break;
		}
		mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(resource));
		mMarkerOptions.position(NEWARK);
	}
	
	public Building(LatLng position, int id) {
		mMarkerOptions = new MarkerOptions();
		mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(getBuildingDrawableResourceID(id)));
		mMarkerOptions.position(position);
		mID = id;
	}
	
	public MarkerOptions getMarkerOptions(){
		return mMarkerOptions;
	}
	
	public void setMarker(Marker marker){
		mMarker = marker;
	}
	
	public Marker getMarker(){
		return mMarker;
	}
	
	public void setRandomLocation(){
		Random r = new Random();
		LatLng newPos = new LatLng(r.nextFloat()*80-40, r.nextFloat()*80-40);
		mMarker.setPosition(newPos);
	}
	
	public static int getBuildingTitleResourceID(int buildingID){
		switch(buildingID){
			case 0:
				return R.string.iron_mine_name;
			case 1:
				return R.string.forester_name;
			case 2:
				return R.string.barracks_name;
			default:
				Log.d("NR", "Building " + buildingID + " not found.");
				return 0;
		}
	}
	
	public int getBuildingTitleResourceID(){
		switch(mID){
			case 0:
				return R.string.iron_mine_name;
			case 1:
				return R.string.forester_name;
			case 2:
				return R.string.barracks_name;
			default:
				Log.d("NR", "Building " + mID + " not found.");
				return 0;
		}
	}
	
	public static int getBuildingDrawableResourceID(int buildingID){
		switch(buildingID){
			case 0:
				return drawable.star_big_on;
			case 1:
				return drawable.ic_menu_help;
			case 2:
				return drawable.ic_menu_camera;
			default:
				Log.d("NR", "Building " + buildingID + " not found.");
				return 0;
		}
	}
	
	public int getBuildingDrawableResourceID(){
		switch(mID){
			case 0:
				return drawable.star_big_on;
			case 1:
				return drawable.ic_menu_help;
			case 2:
				return drawable.ic_menu_camera;
			default:
				Log.d("NR", "Building " + mID + " not found.");
				return 0;
		}
	}

}
