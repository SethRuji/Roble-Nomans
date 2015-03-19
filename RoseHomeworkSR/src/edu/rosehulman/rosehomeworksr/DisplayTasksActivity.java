package edu.rosehulman.rosehomeworksr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayTasksActivity extends ListActivity {
	private ArrayList<Task> mTasks;
	private ArrayAdapter<Task> mArrayAdapter;
	private TaskDataAdapter mTaskDataAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mTasks= new ArrayList<Task>();
        mTasks.add(new Task("Lab6", "CSSE483", new GregorianCalendar()));
        mTasks.add(new Task("Arduino", "ME435", new GregorianCalendar()));
        
        mArrayAdapter = new ArrayAdapter<Task>(this,  android.R.layout.simple_list_item_1, mTasks);
        setListAdapter(mArrayAdapter);
        
        getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				deleteTask(mTasks.get(position));
				return false;
			}        	
		});
        
        mTaskDataAdapter= new TaskDataAdapter(this);
        mTaskDataAdapter.open();
        mTaskDataAdapter.setAllTasks(mTasks);
        mArrayAdapter.notifyDataSetChanged();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// TODO Auto-generated method stub
    	MenuItem addTaskButton = menu.add(R.string.add_task);
    	addTaskButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    	addTaskButton.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				showAddTaskDialog();
				return false;
			}
		});
    	return super.onCreateOptionsMenu(menu);
    }
    
    private void showAddTaskDialog(){    	    
    	DialogFragment df = new DialogFragment(){
			private View mDialogView;

			@Override
    		public Dialog onCreateDialog(Bundle savedInstanceState) {
    			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    		    LayoutInflater inflater = getActivity().getLayoutInflater();

    		    mDialogView = inflater.inflate(R.layout.dialog_add, null);
    		    builder.setView(mDialogView);    		        		    
				
    		    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						TextView nameTV= (TextView)mDialogView.findViewById(R.id.dialogAddName);
						TextView courseTV= (TextView)mDialogView.findViewById(R.id.dialogAddCourse);
										    	    
						DatePicker dateTV= (DatePicker)mDialogView.findViewById(R.id.dialogAddDueDatePicker);
						GregorianCalendar date= new GregorianCalendar(dateTV.getDayOfMonth(), dateTV.getMonth(), dateTV.getYear());
						addTask(new Task(nameTV.getText().toString(), courseTV.getText().toString(), date));
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
    	df.show(getFragmentManager(), "ADD");
    }
    
    private void addTask(Task task){
    	Toast.makeText(this,  "Adding Task: "+ task,  Toast.LENGTH_SHORT).show();
    	mTasks.add(task);    	
    	mTaskDataAdapter.addTask(task);
    	Collections.sort(mTasks);
    	mArrayAdapter.notifyDataSetChanged();
    }
    
    private void deleteTask(Task task){
    	Toast.makeText(this,  "Deleting Task: "+ task,  Toast.LENGTH_SHORT).show();
    	mTaskDataAdapter.deleteTask(task.getId());
    	mTasks.remove(task);
    	Collections.sort(mTasks);
    	mArrayAdapter.notifyDataSetChanged();
    }
    

    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	mTaskDataAdapter.close();
    }
}
