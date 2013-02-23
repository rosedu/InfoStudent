package org.rosedu.infostudent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;

public abstract class ISModelManager {
	private static ISModelNetwork mModelNetwork;

	private static ISModel getModel() {
		if (mModelNetwork == null) {
			mModelNetwork = new ISModelNetwork();
		}

		return mModelNetwork;
	}

	public static ArrayList<String> getCourses() {
		String[] courses = { "AA", "EEA", "PA" };

		return new ArrayList<String>(Arrays.asList(courses));
	}

	public static void getCoursesAsync(Context context, ISCallback< ArrayList<ISCourseFile> > callback) {
		getModel().getCoursesAsync(context, callback);
	}

	public static void listCourseFilesAsync(Context context, String path, ISCallback< ArrayList<ISCourseFile> > callback) {
		getModel().listCourseFilesAsync(context, path, callback);
	}

	public static boolean hasParentDirectory() {
		return getModel().hasParentDirectory();
	}

	public static void listParentFileCoursesAsync(Context context, ISCallback< ArrayList<ISCourseFile> > callback) {
		getModel().listCourseFilesFromParentAsync(context, callback);
	}

	public static String getCoursesPath() {
		return getModel().getCoursesPath();
	}

	public static ArrayList<String> getDummyListData(String firstItem) {
		ArrayList<String> dummyList = new ArrayList<String>();
		dummyList.add(firstItem);
		dummyList.add("This is just");
		dummyList.add("a dummy list.");

		return dummyList;
	}

	public static void getCourseFileAsync(Context context, ISCourseFile isCourseFile, ISCallback<File> callback) {
		File filesDir = context.getExternalFilesDir(null);
		if (filesDir == null) {
			filesDir = context.getFilesDir();
		}

		File courseFile = new File(filesDir.getAbsolutePath(), isCourseFile.getLocalRelativePath());
		if (courseFile.exists()) {
			callback.execute(courseFile);
			return;
		}

		if (!courseFile.getParentFile().mkdirs()) {
			callback.execute(null);
			return;
		}

		getModel().getCourseFileAsync(context, isCourseFile.getPath(), courseFile, callback);
	}
}
