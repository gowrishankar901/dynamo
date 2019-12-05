package com.dynamo.spacex.viewmodels

import androidx.databinding.ObservableField
import com.dynamo.spacex.model.PastLaunch
import javax.inject.Inject

class SpaceXViewModel @Inject constructor() {
    val missionName: ObservableField<String> = ObservableField()
    val missionTimestamp: ObservableField<String> = ObservableField()
    lateinit var pastLaunch: PastLaunch
}