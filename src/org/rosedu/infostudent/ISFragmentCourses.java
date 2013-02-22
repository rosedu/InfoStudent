package org.rosedu.infostudent;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ISFragmentCourses extends ListFragment {
	private ArrayAdapter<ISCourseFile> mAdapter;
	private ISCallback< ArrayList<ISCourseFile> > mPopulateListCallback;

	@Override
	public void onActivityCreated(Bundle savedInstanceBundle) {
		super.onActivityCreated(savedInstanceBundle);
		this.setRetainInstance(true);

		mAdapter = new ArrayAdapter<ISCourseFile>(getActivity(), android.R.layout.simple_list_item_1);
		setListAdapter(mAdapter);

		mPopulateListCallback = new ISCallback< ArrayList<ISCourseFile> >() {

			@Override
			public void execute(ArrayList<ISCourseFile> arg) {
				if (arg == null) {
					new AlertDialog.Builder(getActivity())
						.setTitle(getString(R.string.network_fail))
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

		};

		if (savedInstanceBundle == null) {
			ISModelManager.getCoursesAsync(getActivity(), mPopulateListCallback);
		} else {
			ISModelManager.listFileCoursesAsync(getActivity(), savedInstanceBundle.getString("currentPath"), mPopulateListCallback);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("currentPath", ISModelManager.getCoursesPath());
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		ISCourseFile file = mAdapter.getItem(position);

		if (file.isDirectory()) {
			ISModelManager.listFileCoursesAsync(getActivity(), mAdapter.getItem(position).getPath(), mPopulateListCallback);
			return;
		}
	}

	public boolean onBackPressed() {
		if (ISModelManager.hasParentDirectory()) {
			ISModelManager.listParentFileCoursesAsync(ISFragmentCourses.this.getActivity(), ISFragmentCourses.this.mPopulateListCallback);
			return true;
		}

		return false;
	}
}
