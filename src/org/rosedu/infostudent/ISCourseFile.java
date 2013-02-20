package org.rosedu.infostudent;

import org.apache.commons.net.ftp.FTPFile;

public class ISCourseFile {
	private String mName;
	private String mPath;
	private boolean mIsDirectory;
	
	public ISCourseFile(FTPFile ftpFile, String parentPath) {
		mName = ftpFile.getName();
		mPath = parentPath + "/" + mName;
		mIsDirectory = ftpFile.isDirectory();
	}
	
	public String getPath() {
		return mPath;
	}
	
	public boolean isDirectory() {
		return mIsDirectory;
	}
	
	public String toString() {
		if (mIsDirectory) {
			return "F: " + mName;
		}
		
		return mName;
	}
}
