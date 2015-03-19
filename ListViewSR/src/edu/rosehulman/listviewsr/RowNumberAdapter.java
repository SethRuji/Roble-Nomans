package edu.rosehulman.listviewsr;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

public class RowNumberAdapter extends BaseAdapter {
	private Context mContext;
	private int mNumRows;
	private String[] mMonths;
	
	public RowNumberAdapter(Context context){
		mContext = context;
		mMonths= context.getResources().getStringArray(R.array.monthNames);
	}
	
	
	@Override
	public int getCount() {
		return mNumRows;
	}

	@Override
	public Object getItem(int position) {
		return this.mMonths[position %12];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RowView view = null;
		if(convertView == null){
			//if there isn't one to recycle, initialize a new view
//			view = new TextView(this.mContext);
//			view = new Button(this.mContext);
//			view.setTextSize(18); //properties fixed for all views
			view= new RowView(this.mContext);
		}else{
			//use the recycled view( convert this view)
//			view= (TextView) convertView;
//			view= (Button) convertView;
			view= (RowView) convertView;
		}
		
		//customize the view instance as appropriate before returning it
//		view.setText(" Row "+ position);
//		view.setTextColor(Color.rgb(position*10,  255- position*10, 200));
//		view.setImageResource(R.drawable.ic_launcher);
		view.setLeftText(" "+ (position +1) +". ");
		view.setRightText(mMonths[position %12]);
		return view;
	}


	public void addView() {
		// TODO Auto-generated method stub
		mNumRows++;
	}

}
