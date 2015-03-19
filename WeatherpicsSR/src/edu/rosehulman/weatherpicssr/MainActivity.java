package edu.rosehulman.weatherpicssr;

import java.io.IOException;
import java.util.List;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.appspot.rujirasl_weatherpics.weatherpics.Weatherpics;
import com.appspot.rujirasl_weatherpics.weatherpics.model.Weatherpic;
import com.appspot.rujirasl_weatherpics.weatherpics.model.WeatherpicCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

public class MainActivity extends ListActivity {

	private static final String MQ = "MQ";
	public static final String URL_KEY = "DISPLAY_IMAGE_URL_KEY";
	public static final String CAPTION_KEY = "DISPLAY_CAPTION_KEY";
	private Weatherpics mService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mService= new Weatherpics(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);		
		
		updateQuotes();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent imageIntent= new Intent(this, ImageActivity.class);
		Weatherpic item = (Weatherpic)l.getItemAtPosition(position);
//		ViewHolder adapterV= ((WeatherpicArrayAdapter) l.getAdapter()).getViewHolder(position, v);		
		
		imageIntent.putExtra(CAPTION_KEY, item.getCaption());
		imageIntent.putExtra(URL_KEY, item.getImageUrl());
		startActivity(imageIntent);
	}

	private void updateQuotes() {
		(new QueryForQuotesTask()).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add:
			// add
			addItem();
			return true;
		case R.id.sync:
			// sync
			updateQuotes();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void addItem() {
		DialogFragment df = new DialogFragment() {
			@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				View view = inflater.inflate(R.layout.dialog_add, container);
				getDialog().setTitle("Add an image and caption");
				final Button confirmButton = (Button) view.findViewById(R.id.add_dialog_ok);
				final Button cancelButton = (Button) view.findViewById(R.id.add_dialog_cancel);
				final EditText weatherURLEditText = (EditText) view.findViewById(R.id.add_dialog_url);
				final EditText weatherCaptionEditText = (EditText) view.findViewById(R.id.add_dialog_caption);

				confirmButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String urlText = weatherURLEditText.getText().toString();
						String captionText = weatherCaptionEditText.getText().toString();
						if(urlText.isEmpty()){
							urlText= randomImageUrl();
						}
						// add the data and send to server
						Weatherpic currWP = new Weatherpic();
						currWP.setImageUrl(urlText);
						currWP.setCaption(captionText);
						((WeatherpicArrayAdapter) getListAdapter()).add(currWP);
						((WeatherpicArrayAdapter) getListAdapter()).notifyDataSetChanged();
						new InsertWeatherpicTask().execute(currWP);
						dismiss();
					}
				});

				cancelButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dismiss();						
					}
				});
				return view;
			}
		};
		df.show(getFragmentManager(), "");
	}
	
	private String randomImageUrl() {
	       String[] urls = new String[] {
	         "http://upload.wikimedia.org/wikipedia/commons/0/04/Hurricane_Isabel_from_ISS.jpg",
	         "http://daraint.org/wp-content/uploads/2010/12/hurricane_dennis-700x465.jpg",
	         "http://tornado-facts.com/wp-content/uploads/2009/07/tornadoes1-300x181.jpg",
	         "http://severe-wx.pbworks.com/f/tornado.jpg",
	         "http://t.wallpaperweb.org/wallpaper/nature/1920x1080/Lightning_Storm_Over_Fort_Collins_Colorado.jpg",
	         "http://www.legoengineering.com/wp-content/uploads/2013/06/earthquake.jpg",
	         "http://gfxspeak.com/wp-content/uploads/2012/06/Cypress_LCluff_sm.jpg",
	         "http://i.telegraph.co.uk/multimedia/archive/02405/weather-flood-sign_2405295b.jpg",
	         "http://upload.wikimedia.org/wikipedia/commons/0/00/Flood102405.JPG",
	         "http://www.lfpc.org/admin245937/my_documents/my_pictures/33FZ7_fire-forest.jpg",
	         "http://upload.wikimedia.org/wikipedia/commons/6/6b/Mount_Carmel_forest_fire14.jpg"};
	       return urls[(int) (Math.random() * urls.length)];
	 }

	class QueryForQuotesTask extends AsyncTask<Void, Void, WeatherpicCollection>{

		@Override
		protected WeatherpicCollection doInBackground(Void... params) {
			WeatherpicCollection quotes=null;
			try {
				Weatherpics.Weatherpic.List query = mService.weatherpic().list();
				query.setLimit(50L);
				query.setOrder("-last_touch_date_time");
				quotes= query.execute();
			} catch (Exception e) {
				Log.e(MQ,"error in loading, quotes is null: "+ e);
			}
			return quotes;
		}
		
		@Override
		protected void onPostExecute(WeatherpicCollection result) {
			super.onPostExecute(result);
			if(result ==null){
				Log.e(MQ, "error in loading, quotes is null in postExecute ");
			}
			List<Weatherpic> quotes= result.getItems();
			
			WeatherpicArrayAdapter adapter = new WeatherpicArrayAdapter(MainActivity.this,
					android.R.layout.simple_expandable_list_item_2, android.R.id.text1, quotes);
			setListAdapter(adapter);			
		}
	}
	
	class InsertWeatherpicTask extends AsyncTask<Weatherpic, Void, Weatherpic>{

		@Override
		protected Weatherpic doInBackground(Weatherpic... quotes) {
			Weatherpic returnedQuote= null;
			try{
				returnedQuote= mService.weatherpic().insert(quotes[0]).execute();
			}catch(IOException e){
				Log.e(MQ, "Failed inserting" + e);
			}
			return returnedQuote;
		}
		
		@Override
		protected void onPostExecute(Weatherpic result) {			
			super.onPostExecute(result);
			if(result==null){
				Log.e(MQ, "Failed Inserting, result is null");
				return;
			}
			updateQuotes();
		}
	}
}
