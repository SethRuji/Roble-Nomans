package edu.rosehulman.lab5hellodialogsSR;

import java.sql.Date;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button timeBtn= (Button)findViewById(R.id.button_time);
        Button dateBtn= (Button)findViewById(R.id.button_date);
        
        timeBtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showTimePickerDialog(v);			
			}
		});
        
        dateBtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDatePickerDialog(v);			
			}
		});
    }

	public void showTimePickerDialog(View v) {
		DialogFragment newFrag= new TimePickerFragment();
		newFrag.show(getFragmentManager(), "timePicker");
	}
	
	public void showDatePickerDialog(View v) {
		DialogFragment newFrag= new DatePickerFragment();
		newFrag.show(getFragmentManager(), "datePicker");
	}	
	
	public static class TimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);		
		
		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,
		DateFormat.is24HourFormat(getActivity()));
		}
		
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// Do something with the time chosen by the user
			String min= minute+"";
			if(minute<10){
				min= "0"+min;
			}
			Toast.makeText(getActivity(), "chosen time is "+ hourOfDay+":"+min, Toast.LENGTH_SHORT).show();
		}
	}
	
	public static class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		public void onDateSet(DatePicker view, int year, int month, int day) {
			//	Do something with the date chosen by the user
			
			java.text.DateFormat df= DateFormat.getDateFormat(getActivity());
			Calendar cal= Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DATE, day);
			java.util.Date d= cal.getTime();
			Toast.makeText(getActivity(), "Selected date is: "+df.format(d), Toast.LENGTH_SHORT).show();
		}
	}
}
