package edu.rosehulman.basketballjerseysr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class JerseyEditActivity extends Activity {
	private EditText mNameEditText;
	private EditText mNumberEditText;
	private ToggleButton mJerseyIsRedToggleButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jersey_edit);
		
		Log.d(JerseyDisplayActivity.JDA, "Oncreate edit");
		//get intent extras
		Intent jerseyDisplayActivity= getIntent();
		String resName= jerseyDisplayActivity.getStringExtra(JerseyDisplayActivity.KEY_PLAYER_NAME);
		int resNum= jerseyDisplayActivity.getIntExtra(JerseyDisplayActivity.KEY_PLAYER_NUMBER, 42);
		boolean resToggle= jerseyDisplayActivity.getBooleanExtra(JerseyDisplayActivity.KEY_JERSEY_IS_RED, false);
		Log.d(JerseyDisplayActivity.JDA, "got extras");
		
		//set values
		mNameEditText= (EditText)findViewById(R.id.editNameET);
		mNameEditText.setText(resName);
		
		mNumberEditText= (EditText)findViewById(R.id.editNumberET);
		mNumberEditText.setText(""+resNum);
		
		mJerseyIsRedToggleButton= (ToggleButton)findViewById(R.id.jerseyToggle);
		mJerseyIsRedToggleButton.setChecked(resToggle);
		
		//cancel button
		Button cancelBtn= (Button)findViewById(R.id.btn_cancel);
		cancelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();				
			}
		});
		
		//ok button
		Button okBtn= (Button)findViewById(R.id.btn_ok);
		okBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//get values
				String numStr= mNumberEditText.getText().toString();
				int number=0;
				try{
					number= Integer.parseInt(numStr);
				}catch(NumberFormatException e){
					Log.e(JerseyDisplayActivity.JDA, "Invalid number format");
				}
				String nameStr= mNameEditText.getText().toString();
				boolean isChecked= mJerseyIsRedToggleButton.isChecked();
				
				//return to display activity
				Intent displayIntent= getIntent();
				displayIntent.putExtra(JerseyDisplayActivity.KEY_PLAYER_NAME, nameStr);
				displayIntent.putExtra(JerseyDisplayActivity.KEY_PLAYER_NUMBER, number);
				displayIntent.putExtra(JerseyDisplayActivity.KEY_JERSEY_IS_RED, isChecked);
				setResult(RESULT_OK, displayIntent);
				finish();
			}
		});
	}
}
