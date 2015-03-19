package edu.rosehulman.exam2sr;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		String[] names= getIntent().getExtras().getStringArray(MainActivity.FRIENDS_ARRAY);
		String catNames= "";
		for(String name : names){
			catNames+= name+"\n";
		}

		TextView nameTV= (TextView)findViewById(R.id.textView_message);		
		nameTV.setText(String.format("I'm happy to know:\n%s", catNames));
	}
}
