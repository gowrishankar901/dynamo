package com.dynamo.spacex.viewmodels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dynamo.spacex.shared.LaunchDetailsUseCase;
import com.dynamo.spacex.shared.TransientDataProvider;

import javax.inject.Inject;

public class LaunchDetailsViewModel extends ViewModel {

    public ObservableField<String> launchDetailsTitle = new ObservableField<>();
    public ObservableField<String> rocketName = new ObservableField<>();
    public ObservableField<String> launchDetailsText = new ObservableField<>();
    public MutableLiveData<String> youtubeVideoId = new MutableLiveData<>();

    private final TransientDataProvider transientDataProvider;

    @Inject
    public LaunchDetailsViewModel(TransientDataProvider transientDataProvider) {
        this.transientDataProvider = transientDataProvider;
        getValuesFromUseCase();
    }

    private void getValuesFromUseCase() {
        LaunchDetailsUseCase useCase = transientDataProvider.getUseCase(LaunchDetailsUseCase.class);
        launchDetailsTitle.set(useCase.getTitle());
        rocketName.set(useCase.getRocketName());
        launchDetailsText.set(useCase.getLaunchDetailsText());
        youtubeVideoId.setValue(useCase.getYoutubeVideoId());
    }
}
