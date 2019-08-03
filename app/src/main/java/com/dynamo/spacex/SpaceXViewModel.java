package com.dynamo.spacex;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class SpaceXViewModel extends ViewModel {

    private ObservableField<String> missionName = new ObservableField<>();
    private ObservableField<String> missionTimestamp = new ObservableField<>();

    public SpaceXViewModel() { }

    public void setMissionName(String missionName) {
        this.missionName.set(missionName);
    }

    public void setMissionTimestamp(String missionTimestamp) {
        this.missionTimestamp.set(missionTimestamp);
    }
}
