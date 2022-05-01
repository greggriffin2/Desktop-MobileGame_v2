package com.example.sccopilotapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class SettingsActivityTests {
    @Rule
    public ActivityScenarioRule<SettingsActivity> activityRule =
            new ActivityScenarioRule<>(SettingsActivity.class);

    @Test
    public void settingsButtonIsClickable() {
        onView(withId(R.id.toggleButton))
                .check(matches(isClickable()));
    }

    @Test
    public void backButtonIsClickable() {
        onView(withId(R.id.home))
                .check(matches(isClickable()));
    }
}
