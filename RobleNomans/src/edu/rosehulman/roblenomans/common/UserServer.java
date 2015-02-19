package edu.rosehulman.roblenomans.common;

import java.io.IOException;
import java.util.Random;

import android.os.AsyncTask;
import android.util.Log;

import com.appspot.roble_nomans_debiruji.users.Users;
import com.appspot.roble_nomans_debiruji.users.model.User;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

public class UserServer {
	public static String TAG_SERVER= "RN_TAG_SERVER";
	private Users mService;
	public User user;
	
	public UserServer(){
		user= new User();
		user.setUsername(new Random().nextGaussian()*10000+"");
		mService= new Users(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
	}
	
	public class InsertQuoteTask extends AsyncTask<User, Void, User>{		
		@Override
		protected User doInBackground(User... users) {
			users[0]= new User();
			((User)users[0]).setArmyStr((long) 14);
			((User)users[0]).setUsername("alsdkfjalsdk");
			User returnedUser= null;
			try{
				returnedUser= mService.user().insert(users[0]).execute();
			}catch(IOException e){
				Log.e(TAG_SERVER, "Failed inserting" + e);
			}
			return returnedUser;
		}
		
		@Override
		protected void onPostExecute(User result) {			
			super.onPostExecute(result);
			if(result==null){
				Log.e(TAG_SERVER, "Failed Inserting, result is null");
				return;
			}
			user= result;
		}
	}
}
