package com.dynamo.spacex.serviceprovider;

import com.dynamo.spacex.model.PastLaunch;
import com.dynamo.spacex.service.SpaceXService;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SpaceXProvider {

    private final SpaceXService spaceXService;

    public SpaceXProvider(SpaceXService spaceXService) {
        this.spaceXService = spaceXService;
    }

    public Single<List<PastLaunch>> getPastLaunchData(int offset, int limit) {
        return spaceXService.getPastLaunch(offset, limit)
                .compose(applySchedulers());
    }

    private <T> SingleTransformer<T, T> applySchedulers() {
        return single -> single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
