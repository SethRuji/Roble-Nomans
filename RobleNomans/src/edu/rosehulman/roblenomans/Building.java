package edu.rosehulman.roblenomans;

import java.util.Random;

import android.R.drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Building implements Parcelable{
	
	public enum BuildingTypeID{
		IronMine, Forester, Barracks
	}
	
	protected MarkerOptions mMarkerOptions;
	protected Marker mMarker;
	protected BuildingTypeID mID;
	protected int mLevel;
	
	public static final Parcelable.Creator<Building> CREATOR = new Parcelable.Creator<Building>() {
		public Building createFromParcel(Parcel in) {
			return new Building(in);
		}
		public Building[] newArray(int size) {
			return new Building[size];
		}
	};
	
	public Building(){
		mMarkerOptions = new MarkerOptions();
		mLevel = 1;
	}
	
    public Building(Parcel in) {
    	mMarkerOptions = in.readParcelable(null);    	
    	mMarker = (Marker) in.readValue(null);
    	mID = (BuildingTypeID) in.readSerializable();
    	mLevel = in.readInt();
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
		Log.d("NR", "Building " + mID + " not found.");
		return 0;
	}
	
	public static int getBuildingDrawableResourceID(BuildingTypeID buildingID){
		switch(buildingID){
			case IronMine:
				return drawable.star_big_on;
			case Forester:
				return drawable.ic_menu_help;
			case Barracks:
				return drawable.ic_menu_camera;
			default:
				Log.d("NR", "Building " + buildingID + " not found.");
				return 0;
		}
	}
	
	public int getBuildingDrawableResourceID(){
		Log.d("NR", "Building " + mID + " not found.");
		return 0;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mMarkerOptions, flags);
		dest.writeValue(mMarker);
		dest.writeSerializable(mID);
		dest.writeInt(mLevel);
	}
	
	public long[] getCost(){
		return new long[4];
	}

	public void upgrade() {
		mLevel++;
	}

	public boolean isUpgradable() {
		return false;
	}

	public int getLevel() {
		return mLevel;
	}
	
	public long[] getIncome(){
		return new long[4];
	}

}
