package edu.rosehulman.linearlightsoutmenusethruji;

import android.app.Activity;
import android.content.Intent;
import android.location.GpsStatus.NmeaListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ChangeNumButtons extends Activity implements OnClickListener{
	int mNumButtons;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_num_buttons);
		
		Intent intent=getIntent();
		mNumButtons= intent.getIntExtra(MainActivity.KEY_NUM_BUTTONS, -1);
		
		RadioButton btn3= (RadioButton)findViewById(R.id.radioButton3);
		RadioButton btn5= (RadioButton)findViewById(R.id.radioButton5);
		RadioButton btn7= (RadioButton)findViewById(R.id.radioButton7);
		RadioButton btn9= (RadioButton)findViewById(R.id.radioButton9);

		btn3.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn9.setOnClickListener(this);
		
		RadioGroup rg= (RadioGroup)findViewById(R.id.radioGroup1);
		int id= getResources().getIdentifier("radio"+mNumButtons, "id", getPackageName());
		rg.check(id);
	}

	@Override
	public void onClick(View v) {
		Intent returnIntent= new Intent();
		switch(v.getId()){
			case R.id.radioButton3:
				returnIntent.putExtra(MainActivity.KEY_NUM_BUTTONS, 3);
				break;
			case R.id.radioButton5:
				returnIntent.putExtra(MainActivity.KEY_NUM_BUTTONS, 5);
				break;
			case R.id.radioButton7:
				returnIntent.putExtra(MainActivity.KEY_NUM_BUTTONS, 7);
				break;
			case R.id.radioButton9:
				returnIntent.putExtra(MainActivity.KEY_NUM_BUTTONS, 9);
				break;
		}
		setResult(RESULT_OK, returnIntent);
		this.finish();
	}
}
