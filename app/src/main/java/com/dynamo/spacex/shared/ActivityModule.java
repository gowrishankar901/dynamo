package com.dynamo.spacex.shared;

import com.dynamo.spacex.LaunchDetailsActivity;
import com.dynamo.spacex.SpaceXListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract SpaceXListActivity contributeSpaceXListActivity();

    @ContributesAndroidInjector()
    abstract LaunchDetailsActivity contributeLaunchDetailsActivity();
}
