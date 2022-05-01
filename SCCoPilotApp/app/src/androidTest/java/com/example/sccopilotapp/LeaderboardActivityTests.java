package com.example.sccopilotapp;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
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
public class LeaderboardActivityTests {
    @Rule
    public ActivityScenarioRule<LeaderboardActivity> activityRule =
            new ActivityScenarioRule<>(LeaderboardActivity.class);

    @Test
    public void leaderBoardIsDisplayedWithDataInIt() {
        onData(withId(R.id.LBtitle))
                .inAdapterView(isDisplayed());
    }
}
