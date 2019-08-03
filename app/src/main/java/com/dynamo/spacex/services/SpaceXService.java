package com.dynamo.spacex.services;

import com.dynamo.spacex.models.PastLaunch;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SpaceXService {

    @GET("launches/past")
    Single<List<PastLaunch>> getPastLaunch(@Query("limit") int limit);
}
