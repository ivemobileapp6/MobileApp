package com.example.mobileapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void checkLoginActivityDisplayed() {
        onView(withId(R.id.et_email)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.et_password)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.btn_login)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.btn_register)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.btn_fingerprint_login)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testLoginButtonClick() {
        onView(withId(R.id.btn_login)).perform(ViewActions.click());
        // Add assertions to check the result of the login button click
    }

    @Test
    public void testRegisterButtonClick() {
        onView(withId(R.id.btn_register)).perform(ViewActions.click());
        // Add assertions to check the result of the register button click
    }

    @Test
    public void testFingerprintLoginButtonClick() {
        onView(withId(R.id.btn_fingerprint_login)).perform(ViewActions.click());
        // Add assertions to check the result of the fingerprint login button click
    }
}