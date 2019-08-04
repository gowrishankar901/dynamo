package com.dynamo.spacex.service

import com.dynamo.spacex.model.PastLaunch
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceXService {

    @GET("launches/past")
    fun getPastLaunch(@Query("offset") offset:Int, @Query("limit") limit:Int): Single<List<PastLaunch>>
}