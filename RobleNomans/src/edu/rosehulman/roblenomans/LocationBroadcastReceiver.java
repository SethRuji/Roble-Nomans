package edu.rosehulman.roblenomans;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import edu.rosehulman.roblenomans.Activities.MainActivity;

public class LocationBroadcastReceiver extends BroadcastReceiver{

	private static final String TAG_LOC_TRACKER = "SLR";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG_LOC_TRACKER, "broadcast received");
		MainActivity c = (MainActivity) context.getApplicationContext();
		LatLng loc= c.getLocation();
		loc.toString();
	}

	public void setUpTimer(Context context) {
		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, LocationBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
//        am.setRepeating(AlarmManager.ELAPSED_REALTIME, System.currentTimeMillis(), AlarmManager.INTERVAL_HOUR, pi); // Millisec * Second * Minute
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10000, pi); // Millisec * Second * Minute
	}
}