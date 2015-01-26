package edu.rosehulman.roblenomans;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class ResourceEngine implements Runnable {	
	public long getmGold() {
		return mGold;
	}

	public long getmIron() {
		return mIron;
	}

	public long getmWood() {
		return mWood;
	}


	public long getmWheat() {
		return mWheat;
	}


	public long getWoodRate() {
		return woodRate;
	}


	public long getWheatRate() {
		return wheatRate;
	}


	public long getGoldRate() {
		return goldRate;
	}


	public long getIronRate() {
		return ironRate;
	}	

	private static final String RESOURCE_TAG = "RN Resource";
	public static final int ONE_SECOND=1000;
	
	private long mGold= 100000;
	private long mIron= 3;
	private long mWood= 10;
	private long mWheat= 10;
	
	private long goldRate= 100;
	private long ironRate= 0;
	private long woodRate= 0;
	private long wheatRate= 0;
	private Handler h;
	
	private TextView mIronTV;
	private TextView mGoldTV;
	private TextView mWheatTV;
	private TextView mWoodTV;
	
	public ResourceEngine(){
		h = new Handler();
		h.postDelayed(this,  ONE_SECOND);
	}
	
	public ResourceEngine(View view){
		mGoldTV= (TextView)view.findViewById(R.id.resource_amount_gold);
		mIronTV= (TextView)view.findViewById(R.id.resource_amount_iron);
		mWheatTV= (TextView)view.findViewById(R.id.resource_amount_grain);
		mWoodTV= (TextView)view.findViewById(R.id.resource_amount_wood);
	}
		
	@Override
	public void run() {		
		mGold+=getGoldRate();
		mIron+=getIronRate();
		mWheat+=getWheatRate();
		mWood+=getWoodRate();			
		
//		updateViewFragment();
		
		h.postDelayed(this, ONE_SECOND);
	}

	private void updateViewFragment() {
		mGoldTV.setText(mGold+"");
		mIronTV.setText(mIron+"");
		mWoodTV.setText(mWood+"");
		mWheatTV.setText(mWheat+"");
	}
}
