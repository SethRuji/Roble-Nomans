package edu.rosehulman.roblenomans;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcelable;
import edu.rosehulman.roblenomans.Activities.MainActivity;
import edu.rosehulman.roblenomans.units.Unit;
import edu.rosehulman.roblenomans.units.UnitAbstract;

public class GameState {	

	private ArrayList<UnitAbstract> mUnits;
	private ArrayList<Building> mBuildings;

	
	private ResourceEngine mResourceEngine;

	public GameState(Bundle savedInstanceState){				
		mResourceEngine = new ResourceEngine();
		
		if(savedInstanceState != null && savedInstanceState.containsKey(MainActivity.BUILDINGS_LIST_KEY)){
			mBuildings= savedInstanceState.getParcelableArrayList(MainActivity.BUILDINGS_LIST_KEY);
		} else {
			mBuildings = new ArrayList<Building>();
		}
		if(savedInstanceState != null && savedInstanceState.containsKey(MainActivity.UNITS_LIST_KEY)){
			mUnits= savedInstanceState.getParcelableArrayList(MainActivity.UNITS_LIST_KEY);
		} else {
			mUnits = new ArrayList<UnitAbstract>();
		}		
	}

	public ResourceEngine getmResourceEngine() {		
		return mResourceEngine;
	}	
		
	
	public void addUnit(Unit unit){
		mUnits.add((UnitAbstract) unit);
		updateRates();
	}

	public ArrayList<Building> getBuildings() {
		// TODO Auto-generated method stub
		return mBuildings;
	}

	public void addBuilding(Building b) {
		mBuildings.add(b);
		updateRates();
	}
	
	public void updateRates() {
		long goldRate= mResourceEngine.getGoldRate();
		long ironRate=  mResourceEngine.getIronRate();
		long wheatRate= mResourceEngine.getWheatRate();
		long woodRate= mResourceEngine.getWoodRate();
		
		for (Building b : mBuildings){
			long[] rate = b.getIncome();
			
			goldRate+= rate[0];
			ironRate+= rate[2];
			wheatRate+= rate[3];
			woodRate+= rate[1];
		}
		
		for (Unit u : mUnits){
			long[] rate = u.getIncome();
			
			goldRate+= rate[0];
			woodRate+= rate[1];
			ironRate+= rate[2];
			wheatRate+= rate[3];
		}
		
		mResourceEngine.setResourceRates(new long[]{goldRate, woodRate, ironRate, wheatRate});
	}
}
