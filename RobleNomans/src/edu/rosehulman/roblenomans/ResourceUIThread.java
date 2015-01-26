package edu.rosehulman.roblenomans;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.games.Game;

import edu.rosehulman.roblenomans.common.Utils;

public class ResourceUIThread implements Runnable{

	private MainActivity mActivity;
	private Handler mHandler;

	public ResourceUIThread(MainActivity mainActivity, Handler mResourceUIHandler) {
		mActivity= mainActivity;
		mHandler = mResourceUIHandler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		TextView goldTV= (TextView)mActivity.findViewById(R.id.resource_amount_gold);
		ResourceEngine resources= mActivity.mGame.getmResourceEngine();
		
		goldTV.setText(Utils.ConvertBigInt(resources.getmGold()));
//		Log.d("ResourceUI Update", "Gold: "+ Utils.ConvertBigInt(resources.getmGold()));
		mHandler.postDelayed(this, 1000);	
	}

}
