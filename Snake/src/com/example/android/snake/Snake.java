/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.snake;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Snake: a simple game that everyone can enjoy.
 * 
 * This is an implementation of the classic Game "Snake", in which you control a serpent roaming
 * around the garden looking for apples. Be careful, though, because when you catch one, not only
 * will you become longer, but you'll move faster. Running into yourself or the walls will end the
 * game.
 * 
 */
public class Snake extends Activity {

    /**
     * Constants for desired direction of moving the snake
     */
    public static int MOVE_LEFT = 0;
    public static int MOVE_UP = 1;
    public static int MOVE_DOWN = 2;
    public static int MOVE_RIGHT = 3;

    private static String ICICLE_KEY = "snake-view";

    private SnakeView mSnakeView;
	private GestureDetector mDetector;
	
    public static final String DEBUG_TAG = "SNAKESR"; 


    /**
     * Called when Activity is first created. Turns off the title bar, sets up the content views,
     * and fires up the SnakeView.
     * 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.snake_layout);

        mSnakeView = (SnakeView) findViewById(R.id.snake);
        mSnakeView.setDependentViews((TextView) findViewById(R.id.text),
                findViewById(R.id.arrowContainer), findViewById(R.id.background));
        mDetector = new GestureDetector(this, new MyGestureListener());


        if (savedInstanceState == null) {
            // We were just launched -- set up a new game
            mSnakeView.setMode(SnakeView.READY);
        } else {
            // We are being restored
            Bundle map = savedInstanceState.getBundle(ICICLE_KEY);
            if (map != null) {
                mSnakeView.restoreState(map);
            } else {
                mSnakeView.setMode(SnakeView.PAUSE);
            }
        }
//        mSnakeView.setOnTouchListener(new OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (mSnakeView.getGameState() == SnakeView.RUNNING) {
//                    // Normalize x,y between 0 and 1
//                    float x = event.getX() / v.getWidth();
//                    float y = event.getY() / v.getHeight();
//
//                    // Direction will be [0,1,2,3] depending on quadrant
//                    int direction = 0;
//                    direction = (x > y) ? 1 : 0;
//                    direction |= (x > 1 - y) ? 2 : 0;
//
//                    // Direction is same as the quadrant which was clicked
//                    mSnakeView.moveSnake(direction);
//
//                } else {
//                    // If the game is not running then on touching any part of the screen
//                    // we start the game by sending MOVE_UP signal to SnakeView
//                    mSnakeView.moveSnake(MOVE_UP);
//                }
//                return false;
//            }
//        });
    }


    
    @Override
    protected void onPause() {
        super.onPause();
        // Pause the game along with the activity
        mSnakeView.setMode(SnakeView.PAUSE);
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Store the game state
        outState.putBundle(ICICLE_KEY, mSnakeView.saveState());
    }

    /**
     * Handles key events in the game. Update the direction our snake is traveling based on the
     * DPAD.
     *
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                mSnakeView.moveSnake(MOVE_UP);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                mSnakeView.moveSnake(MOVE_RIGHT);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                mSnakeView.moveSnake(MOVE_DOWN);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                mSnakeView.moveSnake(MOVE_LEFT);
                break;
        }

        return super.onKeyDown(keyCode, msg);
    }

    /**
     * handles initializing the options menu
     */
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {    	
    	if(mSnakeView.getGameState()== SnakeView.RUNNING){
    		this.mSnakeView.setMode(SnakeView.PAUSE);
    	}
    	int nextDir= mSnakeView.getNextDirection();
		switch(item.getItemId()){
		case R.id.resume:
			if(mSnakeView.getGameState()== SnakeView.PAUSE){
	    		this.mSnakeView.setMode(SnakeView.RUNNING);
	    	}
			break;
		case R.id.left_turn:
			mSnakeView.setNextDirection((nextDir+3)%4);
			this.mSnakeView.setMode(SnakeView.RUNNING);
			break;
		case R.id.right_turn:			
			mSnakeView.setNextDirection((nextDir+1)%4);
			this.mSnakeView.setMode(SnakeView.RUNNING);
			break;
		case R.id.slow:
			mSnakeView.setMoveDelay(600);
			break;
		case R.id.medium:
			mSnakeView.setMoveDelay(300);
			break;
		case R.id.fast:			
			mSnakeView.setMoveDelay(100);
			break;
		case R.id.settings:			
			Log.d(this.DEBUG_TAG, "setting button clicked");
			break;
		}    	
    	return true;
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	if(mSnakeView.getGameState()== SnakeView.RUNNING){
    		this.mSnakeView.setMode(SnakeView.PAUSE);
    	}
    	return true;
    }
    
    @Override
    public void onOptionsMenuClosed(Menu menu) {
    	this.mSnakeView.setMode(SnakeView.RUNNING);
    }
    
    @Override 
    public boolean onTouchEvent(MotionEvent event){
//    	Log.d(Snake.DEBUG_TAG, "onTouchEvent");
    	if(mSnakeView.getGameState() != mSnakeView.RUNNING){
	        // If the game is not running then on touching any part of the screen
	        // we start the game by sending MOVE_UP signal to SnakeView
	        mSnakeView.moveSnake(MOVE_UP);
    	}
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    
    
    public class MyGestureListener extends SimpleOnGestureListener implements
	OnGestureListener {
        private static final float VELOCITY_THRESHOLD = 400;

		@Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, 
                float velocityX, float velocityY) {
            Log.d(Snake.DEBUG_TAG, "Flung: X:"+ velocityX +" Y:"+velocityY );
            //Toast.makeText(Snake., "Flung: X:"+ velocityX +" Y:"+velocityY , Toast.LENGTH_SHORT).show();
            if(Math.abs(velocityX)>= VELOCITY_THRESHOLD || Math.abs(velocityY)>= VELOCITY_THRESHOLD){
	            if(Math.abs(velocityX) >= Math.abs(velocityY)){
	            	//moving horizontally
	            	if(velocityX>0){
	            		mSnakeView.moveSnake(MOVE_RIGHT);
	            	}else{
	            		mSnakeView.moveSnake(MOVE_LEFT);
	            	}
	            }else{
	            	//moving vertically
	            	if(velocityY>0){
	            		mSnakeView.moveSnake(MOVE_DOWN);
	            	}else{
	            		mSnakeView.moveSnake(MOVE_UP);
	            	}
	            }
            }
            return true;
        }
    }

}
