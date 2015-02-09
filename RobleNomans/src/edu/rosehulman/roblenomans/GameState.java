package edu.rosehulman.roblenomans;


public class GameState {
	private ResourceEngine mResourceEngine;

	public GameState(){		
		mResourceEngine = new ResourceEngine();
	}

	public ResourceEngine getmResourceEngine() {		
		return mResourceEngine;
	}	
}
