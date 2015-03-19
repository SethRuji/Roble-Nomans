package edu.rosehulman.tictactoesr;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	private Button[][] mButtons;
	private TicTacToeGame mGame;
	private Button mNewGameButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mGame= new TicTacToeGame(this);
        
        mButtons= new Button[TicTacToeGame.NUM_ROWS][TicTacToeGame.NUM_COLUMNS];
        for(int r=0;r<TicTacToeGame.NUM_ROWS;r++){
        	for(int c=0;c<TicTacToeGame.NUM_COLUMNS;c++){
        		int buttonId= getResources().getIdentifier("button"+(r+1)+(c+1), "id", getPackageName());
        		mButtons[r][c]= (Button) findViewById(buttonId);
        		mButtons[r][c].setOnClickListener(this);
        	}
        }
        mNewGameButton= (Button)findViewById(R.id.newGameButton);
        mNewGameButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		Log.d("TTT","Button pressed");
		if(v.getId()==R.id.newGameButton){
			mGame.resetGame();
		}else{
			for(int r=0;r<TicTacToeGame.NUM_ROWS;r++){
		    	for(int c=0;c<TicTacToeGame.NUM_COLUMNS;c++){
		    		if(v.getId()== mButtons[r][c].getId()){
		    			Log.d("TTT", "Pressed button ["+r+"]["+c+"]");
		    			mGame.pressedButtonAtLocation(r, c);
		    			
		    		}
		    	}
		    }
		}
		resetView();
	}

	private void resetView() {
		for(int r=0;r<TicTacToeGame.NUM_ROWS;r++){
        	for(int c=0;c<TicTacToeGame.NUM_COLUMNS;c++){
        		mButtons[r][c].setText(mGame.stringForButtonAtLocation(r,c));
        	}
		}
		TextView mGameStateTextView= (TextView)findViewById(R.id.gameStateTextView);
		mGameStateTextView.setText(mGame.stringForGameState());
	}
}
