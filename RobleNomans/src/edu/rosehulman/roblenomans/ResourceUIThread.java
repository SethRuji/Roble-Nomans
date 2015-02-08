package edu.rosehulman.roblenomans;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.games.Game;

import edu.rosehulman.roblenomans.Activities.MainActivity;
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
		
		ResourceEngine resources= mActivity.mGame.getmResourceEngine();
		
		TextView goldTV= (TextView)mActivity.findViewById(R.id.resource_amount_gold);
		goldTV.setText(Utils.ConvertBigInt(resources.getmGold()));
		
		TextView woodTV= (TextView)mActivity.findViewById(R.id.resource_amount_wood);
		woodTV.setText(Utils.ConvertBigInt(resources.getmWood()));
		
		TextView ironTV= (TextView)mActivity.findViewById(R.id.resource_amount_iron);
		ironTV.setText(Utils.ConvertBigInt(resources.getmIron()));
		
		TextView wheatTV= (TextView)mActivity.findViewById(R.id.resource_amount_grain);
		wheatTV.setText(Utils.ConvertBigInt(resources.getmWheat()));

		mHandler.postDelayed(this, 1000);	
	}

}
