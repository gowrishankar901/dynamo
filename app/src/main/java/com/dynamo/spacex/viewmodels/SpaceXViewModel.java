package com.dynamo.spacex.viewmodels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.dynamo.spacex.model.PastLaunch;

import javax.inject.Inject;

public class SpaceXViewModel extends ViewModel {

    public ObservableField<String> missionName = new ObservableField<>();
    public ObservableField<String> missionTimestamp = new ObservableField<>();

    private PastLaunch pastLaunch;

    @Inject
    public SpaceXViewModel() { }

    public PastLaunch getPastLaunch() {
        return this.pastLaunch;
    }

    void setPastLaunch(PastLaunch pastLaunch) {
        this.pastLaunch = pastLaunch;
    }
}
