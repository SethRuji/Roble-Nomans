package edu.rosehulman.linlightsoutsr;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{

	static int NUM_BUTTONS=7;
	Button mButtons[];
	LightsOutGame mGame;
	TextView gameTextView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mButtons= new Button[NUM_BUTTONS];
      //initialize buttons and setup onclick listener
        for(int i=0;i<NUM_BUTTONS;i++){
        	int buttonId= getResources().getIdentifier("button"+i, "id", getPackageName());
        	mButtons[i]= (Button)findViewById(buttonId);
        	mButtons[i].setOnClickListener(this);
        }
        Button ng= (Button)findViewById(R.id.newGameButton);
        ng.setOnClickListener(this);
        resetGame();
        //toggleAllButtons();
    }

	private void updateButtons() {
		for(int i=0;i<NUM_BUTTONS;i++){
			int bVal= mGame.getValueAtIndex(i);
			mButtons[i].setText(bVal+"");	
		}
	}
	
	private void resetGame(){
		mGame= new LightsOutGame();
		gameTextView= (TextView)findViewById(R.id.textView1);
		gameTextView.setText(R.string.gameText);
		
		updateButtons();
		for(Button b : mButtons){
			b.setEnabled(true);
		}
	}

	@Override
	public void onClick(View v) {
		Button ng= (Button)findViewById(R.id.newGameButton);
		Log.d("RUJI", "ngButtonid: "+ng.getId() +" vId= "+v.getId());
		if(v.getId()== ng.getId()){
			resetGame();
		}else{	
			for(int i=0;i<NUM_BUTTONS;i++){
				if(v.getId()== mButtons[i].getId()){
					boolean wonGame= mGame.pressedButtonAtIndex(i);
					if(wonGame){
						gameTextView.setText(R.string.winText);
						disableButtons();
					}
				}
			}
		}
		updateButtons();
	}

	private void disableButtons() {
		for(Button b : mButtons){
			b.setEnabled(false);
		}
	}
}
