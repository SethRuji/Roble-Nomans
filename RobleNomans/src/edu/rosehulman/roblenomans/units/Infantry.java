package edu.rosehulman.roblenomans.units;

import android.os.Parcel;

public class Infantry extends UnitAbstract{
	
	
	public Infantry(){
		this.mConsumptionRate= -2;
		this.mAttackPower=1;
		this.mDescription= "The infantry move slowly and have a low attack power";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
}
