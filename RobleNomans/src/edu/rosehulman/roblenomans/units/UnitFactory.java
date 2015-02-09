package edu.rosehulman.roblenomans.units;

public class UnitFactory {
	public static Unit createUnit(String type){
		if(type.equalsIgnoreCase("INFANTRY")){
			return new Infantry();
		}else if(type.equalsIgnoreCase("KNIGHT")){
			return new Knight();
		}else{
			return null;
		}
	}
}
