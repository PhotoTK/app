package com.cs495.phototk.ui.user;

import android.app.Instrumentation;
import android.widget.Button;

import androidx.test.rule.ActivityTestRule;

import com.cs495.phototk.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertNotNull;

public class UserRegisterTest {
    @Rule
    public ActivityTestRule<RegisterActivity> mActivityRule = new ActivityTestRule(RegisterActivity.class);
    private RegisterActivity mActivity = null;
    Instrumentation.ActivityMonitor monitorUser= getInstrumentation().addMonitor(RegisterActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void registerWithInvalidEmail() {
        Button btnRegister = mActivity.findViewById(R.id.register_button);
        assertNotNull(btnRegister);
        onView(withId(R.id.email)).perform(typeText("email"));
        onView(withId(R.id.password)).perform(typeText("password"));
        onView(withId(R.id.confirm_password)).perform(typeText("password"));
        onView(withId(R.id.register_button)).perform(click());
    }

    @Test
    public void registerWithInvalidPassword() {
        Button btnRegister = mActivity.findViewById(R.id.register_button);
        assertNotNull(btnRegister);
        onView(withId(R.id.email)).perform(typeText("test123@email.com"));
        onView(withId(R.id.password)).perform(typeText("pwd"));
        onView(withId(R.id.confirm_password)).perform(typeText("pwd"));
        onView(withId(R.id.register_button)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}
