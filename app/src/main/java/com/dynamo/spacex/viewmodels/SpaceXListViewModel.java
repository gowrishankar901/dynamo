package com.dynamo.spacex.viewmodels;

import android.text.TextUtils;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dynamo.spacex.SpaceXListAdapter;
import com.dynamo.spacex.model.PastLaunch;
import com.dynamo.spacex.serviceprovider.SpaceXProvider;
import com.dynamo.spacex.shared.LaunchDetailsUseCase;
import com.dynamo.spacex.shared.TransientDataProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class SpaceXListViewModel extends ViewModel {
    private static final String FORMATTED_TIME_STAMP = "dd MMM, yyyy hh:mm a";
    private static final String UTC_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final int FETCH_LIMIT = 15;

    public ObservableBoolean shouldShowProgressBar = new ObservableBoolean(false);
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<SpaceXViewModel> spaceXViewModelList = new ArrayList<>();
    MutableLiveData<Integer> startLaunchActivityLiveData = new MutableLiveData<>();

    private final SpaceXProvider spaceXProvider;
    private final TransientDataProvider transientDataProvider;
    private SpaceXListAdapter spaceXListAdapter;

    @Inject
    public SpaceXListViewModel(SpaceXProvider spaceXProvider, TransientDataProvider transientDataProvider) {
        this.spaceXProvider = spaceXProvider;
        this.transientDataProvider = transientDataProvider;
        spaceXListAdapter = new SpaceXListAdapter(spaceXViewModelList, this);
        fetchSpaceXPastLaunchList(0);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public SpaceXListAdapter getAdapter() {
        return spaceXListAdapter;
    }

    public void onSpaceXItemClicked(SpaceXViewModel spaceXViewModel) {
        saveLaunchDetailsUseCase(spaceXViewModel.getPastLaunch());
        startLaunchActivityLiveData.setValue(0);
    }

    public LiveData<Integer> startLaunchDetailsActivity() {
        return startLaunchActivityLiveData;
    }

    public void fetchSpaceXPastLaunchList(int offset) {
        shouldShowProgressBar.set(true);
        compositeDisposable.add(
                spaceXProvider.getPastLaunchData(offset, FETCH_LIMIT)
                        .flatMap(this::getSpaceXViewModels)
                        .subscribe(spaceXViewModels1 -> populateData(), throwable -> shouldShowProgressBar.set(false)));
    }

    private Single<List<SpaceXViewModel>> getSpaceXViewModels(List<PastLaunch> pastLaunches) {
        return Observable.fromIterable(pastLaunches)
                .map(pastLaunch -> {
                    SpaceXViewModel spaceXViewModel = new SpaceXViewModel();
                    spaceXViewModel.setPastLaunch(pastLaunch);
                    spaceXViewModel.missionName.set(pastLaunch.getMissionName());
                    spaceXViewModel.missionTimestamp.set(getFormattedDateString(pastLaunch.getLaunchDateUtc()));
                    spaceXViewModelList.add(spaceXViewModel);
                    return spaceXViewModel;
                })
                .toList();
    }

    private void populateData() {
        shouldShowProgressBar.set(false);
        spaceXListAdapter.notifyDataSetChanged();
    }

    private String getFormattedDateString(String utcDateString) {
        if (utcDateString == null || utcDateString.equalsIgnoreCase("")) {
            return "";
        }

        return new SimpleDateFormat(FORMATTED_TIME_STAMP, Locale.getDefault()).format(getDateFromString(utcDateString, UTC_TIME_FORMAT));
    }

    private Date getDateFromString(String date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
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

    private void saveLaunchDetailsUseCase(PastLaunch pastLaunch) {
        String missionName = pastLaunch.getMissionName() != null ? pastLaunch.getMissionName() : "";
        String rocketName = pastLaunch.getRocket().getRocketName() != null ? pastLaunch.getRocket().getRocketName() : "";
        String launchDetailsText = pastLaunch.getDetails() != null ? pastLaunch.getDetails() : "";
        String youtubeVideoId = pastLaunch.getLinks().getVideoId() != null ? pastLaunch.getLinks().getVideoId() : "";
        transientDataProvider.saveUseCase(new LaunchDetailsUseCase(missionName, rocketName, launchDetailsText, youtubeVideoId));
    }
}
