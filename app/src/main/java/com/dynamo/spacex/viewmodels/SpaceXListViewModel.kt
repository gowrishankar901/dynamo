package com.dynamo.spacex.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dynamo.spacex.SpaceXListAdapter
import com.dynamo.spacex.model.PastLaunch
import com.dynamo.spacex.serviceprovider.SpaceXProvider
import com.dynamo.spacex.shared.LaunchDetailsUseCase
import com.dynamo.spacex.shared.TransientDataProvider
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import java.text.SimpleDateFormat
import java.util.*

class SpaceXListViewModel (
        private val spaceXProvider: SpaceXProvider,
        private val transientDataProvider: TransientDataProvider
): ViewModel() {
    val shouldShowProgressBar = ObservableBoolean(false)
    val compositeDisposable = CompositeDisposable()
    val startLaunchActivityLiveData = MutableLiveData<Int>()
    private val spaceXListAdapter = SpaceXListAdapter(this)

    public override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getAdapter():SpaceXListAdapter = spaceXListAdapter

    fun onSpaceXItemClicked(spaceXViewModel: SpaceXViewModel) {
        saveLaunchDetailsUseCase(spaceXViewModel.pastLaunch)
        startLaunchActivityLiveData.postValue(0)
    }

    fun fetchSpaceXPastLaunchList(offset:Int?) {
        shouldShowProgressBar.set(true)
        compositeDisposable.add(spaceXProvider.getPastLaunchData(offset!!, FETCH_LIMIT)
                .flatMap { getSpaceXViewModels(it) }
                .subscribe({populateData(it)}, {shouldShowProgressBar.set(false)}))
    }

    fun startLaunchDetailsActivity(): LiveData<Int> = startLaunchActivityLiveData

    private fun saveLaunchDetailsUseCase(pastLaunch: PastLaunch) = transientDataProvider.saveUseCase(LaunchDetailsUseCase(pastLaunch.missionName, pastLaunch.rocket?.rocketName, pastLaunch.details, pastLaunch.links?.videoId))

    private fun populateData(spaceXViewModels:List<SpaceXViewModel>) {
        shouldShowProgressBar.set(false)
        spaceXListAdapter.setSpaceXViewModels(spaceXViewModels)
    }

    private fun getSpaceXViewModels(pastLaunches:List<PastLaunch>): Single<List<SpaceXViewModel>> {
        return Observable.fromIterable(pastLaunches)
                .map {
                    SpaceXViewModel().apply {
                        pastLaunch = it
                        missionName.set(it.missionName)
                        missionTimestamp.set(getFormattedDateString(it.launchDateUtc))
                    }
                }
                .toList()

    }

    private fun getFormattedDateString(utcDateString: String?):String {
        return SimpleDateFormat(FORMATTED_TIME_STAMP, Locale.getDefault()).format(getDateFromString(utcDateString, UTC_TIME_FORMAT))
    }

    private fun getDateFromString(date: String?, format: String) : Date {
        val formatter = SimpleDateFormat(format)
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        try { return formatter.parse(date) } finally { }
    }

    private companion object {
        private const val FORMATTED_TIME_STAMP: String = "dd MMM, yyyy hh:mm a"
        private const val UTC_TIME_FORMAT: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        private const val FETCH_LIMIT: Int = 15
    }
}