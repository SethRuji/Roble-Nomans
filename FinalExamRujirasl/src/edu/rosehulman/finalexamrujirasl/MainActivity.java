package edu.rosehulman.finalexamrujirasl;

import java.io.IOException;
import java.util.List;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.appspot.csse483_hangman.gameservice.Gameservice;
import com.appspot.csse483_hangman.gameservice.model.Hangman;
import com.appspot.csse483_hangman.gameservice.model.HangmanCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

public class MainActivity extends ListActivity {

	private static final String MQ = "MQ";
	private Gameservice mService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mService= new Gameservice(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);		

		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
//		getListView().setMultiChoiceModeListener(new MyMultiClickListener());

		updateHangmen();
	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		final Hangman currentQuote = (Hangman) getListAdapter().getItem(position);
		DialogFragment df = new DialogFragment() {
			@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				View view = inflater.inflate(R.layout.dialog_guess, container);
				getDialog().setTitle("Add a new game");
				final Button confirmButton = (Button) view.findViewById(R.id.guess_dialog_ok);
				final Button cancelButton = (Button) view.findViewById(R.id.guess_dialog_cancel);
				final TextView displayWordTV= (TextView) view.findViewById(R.id.guess_dialog_word);
				final TextView guessesTV = (TextView) view.findViewById(R.id.guess_dialog_gesses);
				final EditText inputGuess= (EditText) view.findViewById(R.id.guess_dialog_input);
				String displW= currentQuote.getDisplayWord();
				displayWordTV.setText("Word: "+displW);
				guessesTV.setText("Guesses: "+currentQuote.getGuesses());
				
				confirmButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String guess= inputGuess.getText().toString().toLowerCase();
						currentQuote.setGuesses(currentQuote.getGuesses()+guess);
						new InsertGameTask().execute(currentQuote);
						dismiss();
					}
				});

				cancelButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dismiss();
					}
				});
				return view;
			}
		};
		df.show(getFragmentManager(), "");
		super.onListItemClick(l, v, position, id);
	}

	private void updateHangmen() {
		(new QueryForGamesTask()).execute();
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
			addGame();

			return true;
		case R.id.sync:
			// sync
			updateHangmen();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	private void addGame() {
		DialogFragment df = new DialogFragment() {
			@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				View view = inflater.inflate(R.layout.dialog_add, container);
				getDialog().setTitle("Add a new game");
				final Button confirmButton = (Button) view.findViewById(R.id.add_dialog_ok);
				final Button cancelButton = (Button) view.findViewById(R.id.add_dialog_cancel);
				final EditText creatorEditText = (EditText) view.findViewById(R.id.add_dialog_creator);
				final EditText secretEditText = (EditText) view.findViewById(R.id.add_dialog_secret);

				confirmButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String creatorText = creatorEditText.getText().toString();
						String secretText = secretEditText.getText().toString();
						// add the data and send to server
						Hangman currentQuote = new Hangman();
						currentQuote.setCreator(creatorText);
						currentQuote.setSecretWord(secretText);
						((HangmanArrayAdapter) getListAdapter()).add(currentQuote);
						((HangmanArrayAdapter) getListAdapter()).notifyDataSetChanged();
						new InsertGameTask().execute(currentQuote);
						dismiss();
					}
				});

				cancelButton.setOnClickListener(new OnClickListener() {
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

	class InsertGameTask extends AsyncTask<Hangman, Void, Hangman>{

		@Override
		protected Hangman doInBackground(Hangman... quotes) {
			Hangman returnedQuote= null;
			try{
				returnedQuote= mService.hangman().insert(quotes[0]).execute();
			}catch(IOException e){
				Log.e(MQ, "Failed inserting" + e);
			}
			return returnedQuote;
		}
		
		@Override
		protected void onPostExecute(Hangman result) {			
			super.onPostExecute(result);
			if(result==null){
				Log.e(MQ, "Failed Inserting, result is null");
				return;
			}
			updateHangmen();
		}
	}
	class QueryForGamesTask extends AsyncTask<Void, Void, HangmanCollection>{

		@Override
		protected HangmanCollection doInBackground(Void... params) {
			HangmanCollection quotes=null;
			try {
				Gameservice.Hangman.List query = mService.hangman().list();
				query.setLimit(50L);
				query.setOrder("-last_touch_date_time");
				quotes= query.execute();
			} catch (Exception e) {
				Log.e(MQ,"error in loading, quotes is null: "+ e);
			}
			return quotes;
		}
		
		@Override
		protected void onPostExecute(HangmanCollection result) {
			super.onPostExecute(result);
			if(result ==null){
				Log.e(MQ, "error in loading, quotes is null in postExecute ");
			}
			List<Hangman> quotes= result.getItems();
			
			HangmanArrayAdapter adapter = new HangmanArrayAdapter(MainActivity.this,
					R.layout.row_view, R.id.textView_display_word, quotes);
			setListAdapter(adapter);			
		}
	}
}
