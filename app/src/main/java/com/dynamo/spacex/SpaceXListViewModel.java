package com.dynamo.spacex;

import android.text.TextUtils;

import androidx.lifecycle.ViewModel;

import com.dynamo.spacex.serviceprovider.SpaceXProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

class SpaceXListViewModel extends ViewModel {
    private static final String FORMATTED_TIME_STAMP = "dd MMM, yyyy hh:mm a";
    private static final String UTC_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private SpaceXListAdapter spaceXListAdapter;
    private SpaceXProvider spaceXProvider;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SpaceXViewModel spaceXViewModel;


    public SpaceXListViewModel() {
        spaceXListAdapter = new SpaceXListAdapter(new ArrayList<>(), this);
        spaceXProvider = new SpaceXProvider();
        fetchSpaceXPastLaunchList();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public void onSpaceXItemClicked() { }

    private void fetchSpaceXPastLaunchList() {
        compositeDisposable.add(
                spaceXProvider.getPastLaunchData(10)
                        .flatMap(pastLaunches ->
                                Observable.fromIterable(pastLaunches)
                                        .map(pastLaunch -> {
                                            spaceXViewModel.setMissionName(pastLaunch.getMissionName());
                                            spaceXViewModel.setMissionTimestamp(getFormattedDateString(pastLaunch.getLaunchDateUtc()));
                                            return spaceXViewModel;
                                        })
                                        .toList())
                        .subscribe(spaceXViewModels1 -> spaceXListAdapter.setSpaceXViewModels(spaceXViewModels1), Throwable::printStackTrace));
    }

    private String getFormattedDateString(String utcDateString) {
        if (TextUtils.isEmpty(utcDateString)) {
            return "";
        }

        return new SimpleDateFormat(FORMATTED_TIME_STAMP).format(getDateFromString(utcDateString, UTC_TIME_FORMAT));
    }

    private Date getDateFromString(String date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        try {
            if (!TextUtils.isEmpty(date)) {
                return formatter.parse(date);
            }
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
