package com.cs495.phototk.ui.user;

import android.app.Instrumentation;
import android.view.View;
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
import static org.junit.Assert.*;

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule(LoginActivity.class);
    private LoginActivity mActivity = null;
    Instrumentation.ActivityMonitor monitorLogin = getInstrumentation().addMonitor(LoginActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void testContinueWithoutLogginIn() {
        Button btnContinue = mActivity.findViewById(R.id.continue_without_logging_in_button);
        assertNotNull(btnContinue);
        onView(withId(R.id.continue_without_logging_in_button)).perform(click());
    }

    @Test
    public void testLoginInvalidEmail() {
        Button btnSignIn = mActivity.findViewById(R.id.email_sign_in_button);
        assertNotNull(btnSignIn);
        onView(withId(R.id.email)).perform(typeText("test"));
        onView(withId(R.id.password)).perform(typeText("pass"));
        onView(withId(R.id.email_sign_in_button)).perform(click());
    }

    @Test
    public void testLoginWrongEmail() {
        Button btnSignIn = mActivity.findViewById(R.id.email_sign_in_button);
        assertNotNull(btnSignIn);
        onView(withId(R.id.email)).perform(typeText("thisEmailDoesNotExist@email.com"));
        onView(withId(R.id.password)).perform(typeText("pass"));
        onView(withId(R.id.email_sign_in_button)).perform(click());
    }

    @Test
    public void testLoginWrongPassword() {
        Button btnSignIn = mActivity.findViewById(R.id.email_sign_in_button);
        assertNotNull(btnSignIn);
        onView(withId(R.id.email)).perform(typeText("test@email.com"));
        onView(withId(R.id.password)).perform(typeText("pass"));
        onView(withId(R.id.email_sign_in_button)).perform(click());
    }

    @Test
    public void testLoginCorrectPassword() {
        Button btnSignIn = mActivity.findViewById(R.id.email_sign_in_button);
        assertNotNull(btnSignIn);
        onView(withId(R.id.email)).perform(typeText("test@email.com"));
        onView(withId(R.id.password)).perform(typeText("password"));
        onView(withId(R.id.email_sign_in_button)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}