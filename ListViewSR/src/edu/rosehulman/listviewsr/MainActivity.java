package edu.rosehulman.listviewsr;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final ListView myListView= (ListView)findViewById(R.id.list_view);
        final Button addViewButton = (Button) findViewById(R.id.add_view_button);
        
        String[] names= new String[]{"Chris", "Teddy", "Ruji", "Cricket"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  android.R.layout.simple_expandable_list_item_1, names);
        
        final RowNumberAdapter rowNumAdpt= new RowNumberAdapter(this);
        myListView.setAdapter(rowNumAdpt);
        
        myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				Toast.makeText(MainActivity.this, "you pressed row "+ position, Toast.LENGTH_SHORT).show();
				Toast.makeText(MainActivity.this, "you pressed "+ rowNumAdpt.getItem(position), Toast.LENGTH_SHORT).show();
				
			}
        	
		});
        
        addViewButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rowNumAdpt.addView();
				rowNumAdpt.notifyDataSetChanged();
			}
		});
    }
}
