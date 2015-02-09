package edu.rosehulman.roblenomans.units;

import android.os.Parcel;

public class Knight extends UnitAbstract {
	
	
	public Knight(){
		this.mConsumptionRate= -2;
		this.mAttackPower=2;
		this.mDescription= "The knights move slowly and have a high attack power";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {		
	}

}
