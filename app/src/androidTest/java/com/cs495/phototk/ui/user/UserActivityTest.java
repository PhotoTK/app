package com.cs495.phototk.ui.user;

import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.cs495.phototk.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class UserActivityTest {

    @Rule
    public ActivityTestRule<UserActivity> mActivityRule = new ActivityTestRule(UserActivity.class);
    private UserActivity mActivity = null;
    Instrumentation.ActivityMonitor monitorUser= getInstrumentation().addMonitor(UserActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void testUserSignOutWithoutLoggingIn() {
        assertNotNull(mActivity.findViewById(R.id.btnSignOut));
        onView(withId(R.id.btnSignOut)).perform(click());
    }

    @Test
    public void testUserAccountClickWithoutLoggingIn() {
        assertNotNull(mActivity.findViewById(R.id.btnAccount));
        onView(withId(R.id.btnAccount)).perform(click());
    }

    @Test
    public void testUserSignInClickWithoutLoggingIn() {
        assertNotNull(mActivity.findViewById(R.id.btnSignIn));
        onView(withId(R.id.btnSignIn)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}