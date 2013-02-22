package org.rosedu.infostudent;

import java.util.ArrayList;

import android.content.Context;

public interface ISModel {
	void getCoursesAsync(Context context, ISCallback< ArrayList<ISCourseFile> > callback);
	void listFileCoursesAsync(Context context, String path, ISCallback< ArrayList<ISCourseFile> > callback);
	boolean hasParentDirectory();
	void listParentFileCoursesAsync(Context context, ISCallback< ArrayList<ISCourseFile> > callback);
	String getCoursesPath();
}
