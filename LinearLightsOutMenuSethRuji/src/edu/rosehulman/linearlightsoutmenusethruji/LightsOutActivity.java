package edu.rosehulman.linearlightsoutmenusethruji;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class LightsOutActivity extends Activity implements OnClickListener {

	private int mNumButtons;
	private LightsOutGame mGame;
	private ArrayList<Button> mButtons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lights_out);
		
		mNumButtons= getIntent().getIntExtra(MainActivity.KEY_NUM_BUTTONS, 7);
		
		mGame= new LightsOutGame(mNumButtons);
		mButtons = new ArrayList<Button>();
		TableRow buttonRow = new TableRow(this);
		for(int i=0; i<this.mNumButtons; i++){
			Button button = new Button(this);
			button.setTag(Integer.valueOf(i));
			this.mButtons.add(button);
			buttonRow.addView(button);
			button.setOnClickListener(this);
		}
		//new game button
		Button ngButton= (Button)findViewById(R.id.button_new_game);
		ngButton.setOnClickListener(this);
		
		TableLayout tableLayout= (TableLayout)findViewById(R.id.table_button);
		tableLayout.addView(buttonRow);
		updateView();
	}

	private void updateView() {
		for(int i=0; i< mNumButtons;i++){
			mButtons.get(i).setText(mGame.getValueAtIndex(i)+"");
			mButtons.get(i).setEnabled(mGame.checkForWin());
		}
		
		//update the game state
		Resources res= getResources();
		String newGameString;
		int nPresses= mGame.getNumPresses();
		boolean isWin = mGame.checkForWin();
		if(isWin){
			if(nPresses==1){
				newGameString = res.getString(R.string.you_won_one_move);
			}else{
				newGameString = res.getString(R.string.you_won_format, nPresses);
			}
		}else{
			if(nPresses==0){
				newGameString= res.getString(R.string.game_one_move);
			}else{
				newGameString= res.getString(R.string.game_format, nPresses);
			}
		}
		TextView gameStateTextView = (TextView)findViewById(R.id.textView1);
		gameStateTextView.setText(newGameString);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.button_new_game){
			mGame= new LightsOutGame(mNumButtons);
		}else{
			//game button
			mGame.pressedButtonAtIndex((Integer)(v.getTag()));
		}
	}
}
