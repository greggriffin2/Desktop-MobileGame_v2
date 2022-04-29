package com.example.sccopilotapp;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class MainGameActivityTests {
    @Rule
    public ActivityScenarioRule<MainGameActivity> activityRule =
            new ActivityScenarioRule<>(MainGameActivity.class);

    private View decorView;

    /**
     * Instantiates a decorView in order to retrieve data about the Toast we want to test
     */
    @Before
    public void setUp() {
        activityRule.getScenario().onActivity(activity ->
                decorView = activity.getWindow().getDecorView());
    }

    @Test
    /* TODO: This test does not work because of a null object reference when
        fireButtonPressed() is called to the GameSyncSingleton. How can we disable (or incorporate)
        the game sync for testing of the UI?
     */
    public void tenClicksDisplaysToast(){
        String expectedWarning = getApplicationContext().getString(R.string.toast_text);
        ViewInteraction ship = onView(withId(R.id.shipClick));
        for(int i = 0; i < 10; i++){
            ship.perform(ViewActions.click()); // clicks 10 times to trigger Toast
        }
        onView(withText(expectedWarning))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }
}
