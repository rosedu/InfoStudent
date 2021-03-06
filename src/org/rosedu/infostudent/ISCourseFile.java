package org.rosedu.infostudent;

import org.apache.commons.net.ftp.FTPFile;

public class ISCourseFile {
	private final static String mLocalCourseDir = "CourseFiles";

	private String mName;
	private String mParentPath;
	private boolean mIsDirectory;

	public ISCourseFile(FTPFile ftpFile, String parentPath) {
		mName = ftpFile.getName();
		mParentPath = parentPath;
		mIsDirectory = ftpFile.isDirectory();
	}


	public String getPath() {
		if (mParentPath.charAt(mParentPath.length() - 1) == '/') {
			return mParentPath + mName;
		}

		return mParentPath + "/" + mName;
	}

	public String getParentPath() {
		return mParentPath;
	}

	public boolean isDirectory() {
		return mIsDirectory;
	}

	public String getLocalRelativePath() {
		return mLocalCourseDir + getPath();
	}

	public static String getLocalCourseDirName() {
		return mLocalCourseDir;
	}

	public String toString() {
		if (mIsDirectory) {
			return "F: " + mName;
		}

		return mName;
	}
}
