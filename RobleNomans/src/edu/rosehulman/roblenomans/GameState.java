package edu.rosehulman.roblenomans;

import android.app.Activity;
import android.view.View;



public class GameState {
	private ResourceEngine mResourceEngine;

	public GameState(){		
		mResourceEngine = new ResourceEngine();
	}

	public ResourceEngine getmResourceEngine() {		
		return mResourceEngine;
	}	
}
