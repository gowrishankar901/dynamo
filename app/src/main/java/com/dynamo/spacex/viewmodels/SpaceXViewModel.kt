package com.dynamo.spacex.viewmodels

import androidx.databinding.ObservableField
import com.dynamo.spacex.model.PastLaunch

class SpaceXViewModel {
    val missionName: ObservableField<String> = ObservableField()
    val missionTimestamp: ObservableField<String> = ObservableField()
    lateinit var pastLaunch: PastLaunch
}