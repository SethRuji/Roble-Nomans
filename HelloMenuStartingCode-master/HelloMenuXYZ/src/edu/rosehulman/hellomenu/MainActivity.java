package edu.rosehulman.hellomenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	static final String HM = "HM";
	public static final String KEY_COLOR = "KEYCOLOR";
	private TextView mHelloTextView;
	private ActionMode mActMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mHelloTextView = (TextView) findViewById(R.id.helloTextView);
		mHelloTextView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				//adding action mode
				if(mActMode !=null){
					return false;
				}
				
				mActMode= startActionMode(new MyActionModeCallback());
				mActMode.setTitle(R.string.menu_context_title);
				mActMode.setSubtitle(R.string.menu_context_subtitle);
				return true;//givies tactile feedback on return
			}
		});			
	}

	class MyActionModeCallback implements ActionMode.Callback{

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// TODO Auto-generated method stub
			mode.getMenuInflater().inflate(R.menu.context, menu);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			float textSize= mHelloTextView.getTextSize();
			switch(item.getItemId()){
				case R.id.increase:
					textSize+=12;
					break;
				case R.id.decrease:
					textSize-=12;
					break;
			}
			mHelloTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			mActMode = null;
		}
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if(id==R.id.color){
			Log.d(HM,"Launching color activity");
			Intent colorIntent = new Intent(this, ColorActivity.class);
			startActivity(colorIntent);
			return true;
		}
		
		
		return super.onOptionsItemSelected(item);
	}
}
