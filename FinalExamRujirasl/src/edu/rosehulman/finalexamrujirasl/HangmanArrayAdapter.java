package edu.rosehulman.finalexamrujirasl;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appspot.csse483_hangman.gameservice.model.Hangman;

public class HangmanArrayAdapter extends ArrayAdapter<Hangman>{
	public HangmanArrayAdapter(Context context, int resource, int textViewResourceId, List<Hangman> quotes) {
		super(context, resource, textViewResourceId, quotes);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		ImageView img = (ImageView)view.findViewById(R.id.imageView1);
		String secretWord =getItem(position).getSecretWord();
		String guesses =getItem(position).getGuesses();
		String displayWord =getItem(position).getDisplayWord();
		
		int incorrectGuesses= getIncorrectGuesses(guesses, displayWord);		
		int id= getContext().getResources().getIdentifier("hangman"+incorrectGuesses, "drawable", getContext().getPackageName());

		img.setImageResource(id);
		
		TextView displayWordTextView = (TextView) view.findViewById(R.id.textView_display_word);
		displayWordTextView.setText(displayWord);
		
		TextView guessTextView = (TextView) view.findViewById(R.id.textView_guesses);
		guessTextView.setText("Guesses:"+ getItem(position).getGuesses());		
		
		TextView creatorTextView = (TextView) view.findViewById(R.id.textView_creator);
		creatorTextView.setText("Creator:"+ getItem(position).getCreator());
		
		if(displayWord.equalsIgnoreCase(secretWord)&& guesses.length()>= secretWord.length()){
			view.setBackgroundColor(Color.GREEN);
		}else if(incorrectGuesses >= 6){
			view.setBackgroundColor(Color.RED);
		}
		return view;
	}

	private int getIncorrectGuesses(String guesses, String displayWord) {
		char cur;
		int correct=0;
		for(int i=0;i<guesses.length();i++){
			cur= guesses.charAt(i);
			if(displayWord.contains(cur+"")){
				correct++;
			}
		}
		int incorrect=guesses.length()-correct;
		if(incorrect>6){
			return 6;
		}
		return incorrect;
	}
}
