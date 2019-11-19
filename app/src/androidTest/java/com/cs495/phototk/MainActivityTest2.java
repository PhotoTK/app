package com.cs495.phototk;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.cs495.phototk.ui.management.ManagementActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class MainActivityTest2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);
    private MainActivity mActivity = null;
    Instrumentation.ActivityMonitor monitor_exif = getInstrumentation().addMonitor(ManagementActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void testLaunch()
    {
        View view = mActivity.findViewById(R.id.main_center);
        assertNotNull(view);
    }

    @Test
    public void testImageClick_exif()
    {
        assertNotNull(mActivity.findViewById(R.id.home_management));
        onView(withId(R.id.home_management)).perform(click());
        Activity exifActivity = getInstrumentation().waitForMonitorWithTimeout(monitor_exif,5000);
        assertNotNull(exifActivity);
        exifActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}