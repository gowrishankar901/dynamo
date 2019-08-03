package com.dynamo.spacex.serviceprovider;

import com.dynamo.spacex.models.PastLaunch;
import com.dynamo.spacex.services.SpaceXService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpaceXProvider {

    Gson gson = new GsonBuilder().setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create();
    Retrofit spaceXRetrofit = new Retrofit.Builder()
            .baseUrl("https://api.spacexdata.com/v3")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    SpaceXService spaceXService = spaceXRetrofit.create(SpaceXService.class);

    public Single<List<PastLaunch>> getPastLaunchData(int limit) {
        return spaceXService.getPastLaunch(limit)
                .compose(upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()));
    }
}
