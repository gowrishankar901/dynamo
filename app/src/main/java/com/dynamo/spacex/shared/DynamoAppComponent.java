package com.dynamo.spacex.shared;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
        SpaceXModule.class,
        ActivityModule.class,
        AndroidSupportInjectionModule.class
})
@Singleton
public interface DynamoAppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        DynamoAppComponent build();
    }

    void inject(DynamoApplication dynamoApplication);
}
