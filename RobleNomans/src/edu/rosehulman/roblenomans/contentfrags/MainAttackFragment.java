package edu.rosehulman.roblenomans.contentfrags;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import edu.rosehulman.roblenomans.R;
import edu.rosehulman.roblenomans.expandListView.AttackArrayAdapter;
import edu.rosehulman.roblenomans.expandListView.AttackExpandableListItem;
import edu.rosehulman.roblenomans.expandListView.AttackExpandingListView;
import edu.rosehulman.roblenomans.expandListView.ExpandableListItem;
import edu.rosehulman.roblenomans.expandListView.ExpandingListView;

public class MainAttackFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		double[] distances= new double[]{2.2, 3.4, 5.5, 11.2};
		int[] cost= new int[]{40, 50, 60, 100};
		AttackExpandableListItem[] values = new AttackExpandableListItem[] {
                new AttackExpandableListItem("destoryer12", distances[0], cost[0]),
                new AttackExpandableListItem("testUser2",distances[1], cost[1]),
                new AttackExpandableListItem("testUser4",distances[2], cost[2]),
                new AttackExpandableListItem("Leroy Jenkins",distances[3],cost[3]),
        };


        AttackArrayAdapter adapter = new AttackArrayAdapter(getActivity(), R.layout.list_view_item, Arrays.asList(values));

        View unitsView = inflater.inflate(R.layout.fragment_attack, null);
        AttackExpandingListView mListView= (AttackExpandingListView) unitsView.findViewById(R.id.attack_list_view);
        mListView.setAdapter(adapter);
        mListView.setDivider(null);
		return mListView;
	}
}
