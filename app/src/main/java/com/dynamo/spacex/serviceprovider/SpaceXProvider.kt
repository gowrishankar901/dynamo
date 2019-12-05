package com.dynamo.spacex.serviceprovider

import com.dynamo.spacex.model.PastLaunch
import com.dynamo.spacex.service.SpaceXService
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SpaceXProvider constructor(
        private val spaceXService: SpaceXService
) {
    fun getPastLaunchData(offset: Int, limit: Int): Single<List<PastLaunch>> {
        return spaceXService.getPastLaunch(offset, limit).compose(applySchedulers())
    }

    private fun <T> applySchedulers(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }
}