package org.rosedu.infostudent;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class ISModelNetwork implements ISModel {
	private final static String mFTPServerName = "192.168.1.2"; // for DEV: "localhost" will not work
	private final static String mFTPRoot = "/";

	private String mCurrentFTPPath = mFTPRoot;

	private boolean checkConnectivity(Context context) {
		NetworkInfo networkInfo = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

		if (networkInfo == null || !networkInfo.isConnected()) {
			return false;
		}

		return true;
	}

	private FTPClient getFTPClient() throws SocketException, UnknownHostException, IOException {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(InetAddress.getByName(mFTPServerName));
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.enterLocalPassiveMode();

		if (!ftpClient.login("anonymous", "")) {
			return null;
		}

		return ftpClient;
	}

	private ArrayList<ISCourseFile> getCourseFiles(FTPClient ftpClient, String path) throws IOException {
		FTPFile[] files = null;

		if (path != null) {
			files = ftpClient.listFiles(path);
		} else {
			files = ftpClient.listFiles();
			path = ftpClient.printWorkingDirectory();
			path = path.substring(1, path.length() - 1);
		}
		ftpClient.disconnect();

		ArrayList<ISCourseFile> isCourseFiles = new ArrayList<ISCourseFile>();

		for (int i = 0; i < files.length; ++i) {
			isCourseFiles.add(new ISCourseFile(files[i], path));
		}

		return isCourseFiles;
	}

	@Override
	public void getCoursesAsync(Context context, ISCallback< ArrayList<ISCourseFile> > callback) {
		listCourseFilesAsync(context, mFTPRoot, callback);
	}

	@Override
	public void listCourseFilesAsync(Context context, String path, final ISCallback< ArrayList<ISCourseFile> > callback) {
		if (!checkConnectivity(context)) {
			callback.execute(null);
			return;
		}

		if (path == null) {
			path = mCurrentFTPPath;
		}

		new AsyncTask< String, Integer, ArrayList<ISCourseFile> >() {

			@Override
			protected ArrayList<ISCourseFile> doInBackground(String... params) {
				String path = params[0];
				String oldFTPPath = mCurrentFTPPath;
				mCurrentFTPPath = path;

				try {
					return getCourseFiles(getFTPClient(), path);
				} catch (Exception e) {
					mCurrentFTPPath = oldFTPPath;
					Log.println(Log.ERROR, "listPath", e.toString());
				}

				return null;
			}

			@Override
			public void onPostExecute(ArrayList<ISCourseFile> isCourseFiles) {
				callback.execute(isCourseFiles);
			}
		}.execute(path);
	}

	@Override
	public boolean hasParentDirectory() {
		return (!mCurrentFTPPath.equals(mFTPRoot));
	}

	@Override
	public void listCourseFilesFromParentAsync(Context context, final ISCallback< ArrayList<ISCourseFile> > callback) {
		if (!hasParentDirectory()) {
			return;
		}

		if (!checkConnectivity(context)) {
			callback.execute(null);
			return;
		}

		new AsyncTask< String, Integer, ArrayList<ISCourseFile> >() {

			@Override
			protected ArrayList<ISCourseFile> doInBackground(String... params) {
				String oldFTPPath = mCurrentFTPPath;

				try {
					FTPClient ftpClient = getFTPClient();
					if (!ftpClient.changeWorkingDirectory(mCurrentFTPPath) || !ftpClient.changeToParentDirectory()) {
						mCurrentFTPPath = oldFTPPath;
						return null;
					}

					mCurrentFTPPath = ftpClient.printWorkingDirectory();
					mCurrentFTPPath = mCurrentFTPPath.substring(1, mCurrentFTPPath.length() - 1);

					return getCourseFiles(ftpClient, null);
				} catch (Exception e) {
					mCurrentFTPPath = oldFTPPath;
					Log.println(Log.ERROR, "listParent", e.toString());
				}

				return null;
			}

			@Override
			public void onPostExecute(ArrayList<ISCourseFile> isCourseFiles) {
				callback.execute(isCourseFiles);
			}
		}.execute();
	}

	@Override
	public String getCoursesPath() {
		return mCurrentFTPPath;
	}

	@Override
	public void getCourseFileAsync(Context context, String path, File courseFile, final ISCallback<File> callback) {
		if (!checkConnectivity(context)) {
			callback.execute(null);
			return;
		}

		new AsyncTask< Object, Integer, File >() {

			@Override
			protected File doInBackground(Object... params) {
				String path = (String)params[0];
				File courseFile = (File)params[1];

				try {
					FTPClient ftpClient = getFTPClient();
					BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(courseFile));
					if (!ftpClient.retrieveFile(path, outputStream)) {
						courseFile = null;
					}

					ftpClient.disconnect();
					outputStream.close();

					return courseFile; // might be null because of above "if"
				} catch (Exception e) {
					Log.println(Log.ERROR, "getCourseFile", e.toString());
				}

				return null;
			}

			@Override
			public void onPostExecute(File courseFile) {
				callback.execute(courseFile);
			}
		}.execute(path, courseFile);
	}
}
