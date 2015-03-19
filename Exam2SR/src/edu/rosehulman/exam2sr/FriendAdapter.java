package edu.rosehulman.exam2sr;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FriendAdapter extends BaseAdapter{	
	private Context mContext;
	private ArrayList<Integer> selectedIds;

	public FriendAdapter(Context mContext) {
		super();
		selectedIds= new ArrayList<Integer>();
		this.mContext = mContext;		
	}

	@Override
	public int getCount() {
		return MainActivity.getmFriendList().size();
	}

	@Override
	public Object getItem(int position) {
		return MainActivity.getmFriendList().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RowView view =null;
		if(convertView == null){
			//if there isn't one to recycle, initialize a new view
			view= new RowView(this.mContext);
		}else{
			//recycle the old one
			view= (RowView) convertView;
		}
		
		ArrayList<Friend> mFriends= MainActivity.getmFriendList();
		
		view.setLeftText(mFriends.get(position).getName());
		int hour= mFriends.get(position).getHour();
		int minute= mFriends.get(position).getMinute();
		String minuteStr=minute+"";
		if(minute<10){
			minuteStr= "0"+minute;
		}
		view.setRightText(hour + ":" + minuteStr);
		return view;
	}

	public void toggleSelection(int position) {
		if(selectedIds.indexOf(position)!=-1){
			selectedIds.remove((Object)position);
		}else{
			selectedIds.add(position);
		}
	}

	public void removeSelection() {
		selectedIds.removeAll(selectedIds);
	}

	public ArrayList<Integer> getSelectedIds() {
		return selectedIds;
	}

}
