package edu.rosehulman.blackjacktrainer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import edu.rosehulman.blackjacktrainer.Card.CardName;
import edu.rosehulman.blackjacktrainer.Card.Suit;

public class Blackjack extends Activity {

	public static final String BLACKJACK = "BLACKJACK";
	public static final int DIALOG_ID_MAKE_YOUR_MOVE = 0;
	public static final int DIALOG_ID_SKIP = 1;
	public static final int DIALOG_ID_CHEAT = 2;
	public static final int DIALOG_ID_COACH_COMMENT = 3;
	public static final int DIALOG_ID_ABOUT = 4;

	private static final int[] DRAWABLES_CLUBS = {0,R.drawable.playing_card_club_a,R.drawable.playing_card_club_2,R.drawable.playing_card_club_3,R.drawable.playing_card_club_4,R.drawable.playing_card_club_5,R.drawable.playing_card_club_6,R.drawable.playing_card_club_7,R.drawable.playing_card_club_8,R.drawable.playing_card_club_9,R.drawable.playing_card_club_10,R.drawable.playing_card_club_j,R.drawable.playing_card_club_q,R.drawable.playing_card_club_k};
	private static final int[] DRAWABLES_DIAMONDS = {0,R.drawable.playing_card_diamond_a,R.drawable.playing_card_diamond_2,R.drawable.playing_card_diamond_3,R.drawable.playing_card_diamond_4,R.drawable.playing_card_diamond_5,R.drawable.playing_card_diamond_6,R.drawable.playing_card_diamond_7,R.drawable.playing_card_diamond_8,R.drawable.playing_card_diamond_9,R.drawable.playing_card_diamond_10,R.drawable.playing_card_diamond_j,R.drawable.playing_card_diamond_q,R.drawable.playing_card_diamond_k};
	private static final int[] DRAWABLES_HEARTS = {0,R.drawable.playing_card_heart_a,R.drawable.playing_card_heart_2,R.drawable.playing_card_heart_3,R.drawable.playing_card_heart_4,R.drawable.playing_card_heart_5,R.drawable.playing_card_heart_6,R.drawable.playing_card_heart_7,R.drawable.playing_card_heart_8,R.drawable.playing_card_heart_9,R.drawable.playing_card_heart_10,R.drawable.playing_card_heart_j,R.drawable.playing_card_heart_q,R.drawable.playing_card_heart_k};
	private static final int[] DRAWABLES_SPADES = {0,R.drawable.playing_card_spade_a,R.drawable.playing_card_spade_2,R.drawable.playing_card_spade_3,R.drawable.playing_card_spade_4,R.drawable.playing_card_spade_5,R.drawable.playing_card_spade_6,R.drawable.playing_card_spade_7,R.drawable.playing_card_spade_8,R.drawable.playing_card_spade_9,R.drawable.playing_card_spade_10,R.drawable.playing_card_spade_j,R.drawable.playing_card_spade_q,R.drawable.playing_card_spade_k};
	private ImageView mDealerCardImageView;
	private ImageView mPlayerCardImageView_1;
	private ImageView mPlayerCardImageView_2;

	private BlackjackRound mRound;
	private String mActionSelected;
	private static final String KEY_DEALER_CARD = "KEY_DEALER_CARD";
	private static final String KEY_PLAYER_CARD_1 = "KEY_PLAYER_CARD_1";
	private static final String KEY_PLAYER_CARD_2 = "KEY_PLAYER_CARD_2";
	private static final String KEY_NUM_CORRECT = "KEY_NUM_CORRECT";
	private static final String KEY_NUM_ROUNDS = "KEY_NUM_ROUNDS";
	private static final String KEY_ACTION_SELECTED = "KEY_ACTION_SELECTED";
	private int mNumCorrect;
	private int mNumRounds;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		this.mDealerCardImageView = (ImageView) findViewById(R.id.dealer_card_1);
		this.mPlayerCardImageView_1 = (ImageView) findViewById(R.id.player_card_1);
		this.mPlayerCardImageView_2 = (ImageView) findViewById(R.id.player_card_2);

		((Button) findViewById(R.id.make_your_move_button)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showMoveDialog();
			}
		});
		((Button) findViewById(R.id.skip_button)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showSkipDialog();
			}

		});
		((Button) findViewById(R.id.cheat_button)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showCheatDialog();
			}
		});

		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		this.mNumCorrect = prefs.getInt(KEY_NUM_CORRECT, 0);
		this.mNumRounds = prefs.getInt(KEY_NUM_ROUNDS, 0);
		String dealerCardString = prefs.getString(KEY_DEALER_CARD, "");
		String playerCardString1 = prefs.getString(KEY_PLAYER_CARD_1, "");
		String playerCardString2 = prefs.getString(KEY_PLAYER_CARD_2, "");
		if (dealerCardString.equalsIgnoreCase("") || playerCardString1.equalsIgnoreCase("") || playerCardString2.equalsIgnoreCase("")) {
			initNewRound();
		} else {
			Card dealerCard = new Card(dealerCardString);
			Card playerCard1 = new Card(playerCardString1);
			Card playerCard2 = new Card(playerCardString2);
			this.mRound = new BlackjackRound(this, dealerCard, playerCard1, playerCard2);
			updateView();
			this.mActionSelected = prefs.getString(KEY_ACTION_SELECTED, "");  // Fixing a bug when the coach dialog is open during a rotate
		}
	}







	private void initNewRound() {
		Blackjack.this.mRound = new BlackjackRound(this);
		mNumRounds++;
		updateView();	
	}

	private void updateView() {
		Card.CardName dealerCardName = this.mRound.getDealerCard().getCardName();
		Card.Suit dealerSuit = this.mRound.getDealerCard().getSuit();
		Card.CardName playerCardName1 = this.mRound.getPlayerCard1().getCardName();
		Card.Suit playerSuit1 = this.mRound.getPlayerCard1().getSuit();
		Card.CardName playerCardName2 = this.mRound.getPlayerCard2().getCardName();
		Card.Suit playerSuit2 = this.mRound.getPlayerCard2().getSuit();

		int dealerCardDrawableIndex = getDrawableIndexFromCardName( dealerCardName );
		int playerCardDrawableIndex1 = getDrawableIndexFromCardName( playerCardName1 );
		int playerCardDrawableIndex2 = getDrawableIndexFromCardName( playerCardName2 );

		int[] dealerDrawableArray = getDrawableArrayFromSuit( dealerSuit );
		int[] playerDrawableArray1 = getDrawableArrayFromSuit( playerSuit1 );
		int[] playerDrawableArray2 = getDrawableArrayFromSuit( playerSuit2 );

		this.mDealerCardImageView.setImageResource(dealerDrawableArray[dealerCardDrawableIndex]);
		this.mPlayerCardImageView_1.setImageResource(playerDrawableArray1[playerCardDrawableIndex1]);
		this.mPlayerCardImageView_2.setImageResource(playerDrawableArray2[playerCardDrawableIndex2]);
	}

	private int[] getDrawableArrayFromSuit(Suit dealerSuit) {
		switch (dealerSuit) {
		case CLUBS:
			return DRAWABLES_CLUBS;
		case DIAMONDS:
			return DRAWABLES_DIAMONDS;
		case HEARTS:
			return DRAWABLES_HEARTS;
		case SPADES:
			return DRAWABLES_SPADES;
		default:
			return null;
		}
	}

	private int getDrawableIndexFromCardName(CardName cardName) {
		switch (cardName) {
		case KING:
			return 13;
		case QUEEN:
			return 12;
		case JACK:
			return 11;
		default:
			return Card.numericValueFromCardName(cardName);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(KEY_NUM_CORRECT, this.mNumCorrect);
		editor.putInt(KEY_NUM_ROUNDS, this.mNumRounds);
		editor.putString(KEY_DEALER_CARD, this.mRound.getDealerCard().toString());
		editor.putString(KEY_PLAYER_CARD_1, this.mRound.getPlayerCard1().toString());
		editor.putString(KEY_PLAYER_CARD_2, this.mRound.getPlayerCard2().toString());
		editor.putString(KEY_ACTION_SELECTED, this.mActionSelected);
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about_menu_item:
			showAboutMenu();
			return true;
		case R.id.reset_menu_item:
			this.mNumCorrect = 0;
			this.mNumRounds = 0;
			return true;
		default:
			return super.onOptionsItemSelected(item);	
		}
	}

	private void showAboutMenu() {
		DialogFragment df= new DialogFragment(){			
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {				
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle(R.string.about_title);
				builder.setIcon(R.drawable.ic_blackjack);
				builder.setMessage(R.string.about_message);
				builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {			
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dismiss();
					}
				});
				return builder.create();
			}
		};
		df.show(getFragmentManager(), getString(R.string.about));
	}
	private void showMoveDialog() {
		DialogFragment df= new DialogFragment(){
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder b= new Builder(getActivity());
				b.setTitle(R.string.make_your_move_title);
				b.setItems(mRound.legalMovesAsString(), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {						
						mActionSelected= mRound.legalMovesAsString()[which];
						String correct= mRound.getCorrectPlayerActionAsString();
					
						showAdviceDialog(correct);
					}


				});
				return b.create();
			}
		};
		df.show(getFragmentManager(), "MakeMove");
	}
	private void showAdviceDialog(final String correct) {
		DialogFragment df= new DialogFragment(){			
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				String dialogText="";
				if(correct.equals(mActionSelected)){
					builder.setIcon(R.drawable.correct_move);	
					builder.setTitle(R.string.correct);
					mNumCorrect++;
				}else{
					dialogText= getString(R.string.coach_advice, mRound.toString(), mRound.getCorrectPlayerActionAsString()) + "\n";
					builder.setIcon(R.drawable.incorrect_move);
					builder.setTitle(R.string.incorrect);
				}
				String percentCorrect= getString(R.string.percentage_correct, mNumCorrect, mNumRounds+1, (double)mNumCorrect/(mNumRounds+1) *100.0);
				builder.setMessage(dialogText.concat(percentCorrect));
				builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dismiss();
						initNewRound();
					}
				});
				return builder.create();
			}
		};
		df.show(getFragmentManager(), "MakeMove");
	}
	
	private void showSkipDialog() {
		DialogFragment df= new DialogFragment(){			
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage(R.string.skip_message);
				builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {			
					@Override
					public void onClick(DialogInterface dialog, int which) {
						initNewRound();
						dismiss();
					}
				});
				builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dismiss();						
					}
				});
				return builder.create();
			}
		};
		df.show(getFragmentManager(), getString(R.string.about));
	}
	
	protected void showCheatDialog() {
		DialogFragment df= new DialogFragment(){

			@Override
			public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, Bundle savedInstanceState) {
				String action= mRound.getCorrectPlayerActionAsString();
				String title= getString(R.string.coach_advice, mRound.toString(), action);
				getDialog().setTitle(title);
				View cheatView= inflater.inflate(R.layout.cheat_matrix,null);				
				return cheatView;
			};
		};
		
		df.show(getFragmentManager(), "");
	}
}