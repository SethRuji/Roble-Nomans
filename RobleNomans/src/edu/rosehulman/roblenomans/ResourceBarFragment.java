package edu.rosehulman.roblenomans;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ResourceBarFragment extends Fragment implements OnClickListener {	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v= inflater.inflate(R.layout.resource_bar, container, false);
		
		return v;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);		
	}
	
	@Override
	public void onClick(View v) {
		
	}
	
}
