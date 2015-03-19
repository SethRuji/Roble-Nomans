package edu.rosehulman.weatherpicssr;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appspot.rujirasl_weatherpics.weatherpics.model.Weatherpic;

public class WeatherpicArrayAdapter extends ArrayAdapter<Weatherpic> {

	private Context context;

	public WeatherpicArrayAdapter(Context context, int resource, int textViewResourceId, List<Weatherpic> quotes) {
		super(context, resource, textViewResourceId, quotes);
		this.context= context;
	}
	
	public static class ViewHolder {	    
		public ViewHolder(){}
		public ViewHolder(String caption, String url2) {
			url= url2;
		}
		TextView txtTitle;
	    ImageView imageView;
	    String url;		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder = null;
	    Weatherpic pic = getItem(position);

	    LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	    if (convertView == null) {
	        convertView = mInflater.inflate(R.layout.list_item, null);
	        holder = new ViewHolder();
	        holder.txtTitle= (TextView) convertView.findViewById(R.id.image_caption);
	        holder.imageView = (ImageView) convertView.findViewById(R.id.thumbnail);
	        convertView.setTag(holder);
	    } else{
	        holder = (ViewHolder) convertView.getTag();
		}
	    holder.txtTitle.setText(pic.getCaption());	    
	    holder.imageView.setImageResource(R.drawable.ic_launcher);
	    holder.url= pic.getImageUrl();
	    new DownloadImageTask().execute(holder);
	    return convertView;
	}
	
	public static class DownloadImageTask extends AsyncTask<ViewHolder, Void, Bitmap>{
		ViewHolder mHolder;
		@Override
		protected Bitmap doInBackground(ViewHolder... params) {
			Bitmap bitmap= null;
			mHolder= params[0];
			try{
				InputStream in = new java.net.URL(params[0].url).openStream();
				bitmap= BitmapFactory.decodeStream(in);
				in.close();
			}catch(Exception e){
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return bitmap;
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			float height= result.getHeight();
			float width= result.getWidth();
			float aspect= height/width;
			Log.d("TAG", "aspect ratio"+aspect);
			int containerWidth = mHolder.imageView.getWidth();
			
			Bitmap scaled =Bitmap.createScaledBitmap(result, containerWidth, (int)(containerWidth*aspect), false);
			mHolder.imageView.setImageBitmap(scaled);
		}
	}

//	public ViewHolder getViewHolder(int position, View v) {
//		getView(position, v, null);
//	}
}
