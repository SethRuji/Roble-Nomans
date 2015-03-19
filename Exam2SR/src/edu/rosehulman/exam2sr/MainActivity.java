package edu.rosehulman.exam2sr;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

public class MainActivity extends Activity {
	public static final String FRIENDS_ARRAY = "FRIENDS_ARRAY";
	private static ArrayList<Friend> mFriendList;
	private int selected;
	private FriendAdapter friendAdt;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView friends= (ListView)findViewById(R.id.listView_friends);
        
        friendAdt= new FriendAdapter(this);
        friends.setAdapter(friendAdt);
        
        setupRemoveListener(friends, friendAdt);
        
        friends.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        
		friends.setMultiChoiceModeListener(new MultiChoiceModeListener() {
 
			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				final int checkedCount = friends.getCheckedItemCount();			
				mode.setSubtitle("Chose "+checkedCount);
				friendAdt.toggleSelection(position);
			}
 
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
				case R.id.details:					
					ArrayList<Integer> selected = friendAdt.getSelectedIds();
					String[] friends= new String[selected.size()];
					for(int i =0;i<selected.size();i++){
						friends[i]= mFriendList.get(selected.get(i)).getName();
					}
					showFriendMessage(friends);
					mode.finish();
					return true;
				default:
					return false;
				}
			}
 
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				mode.getMenuInflater().inflate(R.menu.multi_menu, menu);
				mode.setTitle(R.string.choose);
				return true;
			}
 
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				friendAdt.removeSelection();
			}
 
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}
		}); 
    }

	protected void showFriendMessage(String[] friends) {
		Intent i = new Intent(MainActivity.this, MessageActivity.class);
		i.putExtra(FRIENDS_ARRAY, friends);
		startActivity(i);
	}

	private void setupRemoveListener(ListView friends,
			final FriendAdapter friendAdt) {
		friends.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {				
				DialogFragment df= new DialogFragment(){
					@Override
					public Dialog onCreateDialog(Bundle savedInstanceState) {
						AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
						b.setTitle(R.string.delete_title);
						b.setMessage(R.string.delete_message);
						b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mFriendList.remove(position);
								friendAdt.notifyDataSetChanged();
							}						
						});
						b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dismiss();
							}
						});
						return b.create();
					}
				};
				df.show(getFragmentManager(), "Confirm");			
			}
        });
	}
    
	public static ArrayList<Friend> getmFriendList() {
		if(mFriendList==null){
			mFriendList = new ArrayList<Friend>();
		}
		return mFriendList;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main, menu);    	
		return true;
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	case R.id.add:
    		/*launch custom dialog*/
    		showAddFriendDialog();
    	}
    	return true;
    }
    
    

	private void showAddFriendDialog() {
		DialogFragment df= new DialogFragment(){			
			
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
				builder.setTitle("Add a friend");
				
//				View view= getActivity().getLayoutInflater().inflate(R.layout.add_dialog, null);
				LinearLayout ll= new LinearLayout(getActivity());
				final EditText mName= new EditText(getActivity());
				mName.setWidth(mName.getMaxWidth());
				mName.setHint(R.string.name_hint);
				final TimePicker mTimePicker= new TimePicker(getActivity());
				ll.setOrientation(LinearLayout.VERTICAL);				
				ll.addView(mName);
				ll.addView(mTimePicker);
				mName.requestFocus();
				builder.setView(ll);	
				
				builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {										

					@Override
					public void onClick(DialogInterface dialog, int which) {						
						String name= mName.getText().toString();
												
						int hour= mTimePicker.getCurrentHour();
						int min=  mTimePicker.getCurrentMinute();						
						mFriendList.add(new Friend(name, hour, min ));
						friendAdt.notifyDataSetChanged();
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
			@Override
			public View onCreateView(LayoutInflater inflater,
					ViewGroup container, Bundle savedInstanceState) {
				
				View v= super.onCreateView(inflater, container, savedInstanceState);
				
				return v;
			}
		};	
		
		
		df.show(getFragmentManager(), "add");
				
	}
}
