package edu.rose_hulman.rujirasl.hellobuttonSRuji;

import java.util.Random;

import android.app.Activity;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	private int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final TextView msgText= (TextView)findViewById(R.id.messageText);
        Button btn = (Button)findViewById(R.id.button1);
        
        counter=0;
        
        btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				counter++;
				String s= getString(R.string.message_format, counter);
				msgText.setText(s);
				if(counter>5 && counter<10){
					msgText.setVisibility(0);
				}else{
					msgText.setVisibility(1);
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
