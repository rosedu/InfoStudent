package org.rosedu.infostudent;

import java.net.InetAddress;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

public class ISModelNetwork implements ISModel {
	private final static String mFTPServerName = "192.168.1.2"; // for DEV: "localhost" will not work
	
	public void getCoursesAsync(Context context, ISCallback< ArrayList<ISCourseFile> > callback) {
		listFileCoursesAsync(context, "", callback);
	}
	
	public void listFileCoursesAsync(Context context, String path, final ISCallback< ArrayList<ISCourseFile> > callback) {
		NetworkInfo networkInfo = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		
		if (networkInfo == null || !networkInfo.isConnected()) {
			callback.execute(null);
			return;
		}
		
		new AsyncTask< String, Integer, ArrayList<ISCourseFile> >() {

			@Override
			protected ArrayList<ISCourseFile> doInBackground(String... params) {
				String path = params[0];
				
				FTPClient ftpClient = new FTPClient();
				try {
					ftpClient.connect(InetAddress.getByName(mFTPServerName));
					ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
					ftpClient.enterLocalPassiveMode();
					
					if (!ftpClient.login("anonymous", "")) {
						return null;
					}
					
					FTPFile[] files = ftpClient.listFiles(path);
					ArrayList<ISCourseFile> isCourseFiles = new ArrayList<ISCourseFile>();
					
					for (int i = 0; i < files.length; ++i) {
						isCourseFiles.add(new ISCourseFile(files[i], path));
					}
					
					return isCourseFiles;
				} catch (Exception e) {
					;
				}
				
				return null;
			}
			
			@Override
			public void onPostExecute(ArrayList<ISCourseFile> isCourseFiles) {
				callback.execute(isCourseFiles);
			}
		}.execute(path);
	}
}
