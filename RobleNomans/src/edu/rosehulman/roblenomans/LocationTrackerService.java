package edu.rosehulman.roblenomans;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class LocationTrackerService extends Service{
	protected static final String TAG_LOC_TRACKER = "TAG_LOC_TRACKER";	
		
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
		return super.onStartCommand(intent, flags, startId);
	}

	private class LocationBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			
		}

	}
	public class autostart extends BroadcastReceiver{		

		@Override
		public void onReceive(Context context, Intent intent) {
			Intent locTrackerIntent = new Intent(context, LocationTrackerService.class);
			context.startService(locTrackerIntent);
			Log.d(TAG_LOC_TRACKER, "Started autostart location tracking service");
		}		
	}
}
