package edu.rosehulman.weatherpicssr;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import edu.rosehulman.weatherpicssr.WeatherpicArrayAdapter.ViewHolder;

public class ImageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		
		String caption = getIntent().getExtras().getString(MainActivity.CAPTION_KEY);
		String url = getIntent().getExtras().getString(MainActivity.URL_KEY);
		
		TextView captionTV= (TextView)findViewById(R.id.image_caption);
		ImageView imageView= (ImageView)findViewById(R.id.imageView_large);
		captionTV.setText(caption);
		
		ViewHolder holder= new ViewHolder(caption, url);
		
		holder.imageView=imageView;
		holder.txtTitle= captionTV;
		new WeatherpicArrayAdapter.DownloadImageTask().execute(holder);		
	}
}


