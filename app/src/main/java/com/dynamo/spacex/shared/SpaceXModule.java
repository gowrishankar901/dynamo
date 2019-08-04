package com.dynamo.spacex.shared;

import com.dynamo.spacex.service.SpaceXService;
import com.dynamo.spacex.serviceprovider.SpaceXProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class SpaceXModule {

    @Singleton
    @Provides
    Retrofit provideRetrofitService(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("https://api.spacexdata.com/v3/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    Gson provideGson() {
            return new GsonBuilder().setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create();
    }

    @Singleton
    @Provides
    SpaceXService provideSpaceXService(Retrofit retrofit) {
        return retrofit.create(SpaceXService.class);
    }

    @Singleton
    @Provides
    SpaceXProvider provideSpaceXProvider(SpaceXService spaceXService) {
        return new SpaceXProvider(spaceXService);
    }
}
