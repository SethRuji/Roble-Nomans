package edu.rosehulman.roblenomans;

import java.util.Random;

import android.R.drawable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Building{
	
	private MarkerOptions mMarkerOptions;
	private Marker mMarker;

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
}
