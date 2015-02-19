package edu.rosehulman.roblenomans;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.identity.intents.AddressConstants.Extras;

import edu.rosehulman.roblenomans.Activities.MainActivity;

public class LocationTrackerService extends Service{
	protected static final String TAG_LOC_TRACKER = "SLR";
	private LocationBroadcastReceiver mLocReceiver= new LocationBroadcastReceiver();	
		
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.d(TAG_LOC_TRACKER, "tracker service created");		
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d(TAG_LOC_TRACKER, "tracker service started");
		Bundle extras=intent.getExtras();
		String entityKey= extras.getString(MainActivity.USER_ENTITY_KEY);
		String username= extras.getString(MainActivity.USERNAME_KEY);
		super.onStartCommand(intent, flags, startId);
		mLocReceiver.setUpTimer(LocationTrackerService.this, entityKey, username);
		return 1;
	}	
}
