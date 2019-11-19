package com.cs495.phototk.ui.celestial;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.cs495.phototk.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

public class SolarEclipseTest {
    @Rule
    public ActivityTestRule<SolarEclipse> activity1 = new ActivityTestRule(SolarEclipse.class);
    private SolarEclipse action = null;

    @Before
    public void setUp() throws Exception {
        action = activity1.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        action = null;
    }

    @Test
    public void testCheckbox() {
        View view = action.findViewById(R.id.btnone);
        assertNotNull(view);
        onView(withId(R.id.btnone)).perform(click());
    }
}