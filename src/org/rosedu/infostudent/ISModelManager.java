package org.rosedu.infostudent;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class ISModelManager {
	public static ArrayList<String> getCourses() {
		String[] courses = { "AA", "EEA", "PA" };

		return new ArrayList<String>(Arrays.asList(courses));
	}
	
	public static ArrayList<String> getDummyListData(String firstItem) {
		ArrayList<String> dummyList = new ArrayList<String>();
		dummyList.add(firstItem);
		dummyList.add("This is just");
		dummyList.add("a dummy list.");
		
		return dummyList;
	}
}
