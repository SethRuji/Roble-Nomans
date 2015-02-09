package edu.rosehulman.roblenomans.units;

import android.os.Parcelable;

public abstract class UnitAbstract implements Unit, Parcelable {
	protected int mConsumptionRate;
	protected int mAttackPower;
	protected int mHP;
	protected String mDescription;	

	@Override
	public int getAttackPower() {
		return this.mAttackPower;
	}
	
	@Override
	public int getConsumptionRate() {
		return this.mConsumptionRate;
	}
	@Override
	public int getHP() {
		return this.mHP;
	}
	
	@Override
	public long[] getIncome() {
		// TODO Auto-generated method stub
		return new long[]{0,0,0,mConsumptionRate};
	}
	
	public String getDescription(){
		return mDescription;
	}
}
