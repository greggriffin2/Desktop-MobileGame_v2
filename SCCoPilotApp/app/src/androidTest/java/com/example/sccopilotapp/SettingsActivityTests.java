package com.example.sccopilotapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBackUnconditionally;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Authored by Greg Griffin
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

    /**
     * Tests user story #31
     */
    @Test
    public void backButtonIsClickable() {
        pressBackUnconditionally();
    }
}
