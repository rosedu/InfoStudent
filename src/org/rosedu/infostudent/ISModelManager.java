package org.rosedu.infostudent;

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

	public static void listFileCoursesAsync(Context context, String path, ISCallback< ArrayList<ISCourseFile> > callback) {
		getModel().listFileCoursesAsync(context, path, callback);
	}

	public static boolean hasParentDirectory() {
		return getModel().hasParentDirectory();
	}

	public static void listParentFileCoursesAsync(Context context, ISCallback< ArrayList<ISCourseFile> > callback) {
		getModel().listParentFileCoursesAsync(context, callback);
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
}
