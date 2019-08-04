package com.dynamo.spacex;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SpaceXListActivityTest {

    @Rule
    public ActivityTestRule<SpaceXListActivity> activityTestRule = new ActivityTestRule<>(SpaceXListActivity.class);

}