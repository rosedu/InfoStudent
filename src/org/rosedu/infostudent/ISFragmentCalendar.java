package org.rosedu.infostudent;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class ISFragmentCalendar extends ListFragment {
	private ArrayAdapter<String> mAdapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceBundle) {
		super.onActivityCreated(savedInstanceBundle);
		
		mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ISModel.getDummyListData("Calendar"));
		setListAdapter(mAdapter);
	}
}
