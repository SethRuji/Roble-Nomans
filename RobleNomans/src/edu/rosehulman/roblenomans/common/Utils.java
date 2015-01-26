package edu.rosehulman.roblenomans.common;

import android.util.Log;


public class Utils {
	public static String ConvertBigInt(long val){
		float floatVal= Float.parseFloat(val+"");
		if(val<1000){
			return val+"";
		}else if(val >=1000 && val <1000000){
			floatVal= floatVal /1000;
//			Log.d("floatVal", floatVal+"");
			return String.format("%.1fk", floatVal);
		}else if(val>=1000000 && val< 1000000000){
			floatVal= floatVal / 1000000;
			return String.format("%.1fm", floatVal);
		}else if(val>=1000000000){
			floatVal= floatVal / 1000000000;
			return String.format("%.1fb", floatVal);
		}else{
			return "ERROR";
		}
	}
}
