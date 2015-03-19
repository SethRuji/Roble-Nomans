package edu.rosehulman.exam2sr;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RowView extends LinearLayout {

	private TextView mLeftTextView;
	private TextView mRightTextView;

	public RowView(Context context) {
		super(context);		
		((Activity)context).getLayoutInflater().inflate(R.layout.row_view,this);
		
		//Create Views
		mLeftTextView= (TextView)findViewById(R.id.textView_name);
		mRightTextView= (TextView)findViewById(R.id.textView_time);

	}
	
	public void setLeftText(String text){
		mLeftTextView.setText(text);
	}
	
	public void setRightText(String text){
		mRightTextView.setText(text);
	}
	
}
