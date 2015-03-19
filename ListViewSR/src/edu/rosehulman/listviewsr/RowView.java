package edu.rosehulman.listviewsr;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RowView extends LinearLayout {

	private TextView mLeftTextView;
	private TextView mRightTextView;

	public RowView(Context context) {
		super(context);		
		((Activity)context).getLayoutInflater().inflate(R.layout.row_view,this);
		
		//Create Views
		mLeftTextView= (TextView)findViewById(R.id.left_text_view);
		mRightTextView= (TextView)findViewById(R.id.right_text_view);
//		mLeftTextView= new TextView(context);
//		mRightTextView= new TextView(context);
		
		//Modify Properties
//		this.setOrientation(HORIZONTAL);
//		this.setBackgroundColor(Color.YELLOW);
//	 	this.mLeftTextView.setBackgroundColor(Color.CYAN);
//	 	this.mLeftTextView.setMinimumWidth(60);
//	 	this.mLeftTextView.setTextColor(Color.DKGRAY);
//	 	this.mLeftTextView.setTextSize(18);
//	 	this.mLeftTextView.setPadding(5, 5, 5, 5);
//	 	this.mRightTextView.setTextColor(Color.BLACK);
//	 	this.mRightTextView.setTextSize(18);
//	 	this.mRightTextView.setPadding(10, 5, 10, 5);
		
		//add to layout
//		addView(mLeftTextView);
//		addView(mRightTextView);
	}
	
	public void setLeftText(String text){
		mLeftTextView.setText(text);
	}
	
	public void setRightText(String text){
		mRightTextView.setText(text);
	}
	
}
