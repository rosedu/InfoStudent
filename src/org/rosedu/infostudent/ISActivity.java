package org.rosedu.infostudent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.View;

public class ISActivity extends FragmentActivity {
	private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		mTabHost.setPadding(0, mTabHost.getPaddingTop(), 0, mTabHost.getPaddingBottom());
		
		mTabHost.addTab(mTabHost.newTabSpec("info").setIndicator(getString(R.string.info)), 
				ISFragmentInfo.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("courses").setIndicator(getString(R.string.courses)),
				ISFragmentCourses.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("calendar").setIndicator(getString(R.string.calendar)),
				ISFragmentCalendar.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("schedule").setIndicator(getString(R.string.schedule)),
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
}
