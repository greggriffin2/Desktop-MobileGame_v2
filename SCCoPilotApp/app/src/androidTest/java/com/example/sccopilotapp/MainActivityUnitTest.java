package com.example.sccopilotapp;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
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
public class MainActivityUnitTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);
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
    public void doesNotContainedNumbersToast(){
        String numberInputCode = "gregsucks69";
        String expectedWarning1 = getApplicationContext().getString(R.string.stringWithNumber);
        ViewInteraction play = onView(withId(R.id.playButton));
        ViewInteraction inputCode = onView(withId(R.id.inputCode));
        inputCode.perform(ViewActions.typeText(numberInputCode));
        closeSoftKeyboard();
        play.perform(ViewActions.click());
        onView(withText(expectedWarning1))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }
    @Test
    public void doesNotContainSpacesToast(){
        String spaceInputCode = "greg sucks";
        String expectedWarning2 = getApplicationContext().getString(R.string.stringWithSpaces);
        ViewInteraction play = onView(withId(R.id.playButton));
        ViewInteraction inputCode = onView(withId(R.id.inputCode));
        inputCode.perform(ViewActions.typeText(spaceInputCode));
        closeSoftKeyboard();
        play.perform(ViewActions.click());
        onView(withText(expectedWarning2))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }
    @Test
    public void inputEmptyDisplaysToast(){
        String emptyInputCode = " ";
        String expectedWarning4 = getApplicationContext().getString(R.string.incorrectCode);
        ViewInteraction play = onView(withId(R.id.playButton));
        ViewInteraction inputCode = onView(withId(R.id.inputCode));
        inputCode.perform(ViewActions.typeText(emptyInputCode));
        closeSoftKeyboard();
        play.perform(ViewActions.click());
        onView(withText(expectedWarning4))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }
}