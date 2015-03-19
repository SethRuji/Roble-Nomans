package edu.rosehulman.linearlightsoutmenusethruji;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

    public static final String KEY_NUM_BUTTONS = "KEY_NUM_BUTTONS";
	private static final int REQUEST_CODE_CHANGE_BUTTON = 1;
	private static final String PREFS = "PREFS";
	private static final String LOM = "SRLOM";
	private int mNumButtons=7;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button playBtn= (Button)findViewById(R.id.button_play);
        Button changeBtn= (Button)findViewById(R.id.button_change);
        Button aboutBtn= (Button)findViewById(R.id.button_about);
        Button exitBtn= (Button)findViewById(R.id.button_exit);
        
        
//        SharedPreferences prefs= getSharedPreferences(PREFS, MODE_PRIVATE);
//        mNumButtons= prefs.getInt(KEY_NUM_BUTTONS, 7);
        if(savedInstanceState !=null){
        	mNumButtons = savedInstanceState.getInt(KEY_NUM_BUTTONS);
        }
        playBtn.setText(getString(R.string.play_button_format, mNumButtons));
        
        playBtn.setOnClickListener(this);
        changeBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
        aboutBtn.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		Log.d("LOM", "Button clicked: ");
		switch(v.getId()){
		case R.id.button_play:
			Log.d("LOM", "play button");
			Toast.makeText(this, "play with "+ mNumButtons+" buttons", Toast.LENGTH_SHORT).show();
			Intent playIntent = new Intent(this, LightsOutActivity.class);
			playIntent.putExtra(KEY_NUM_BUTTONS, mNumButtons);
			startActivity(playIntent);
			break;
		case R.id.button_change:
			Log.d("LOM", "change button");
			Intent changeIntent = new Intent(this, ChangeNumButtons.class);
			changeIntent.putExtra(KEY_NUM_BUTTONS, mNumButtons);
			this.startActivityForResult(changeIntent, REQUEST_CODE_CHANGE_BUTTON);
			break;
		case R.id.button_about:
			Log.d("LOM", "about button");
			Intent aboutIntent = new Intent(this, Lights_Out_About.class);
			this.startActivity(aboutIntent);
			break;
		case R.id.button_exit:
			Log.d("LOM", "exit button");
			finish();
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// See which child activity is calling us back. 
	    switch (requestCode) {
	        case REQUEST_CODE_CHANGE_BUTTON:
	            if (resultCode == Activity.RESULT_OK){
	                Log.d(LOM, "Result ok!");
	            } 
	            else {
	                Log.d(LOM, "Result not okay.  User hit back without a button");
	            }
	            break;
	        default:
	            Log.d(LOM, "Unknown result code");
	            break;
	    }

	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_NUM_BUTTONS, mNumButtons);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		SharedPreferences prefs= getSharedPreferences(PREFS, MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(KEY_NUM_BUTTONS, mNumButtons);
		editor.commit();		
	}
}
