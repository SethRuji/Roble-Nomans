package edu.rose_hulman.rujirasl.MyFirstAppSethRuji;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import edu.rose_hulman.rujirasl.hellobuttonSRuji.R;


public class MainActivity extends Activity {
	
	private EditText editText;
	private Button button;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        editText= (EditText)findViewById(R.id.edit_message);
        
        button = (Button)findViewById(R.id.sendButton);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (editText.getText().toString().equals(getString(R.string.secret))) {
					button.setText(R.string.button_text_wow);
				} else {
					button.setText(R.string.button_send);
				}
			}
		});
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
        }
        return super.onOptionsItemSelected(item);
    }
}