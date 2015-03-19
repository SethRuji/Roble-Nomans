package edu.rosehulman.basketballjerseysr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class JerseyDisplayActivity extends Activity {
	public static final String JDA ="JDA";
	private ImageView mJerseyImageView;
	private TextView mNameTextView;
	private TextView mNumberTextView;
	private String mPlayerName;
	private int mPlayerNumber;
	private boolean mJerseyIsRed = true;
	private Intent editActivity;
	
	public static final String KEY_PLAYER_NAME = "KEY_PLAYER_NAME";
	public static final String KEY_PLAYER_NUMBER = "KEY_PLAYER_NUMBER";
	public static final String KEY_JERSEY_IS_RED = "KEY_JERSEY_IS_RED";
	private static final int REQUEST_CODE_JERSEY_INFO = 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jersey_display);
        
        mNameTextView= (TextView)findViewById(R.id.jersey_nameTV);
        mNumberTextView= (TextView)findViewById(R.id.jersey_numberTV);
        mJerseyImageView= (ImageView)findViewById(R.id.imageView_jersey);        
        
        mPlayerName= mNameTextView.getText().toString();
        mPlayerNumber= Integer.parseInt(mNumberTextView.getText().toString());
        
        Button editButton = (Button)findViewById(R.id.btn_edit);
        editButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editActivity= new Intent(JerseyDisplayActivity.this, JerseyEditActivity.class);
				editActivity.putExtra(KEY_PLAYER_NAME, mPlayerName);
				editActivity.putExtra(KEY_PLAYER_NUMBER, mPlayerNumber);
				editActivity.putExtra(KEY_JERSEY_IS_RED, mJerseyIsRed);
				startActivityForResult(editActivity,REQUEST_CODE_JERSEY_INFO);
			}
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode== REQUEST_CODE_JERSEY_INFO){
	    	if(resultCode == RESULT_OK){
	    		mJerseyIsRed= data.getBooleanExtra(KEY_JERSEY_IS_RED, true);
	    		mPlayerName= data.getStringExtra(KEY_PLAYER_NAME);
	    		mPlayerNumber= data.getIntExtra(KEY_PLAYER_NUMBER,0);
	    		updateJerseyInfo();
	    	}
    	}
    }

	private void updateJerseyInfo() {
		if(mJerseyIsRed){
			mJerseyImageView.setImageResource(R.drawable.red_jersey);
		}else{
			mJerseyImageView.setImageResource(R.drawable.blue_jersey);
		}
		mNameTextView.setText(mPlayerName);
		mNumberTextView.setText(""+mPlayerNumber);
	}
}
