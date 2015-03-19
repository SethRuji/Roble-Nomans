package com.example.lab6fragmentssr;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentTwo extends Fragment implements OnClickListener {
	private OnRandomColorChangeListener mListener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v= inflater.inflate(R.layout.fragment_two, container, false);
		((Button) v.findViewById(R.id.button_2)).setOnClickListener(this);
		return v;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try{
			mListener= (OnRandomColorChangeListener) activity;
		}catch(ClassCastException e){
			throw new ClassCastException(activity.toString()+ " must implement OnRandomColorChangeListener");
		}
	}
	
	@Override
	public void onClick(View v) {
		mListener.randomizeColor();
	}
	
	public void setText(String text){
		((TextView) getActivity().findViewById(R.id.textView_2)).setText(text);
	}
}
