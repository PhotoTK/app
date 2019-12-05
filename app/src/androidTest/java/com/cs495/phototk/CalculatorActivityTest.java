package com.cs495.phototk;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.SystemClock;
import android.view.View;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.cs495.phototk.ui.calculator.CalculatorActivity;
import com.cs495.phototk.ui.management.GearEdit;
import com.cs495.phototk.ui.management.ManagementActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CalculatorActivityTest {

    @Rule
    public ActivityTestRule<CalculatorActivity> mActivityRule = new ActivityTestRule(CalculatorActivity.class);
    private CalculatorActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void testCalculateNon()
    {
        View view = mActivity.findViewById(R.id.btnCalculate);
        assertNotNull(view);
        onView(withId(R.id.btnCalculate)).perform(click());
    }

    @Test
    public void testEV()
    {
        mActivity = mActivityRule.getActivity();
        onView(withId(R.id.aperture)).perform(typeText("5.6"));
        onView(withId(R.id.speed)).perform(typeText("0.00125"));
        onView(withId(R.id.iso)).perform(typeText("200"),closeSoftKeyboard());
        View view = mActivity.findViewById(R.id.btnCalculate);
        assertNotNull(view);
        onView(withId(R.id.btnCalculate)).perform(click());
        onView(withText("EV = 13.61")).check(matches(isDisplayed()));
    }


    @Test
    public void testEV2()
    {
        mActivity = mActivityRule.getActivity();
        onView(withId(R.id.aperture)).perform(typeText("1"));
        onView(withId(R.id.speed)).perform(typeText("0"));
        onView(withId(R.id.iso)).perform(typeText("0"),closeSoftKeyboard());
        View view = mActivity.findViewById(R.id.btnCalculate);
        assertNotNull(view);
        onView(withId(R.id.btnCalculate)).perform(click());
        onView(withText("EV = Infinity")).check(matches(isDisplayed()));

    }

    @Test
    public void testEV3()
    {
        mActivity = mActivityRule.getActivity();
        onView(withId(R.id.aperture)).perform(typeText("0"));
        onView(withId(R.id.speed)).perform(typeText("0"));
        onView(withId(R.id.iso)).perform(typeText("0"),closeSoftKeyboard());
        View view = mActivity.findViewById(R.id.btnCalculate);
        assertNotNull(view);
        onView(withId(R.id.btnCalculate)).perform(click());
        onView(withText("EV = NaN")).check(matches(isDisplayed()));

    }


    @Test
    public void testEV5()
    {
        mActivity = mActivityRule.getActivity();
        onView(withId(R.id.aperture)).perform(typeText("64"));
        onView(withId(R.id.speed)).perform(typeText("30"));
        onView(withId(R.id.iso)).perform(typeText("100"),closeSoftKeyboard());
        View view = mActivity.findViewById(R.id.btnCalculate);
        assertNotNull(view);
        onView(withId(R.id.btnCalculate)).perform(click());
        onView(withText("EV = 7.09")).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}
