package edu.rosehulman.roblenomans.contentfrags;


import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.rosehulman.roblenomans.R;
import edu.rosehulman.roblenomans.expandListView.CustomArrayAdapter;
import edu.rosehulman.roblenomans.expandListView.ExpandableListItem;
import edu.rosehulman.roblenomans.expandListView.ExpandingListView;
import edu.rosehulman.roblenomans.units.Infantry;
import edu.rosehulman.roblenomans.units.Knight;
import edu.rosehulman.roblenomans.units.Unit;

public class MainUnitsFragment extends Fragment {
    private final int CELL_DEFAULT_HEIGHT = 200;
    private final int NUM_OF_CELLS = 30;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

        ExpandableListItem[] values = new ExpandableListItem[] {
                new ExpandableListItem("Knight", R.drawable.knight, CELL_DEFAULT_HEIGHT,
                        ((Unit)new Knight()).getDescription()),
                new ExpandableListItem("Infantry", R.drawable.rock, CELL_DEFAULT_HEIGHT,
                        ((Unit)new Infantry()).getDescription()),
        };

        List<ExpandableListItem> mData = new ArrayList<ExpandableListItem>();

        for (int i = 0; i < NUM_OF_CELLS; i++) {
            ExpandableListItem obj = values[i % values.length];
            mData.add(new ExpandableListItem(obj.getTitle(), obj.getImgResource(),
                    obj.getCollapsedHeight(), obj.getText()));
        }

        CustomArrayAdapter adapter = new CustomArrayAdapter(getActivity(), R.layout.list_view_item, mData);

        View unitsView = inflater.inflate(R.layout.fragment_units, null);
        ExpandingListView mListView= (ExpandingListView) unitsView.findViewById(R.id.main_list_view);
        mListView.setAdapter(adapter);
        mListView.setDivider(null);
		return mListView;
	}
}
