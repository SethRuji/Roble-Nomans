package edu.rosehulman.roblenomans.contentfrags;

import edu.rosehulman.roblenomans.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainResourceFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view= inflater.inflate(R.layout.fragment_resource, null);
		return view;
	}
}
