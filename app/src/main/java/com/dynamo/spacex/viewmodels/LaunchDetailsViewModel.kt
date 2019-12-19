package com.dynamo.spacex.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dynamo.spacex.shared.LaunchDetailsUseCase
import com.dynamo.spacex.shared.TransientDataProvider

class LaunchDetailsViewModel (
        private val transientDataProvider: TransientDataProvider
): ViewModel() {
    val launchDetailsTitle: ObservableField<String> = ObservableField()
    val rocketName: ObservableField<String> = ObservableField()
    val launchDetailsText: ObservableField<String> = ObservableField()
    val youtubeVideoId = MutableLiveData<String>()

    fun getValuesFromUseCase() {
        if (transientDataProvider.containsUseCase(LaunchDetailsUseCase::class.java)) {
            val useCase = transientDataProvider.getUseCase(LaunchDetailsUseCase::class.java) as LaunchDetailsUseCase
            launchDetailsTitle.set(useCase.title)
            rocketName.set(useCase.rocketName)
            launchDetailsText.set(useCase.launchDetailsText)
            youtubeVideoId.postValue(useCase.youtubeVideoId)
        }
    }
}