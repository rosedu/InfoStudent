package org.rosedu.infostudent;

import java.io.File;
import java.util.ArrayList;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
			public void execute(ArrayList<ISCourseFile> files) {
				if (files == null) {
					((ISActivity)getActivity()).displayErrorDialog(R.string.network_fail);
					return;
				}

				mAdapter.clear();
				for (ISCourseFile f : files) {
					mAdapter.add(f);
				}
			}

		};

		if (savedInstanceBundle == null) {
			ISModelManager.getCoursesAsync(getActivity(), mPopulateListCallback);
		} else {
			ISModelManager.listCourseFilesAsync(getActivity(), savedInstanceBundle.getString("currentPath"), mPopulateListCallback);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("currentPath", ISModelManager.getCoursesPath());
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		ISCourseFile isCourseFile = mAdapter.getItem(position);

		if (isCourseFile.isDirectory()) {
			ISModelManager.listCourseFilesAsync(getActivity(), mAdapter.getItem(position).getPath(), mPopulateListCallback);
			return;
		}

		ISModelManager.getCourseFileAsync(getActivity(), isCourseFile,
				new ISCallback<File>() {

					@Override
					public void execute(File courseFile) {
						if (courseFile == null) {
							((ISActivity)getActivity()).displayErrorDialog(R.string.file_display_fail);
							return;
						}

	                    Intent intent = new Intent(Intent.ACTION_VIEW);
	                    intent.setDataAndType(Uri.fromFile(courseFile),
	                    		MimeTypeMap.getSingleton().getMimeTypeFromExtension(
	                    				MimeTypeMap.getFileExtensionFromUrl(courseFile.getName())));
	                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

	                    try {
	                        startActivity(intent);
	                    }
	                    catch (ActivityNotFoundException e) {
	                        Toast.makeText(getActivity(),
	                            "Nu pot afisa",
	                            Toast.LENGTH_SHORT).show();
	                    }
					}

		});
	}

	public boolean onBackPressed() {
		if (ISModelManager.hasParentDirectory()) {
			ISModelManager.listParentFileCoursesAsync(ISFragmentCourses.this.getActivity(), ISFragmentCourses.this.mPopulateListCallback);
			return true;
		}

		return false;
	}
}
