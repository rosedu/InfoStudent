package org.rosedu.infostudent;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class ISFragmentCourses extends ListFragment {
	private ArrayAdapter<ISCourseFile> mAdapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceBundle) {
		super.onActivityCreated(savedInstanceBundle);
		
		mAdapter = new ArrayAdapter<ISCourseFile>(getActivity(), android.R.layout.simple_list_item_1);
		setListAdapter(mAdapter);
		
		ISModelManager.getCoursesAsync(getActivity(), new ISCallback< ArrayList<ISCourseFile> >() {

			@Override
			public void execute(ArrayList<ISCourseFile> arg) {
				if (arg == null) {
					new AlertDialog.Builder(getActivity())
						.setTitle("Network Fail")
						.setMessage("Could not list files!")
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {}
						})
						.show();
					return;
				}
				
				mAdapter.clear();
				for (ISCourseFile f : arg) {
					mAdapter.add(f);
				}
			}
			
		});
	}
}
