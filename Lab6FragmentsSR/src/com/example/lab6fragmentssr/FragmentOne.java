package com.example.lab6fragmentssr;

import java.util.Random;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentOne extends Fragment implements OnClickListener {
	
	private OnDataPassToTwoListener mListener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v= inflater.inflate(R.layout.fragment_one, container, false);
		((Button) v.findViewById(R.id.button_1)).setOnClickListener(this);
		return v;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try{
			mListener= (OnDataPassToTwoListener) activity;
		}catch(ClassCastException e){
			throw new ClassCastException(activity.toString()+ " must implement OnDatPassToTwoListener");
		}
	}

	@Override
	public void onClick(View v) {
		mListener.passDataToTwo(getString(R.string.one));
	}

	public void randomizeColor() {				
		int color= Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
		((View)(getActivity().findViewById(R.id.button_1).getParent())).setBackgroundColor(color);
	}
}
