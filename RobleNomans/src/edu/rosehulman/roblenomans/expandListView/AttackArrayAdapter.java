/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.rosehulman.roblenomans.expandListView;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.rosehulman.roblenomans.R;
import edu.rosehulman.roblenomans.Activities.MainActivity;
import edu.rosehulman.roblenomans.units.Unit;
import edu.rosehulman.roblenomans.units.UnitFactory;

/**
 * This is a custom array adapter used to populate the listview whose items will
 * expand to display extra content in addition to the default display.
 */
public class AttackArrayAdapter extends ArrayAdapter<AttackExpandableListItem> {

    private List<AttackExpandableListItem> mData;
    private int mLayoutViewResourceId;
	private Context mContext;
	private String btnText;
	private Button recruitBtn;

    public String getBtnText() {
		return btnText;
	}

	public void setBtnText(String btnText) {
		this.btnText = btnText;
	}

	public AttackArrayAdapter(Context context, int layoutViewResourceId,
                              List<AttackExpandableListItem> mData2) {
        super(context, layoutViewResourceId, mData2);
        mData = mData2;
        mContext= context;
        mLayoutViewResourceId = layoutViewResourceId;
    }

    /**
     * Populates the item in the listview cell with the appropriate data. This method
     * sets the thumbnail image, the title and the extra text. This method also updates
     * the layout parameters of the item's view so that the image and title are centered
     * in the bounds of the collapsed view, and such that the extra text is not displayed
     * in the collapsed state of the cell.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final AttackExpandableListItem object = mData.get(position);

        if(convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(mLayoutViewResourceId, parent, false);
        }

        LinearLayout linearLayout = (LinearLayout)(convertView.findViewById(
                R.id.item_linear_layout));
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams
                (AbsListView.LayoutParams.MATCH_PARENT, object.getCollapsedHeight());
        linearLayout.setLayoutParams(linearLayoutParams);

        ImageView imgView = (ImageView)convertView.findViewById(R.id.image_view);
        TextView titleView = (TextView)convertView.findViewById(R.id.title_view);
        TextView textView = (TextView)convertView.findViewById(R.id.text_view);

        titleView.setText(object.getTitle());
        imgView.setImageBitmap(getCroppedBitmap(BitmapFactory.decodeResource(getContext()
                .getResources(), object.getImgResource(), null)));
        textView.setText(object.getText());

        convertView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.WRAP_CONTENT));

        ExpandingLayout expandingLayout = (ExpandingLayout)convertView.findViewById(R.id
                .expanding_layout);
        expandingLayout.setExpandedHeight(object.getExpandedHeight());
        expandingLayout.setSizeChangedListener(object);

        if (!object.isExpanded()) {
            expandingLayout.setVisibility(View.GONE);
        } else {
            expandingLayout.setVisibility(View.VISIBLE);
        }
        
        recruitBtn= (Button) convertView.findViewById(R.id.button_recruit);
        recruitBtn.setText(R.string.attack);
        recruitBtn.setTag(position);
        recruitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				int position= (Integer)v.getTag();										
				
				MainActivity act= ((MainActivity)mContext);
				int cost= mData.get(position).getCost();
				if(act.mGame.getmResourceEngine().canAfford(new long[]{0,0,0, cost})){
					act.mGame.getmResourceEngine().payResources(new long[]{0,0,0, cost});
					Toast.makeText(getContext(), "Attacking user: "+ mData.get(position).getTitle(), Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getContext(), "Can't afford", Toast.LENGTH_LONG).show();
				}
				
			}
		});
        
        return convertView;
    }

    /**
     * Crops a circle out of the thumbnail photo.
     */
    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Config.ARGB_8888);

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);

        int halfWidth = bitmap.getWidth()/2;
        int halfHeight = bitmap.getHeight()/2;

        canvas.drawCircle(halfWidth, halfHeight, Math.max(halfWidth, halfHeight), paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}