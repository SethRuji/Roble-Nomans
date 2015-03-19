package edu.rosehulman.photomessage;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationBroadcastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Notification notific= (Notification)intent.getParcelableExtra(MainActivity.KEY_NOTIFICATION);
		int notificId= intent.getIntExtra(MainActivity.KEY_SOON_NOTIFICATION_ID, 0);
		
		NotificationManager manager= (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(notificId, notific);
	}

}
