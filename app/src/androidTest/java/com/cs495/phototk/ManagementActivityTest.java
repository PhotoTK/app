package com.cs495.phototk;

import android.app.Activity;
import com.cs495.phototk.ui.management.GearEdit;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.cs495.phototk.ui.management.ManagementActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.os.SystemClock;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertNotNull;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;




@RunWith(AndroidJUnit4.class)
@LargeTest
public class ManagementActivityTest {

    @Rule
    public ActivityTestRule<ManagementActivity> mActivityRule = new ActivityTestRule(ManagementActivity.class);
    private ManagementActivity mActivity = null;
    Instrumentation.ActivityMonitor addGear = getInstrumentation().addMonitor(GearEdit.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void testAddGearLaunch()
    {
        View view = mActivity.findViewById(R.id.button_AddGear);
        assertNotNull(view);
        onView(withId(R.id.button_AddGear)).perform(click());
        Activity GearEdit = getInstrumentation().waitForMonitorWithTimeout(addGear,5000);
        assertNotNull(GearEdit);
    }

    @Test
    public void testEditText()
    {
        View view = mActivity.findViewById(R.id.button_AddGear);
        assertNotNull(view);
        onView(withId(R.id.button_AddGear)).perform(click());
        onView(withId(R.id.edit_item_name)).perform(typeText("Cool Cam"));
        onView(withId(R.id.edit_owner_name)).perform(typeText("Min"));
        onView(withId(R.id.edit_item_price)).perform(typeText("8500.55"));
        onView(withId(R.id.edit_insurance_date)).perform(typeText("2022/5/5"));
        onView(withId(R.id.edit_warranty_date)).perform(typeText("2022/6/6"));
        onView(withId(R.id.edit_detail)).perform(typeText("testing"));
    }

    @Test
    public void testListItemLongClick()
    {
        SystemClock.sleep(1000);
        assertNotNull(mActivity.findViewById(R.id.list_view_gears));
        onData(anything()).inAdapterView(withId(R.id.list_view_gears)).atPosition(0).perform(longClick());
        onView(withText("Delete the selected gear?")).check(matches(isDisplayed()));
    }

    @Test
    public void testGearEdit()
    {
        SystemClock.sleep(1000);
        assertNotNull(mActivity.findViewById(R.id.list_view_gears));
        onData(anything()).inAdapterView(withId(R.id.list_view_gears)).atPosition(0).perform(click());
        Activity GearEdit = getInstrumentation().waitForMonitorWithTimeout(addGear,5000);
        assertNotNull(GearEdit);
        GearEdit.finish();
    }



    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}