package com.cs495.phototk;

import android.app.Instrumentation;
import android.widget.Button;

import androidx.test.rule.ActivityTestRule;

import com.cs495.phototk.ui.map.MapsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class MapsActivityTest {
    @Rule
    public ActivityTestRule<MapsActivity> mActivityRule = new ActivityTestRule(MapsActivity.class);
    private MapsActivity mActivity = null;
    Instrumentation.ActivityMonitor monitorUser= getInstrumentation().addMonitor(MapsActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void userNotSignedInSaveTest() {
        Button btnSave = mActivity.findViewById(R.id.btnSaveLocation);
        assertNotNull(btnSave);
        onView(withId(R.id.btnSaveLocation)).perform(click());
    }

    @Test
    public void userNotSignedInClearTest() {
        Button btnClear = mActivity.findViewById(R.id.btnClearLocations);
        assertNotNull(btnClear);
        onView(withId(R.id.btnClearLocations)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}
