package edu.rosehulman.roblenomans;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.appspot.roble_nomans_debiruji.users.Users;
import com.appspot.roble_nomans_debiruji.users.model.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import edu.rosehulman.roblenomans.Activities.MainActivity;
import edu.rosehulman.roblenomans.common.UserServer;

public class LocationBroadcastReceiver extends BroadcastReceiver{

	private static final String TAG_LOC_TRACKER = "SLR";
	private UserServer server;
	public LocationBroadcastReceiver(){
		server= new UserServer();        	
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		Context c= context.getApplicationContext();		
		LatLng loc= MainActivity.getLocation(c);
		Log.d(TAG_LOC_TRACKER, loc.toString());
		User usr= new User();
		usr.setLat(loc.latitude);
		usr.setLon(loc.longitude);
		if(server!=null){
			server.new InsertQuoteTask().execute(usr);
		}
	}

	public void setUpTimer(Context context) {
		Log.d(TAG_LOC_TRACKER, "in setup Timer");
		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, LocationBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+1000, 2000, pi); // Millisec * Second * Minute		
	}
}