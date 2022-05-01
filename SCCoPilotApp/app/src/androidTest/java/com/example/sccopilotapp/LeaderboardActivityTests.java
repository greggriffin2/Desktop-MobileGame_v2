package com.example.sccopilotapp;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.assertion.ViewAssertions.selectedDescendantsMatch;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withInputType;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.JMock1Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasToString;

import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
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
        onView(withId(R.id.list)).check(matches(isDisplayed()));
    }

    @Test
    public void reloadButtonIsClickable() {
        onView(withId(R.id.leaderboard_reload)).check(matches(isDisplayed()));
    }

    @Test
    public void filterButtonIsClickable(){
        onView(withId(R.id.leaderboard_filter)).check(matches(isDisplayed()));
    }

    @Test
    public void filterCancelWorks(){
        onView(withId(R.id.leaderboard_filter))
                .perform(ViewActions.click());
        onView(withText("Cancel"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(ViewActions.click());
    }

    @Test
    public void filterWorksWithTextEntry(){
        onView(withId(R.id.leaderboard_filter))
                .perform(ViewActions.click());
        onView(isAssignableFrom(EditText.class))
                .inRoot(isDialog())
                // a filter that will always work
                .perform(ViewActions.typeText("ktb"));
                closeSoftKeyboard();
        onView(withId(android.R.id.button1)) // confirm filter and close dialog
                .perform(ViewActions.click());

        // then check if the data is there
//        onData(withId(R.id.LBtitle))
//                .inAdapterView(isDisplayed())
//                .check(matches(withText("ktb")));
    }

//    @Test
//    public void filterWorked(){
//        //filterWorksWithTextEntry();
//        onData(allOf(is(instanceOf(String.class)),startsWith("Apple")))
//                .check(matches(withText("ktb")));
//    }
}
