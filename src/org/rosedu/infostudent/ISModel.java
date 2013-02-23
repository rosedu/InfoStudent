package org.rosedu.infostudent;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;

public interface ISModel {
	void getCoursesAsync(Context context, ISCallback< ArrayList<ISCourseFile> > callback);
	void listCourseFilesAsync(Context context, String path, ISCallback< ArrayList<ISCourseFile> > callback);
	boolean hasParentDirectory();
	void listCourseFilesFromParentAsync(Context context, ISCallback< ArrayList<ISCourseFile> > callback);
	String getCoursesPath();
	void getCourseFileAsync(Context context, String path, File courseFile, ISCallback<File> callback);
}
