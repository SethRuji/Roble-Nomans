package com.example.lab6fragmentssr;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity implements OnDataPassToTwoListener, OnRandomColorChangeListener {
	private FragmentOne mFragmentOne;
	private FragmentTwo mFragmentTwo;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        FragmentManager fragMan= getFragmentManager();
        FragmentTransaction ft= fragMan.beginTransaction();
        
        mFragmentOne= new FragmentOne();
        mFragmentTwo= new FragmentTwo();
        
        ft.add(R.id.fragment_one_container, mFragmentOne);
        ft.add(R.id.fragment_two_container, mFragmentTwo);
        ft.commit();
    }

	@Override
	public void passDataToTwo(String data) {
		mFragmentTwo.setText(data);
	}

	@Override
	public void randomizeColor() {
		mFragmentOne.randomizeColor();
	}
}
