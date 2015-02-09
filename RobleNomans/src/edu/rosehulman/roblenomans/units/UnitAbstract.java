package edu.rosehulman.roblenomans.units;

public abstract class UnitAbstract implements Unit {
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
	
	public String getDescription(){
		return mDescription;
	}
}
