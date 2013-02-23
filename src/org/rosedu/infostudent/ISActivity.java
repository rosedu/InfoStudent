package org.rosedu.infostudent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.View;

public class ISActivity extends FragmentActivity {
	private final static String mInfoTabTag = "info";
	private final static String mCoursesTabTag = "courses";
	private final static String mCalendarTabTag = "calendar";
	private final static String mScheduleTabTag = "schedule";

	private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		mTabHost.setPadding(0, mTabHost.getPaddingTop(), 0, mTabHost.getPaddingBottom());

		mTabHost.addTab(mTabHost.newTabSpec(mInfoTabTag).setIndicator(getString(R.string.info)),
				ISFragmentInfo.class, null);
		mTabHost.addTab(mTabHost.newTabSpec(mCoursesTabTag).setIndicator(getString(R.string.courses)),
				ISFragmentCourses.class, null);
		mTabHost.addTab(mTabHost.newTabSpec(mCalendarTabTag).setIndicator(getString(R.string.calendar)),
				ISFragmentCalendar.class, null);
		mTabHost.addTab(mTabHost.newTabSpec(mScheduleTabTag).setIndicator(getString(R.string.schedule)),
				ISFragmentSchedule.class, null);

		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); ++i) {
			View v = mTabHost.getTabWidget().getChildTabViewAt(i);
			v.setPadding(0, v.getPaddingTop(), 0, v.getPaddingBottom());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.i, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		Fragment courses = getSupportFragmentManager().findFragmentByTag(mCoursesTabTag);

		if (courses instanceof ISFragmentCourses) {
			if (!((ISFragmentCourses)courses).onBackPressed()) {
				super.onBackPressed();
			}

			return;
		}

		super.onBackPressed();
	}

	public void displayErrorDialog(int stringId) {
		new AlertDialog.Builder(this)
		.setTitle(getString(stringId))
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {}
		})
		.show();
	}
}
