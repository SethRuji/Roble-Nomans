package edu.rosehulman.exam1sethruji;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	private int clickCount=0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button reset= (Button)findViewById(R.id.button_reset);
        Button redBut= (Button)findViewById(R.id.button_red);
        Button blueBut= (Button)findViewById(R.id.button_blue);
        Button greenBut= (Button)findViewById(R.id.button_green);
        Button yellowBut= (Button)findViewById(R.id.button_yellow);
        
        reset.setOnClickListener(this);
        redBut.setOnClickListener(this);
        blueBut.setOnClickListener(this);
        greenBut.setOnClickListener(this);
        yellowBut.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View v) {
    	TextView tv= (TextView)findViewById(R.id.textview_game);
    	clickCount++;
    	if(clickCount==1){
    		tv.setText(R.string.pressed);
    	}else if(clickCount>10){
    		tv.setTextSize(14);
    	}
    	
    	String gameText=(String) tv.getText();
    	Log.d("EXAM1", gameText);
		switch(v.getId()){
		case R.id.button_blue:	
			gameText= gameText.concat(" B");
			tv.setText(gameText);
			break;
		case R.id.button_green:
			gameText= gameText.concat(" G");
			tv.setText(gameText);
			break;
		case R.id.button_yellow:
			gameText= gameText.concat(" Y");
			tv.setText(gameText);
			break;
		case R.id.button_red:
			gameText= gameText.concat(" R");
			tv.setText(gameText);
			break;
		case R.id.button_reset:
			Log.d("EXAM1", "reset clicked");
			clickCount=0;
			tv.setText(R.string.record);
			tv.setTextSize(20);
			break;
		}
	}
}
