package com.dynamo.spacex.viewmodels;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.dynamo.spacex.shared.LaunchDetailsUseCase;
import com.dynamo.spacex.shared.TransientDataProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class LaunchDetailsViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    TransientDataProvider transientDataProvider;

    @Mock
    LaunchDetailsUseCase useCase;

    private LaunchDetailsViewModel subject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void onLoad_getsValuesFromUseCase() {
        when(useCase.getTitle()).thenReturn("title");
        when(useCase.getRocketName()).thenReturn("rocketName");
        when(useCase.getLaunchDetailsText()).thenReturn("details");
        when(useCase.getYoutubeVideoId()).thenReturn("youtubeVideoId");
        when(transientDataProvider.getUseCase(LaunchDetailsUseCase.class)).thenReturn(useCase);

        subject = new LaunchDetailsViewModel(transientDataProvider);

        assertThat(subject.launchDetailsTitle.get()).isEqualTo("title");
        assertThat(subject.rocketName.get()).isEqualTo("rocketName");
        assertThat(subject.launchDetailsText.get()).isEqualTo("details");
        assertThat(subject.youtubeVideoId.getValue()).isEqualTo("youtubeVideoId");
    }
}