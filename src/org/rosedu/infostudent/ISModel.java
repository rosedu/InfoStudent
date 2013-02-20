package org.rosedu.infostudent;

import java.util.ArrayList;

import android.content.Context;

public interface ISModel {
	public void getCoursesAsync(Context context, ISCallback< ArrayList<ISCourseFile> > callback);
	public void listFileCoursesAsync(Context context, String path, ISCallback< ArrayList<ISCourseFile> > callback);
}
