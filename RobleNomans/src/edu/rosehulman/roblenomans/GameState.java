package edu.rosehulman.roblenomans;

import java.util.ArrayList;

import edu.rosehulman.roblenomans.units.Unit;

import android.app.Activity;
import android.view.View;


public class GameState {
	private ArrayList<Unit> mUnits;
	
	private ResourceEngine mResourceEngine;

	public GameState(){		
		mUnits= new ArrayList<Unit>();
		mResourceEngine = new ResourceEngine();
	}

	public ResourceEngine getmResourceEngine() {		
		return mResourceEngine;
	}	
	
	public void addUnit(Unit unit){
		mUnits.add(unit);
	}
}
