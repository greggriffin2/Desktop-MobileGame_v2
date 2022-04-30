package com.example.sccopilotapp;

import static androidx.test.espresso.Espresso.onView;
import static org.junit.Assert.assertEquals;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import android.view.View;

import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing%22%3ETesting documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityUnitTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void validateConnectionTest(){
        
    }
//    @Test
//    public void tenClicksDisplaysToast(){
//        onView(withId(R.id.shipClick))
//                .perform(click());
//    }
}