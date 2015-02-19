package edu.rosehulman.roblenomans.contentfrags;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import edu.rosehulman.roblenomans.R;
import edu.rosehulman.roblenomans.Activities.MainActivity;

public class MainSettingFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view= inflater.inflate(R.layout.fragment_settings, container);
		Button okBtn= (Button)getActivity().findViewById(R.id.button_save_settings);
		
		
		okBtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				TextView usernameTV= (TextView)getActivity().findViewById(R.id.username);
				((MainActivity)getActivity()).mServer.user.setUsername(usernameTV.getText().toString());
			}
		});
		return view;
	}
}
