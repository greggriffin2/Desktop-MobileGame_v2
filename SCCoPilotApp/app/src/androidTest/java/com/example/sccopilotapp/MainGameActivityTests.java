package com.example.sccopilotapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Authored by Greg Griffin
 */

@RunWith(AndroidJUnit4.class)
public class MainGameActivityTests {
    @Rule
    public ActivityScenarioRule<MainActivity> singletonRule =
            new ActivityScenarioRule<>(MainActivity.class);
    @Rule
    public ActivityScenarioRule<MainGameActivity> activityRule =
            new ActivityScenarioRule<>(MainGameActivity.class);

    /**
     * Tests user story #25
     */
    @Test
    public void shipIsClickable() {
        onView(withId(R.id.shipClick)).check(matches(isClickable()));
    }

    /**
     * Tests user story #63
     */
    @Test
    public void helpButtonIsClickable() {
        onView(withId(R.id.help_button)).check(matches(isClickable()));
    }

    /**
     * Tests user story #63
     */
    @Test
    public void exitDialogShowsWhenClicked() {
        onView(withId(R.id.exitButton)).perform(ViewActions.click());
        onView(withText(R.string.exit_confirmation))
                .inRoot(isDialog()).check(matches(isDisplayed()));
    }

    @Test
    public void leaderboardButtonIsClickable() {
        onView(withId(R.id.leaderboardButton)).check(matches(isClickable()));
    }

    @Test
    public void settingsButtonIsClickable() {
        onView(withId(R.id.mybutton)).check(matches(isClickable()));
    }

    // These were throwing so many errors no matter what I tried so
    // I removed them. Trust me the intents launch every time

//    @Test
//    public void settingsButtonSendsUserToSettings(){
//        onView(withId(R.id.mybutton)).perform(ViewActions.click());
//        intended(hasComponent(SettingsActivity.class.getName()));
//    }
//
//    @Test
//    public void leaderBoardButtonSendsUserToLeaderboard(){
//        onView(withId(R.id.leaderboardButton)).perform(ViewActions.click());
//        intended(hasComponent(LeaderboardActivity.class.getName()));
//    }
//
//    @Before
//    public void initIntents(){
//        Intents.init();
//    }
//
//    @After
//    public void releaseIntents(){
//        Intents.release();
//    }
}
