package com.dynamo.spacex.viewmodels;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.dynamo.spacex.model.PastLaunch;
import com.dynamo.spacex.serviceprovider.SpaceXProvider;
import com.dynamo.spacex.shared.LaunchDetailsUseCase;
import com.dynamo.spacex.shared.TransientDataProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import io.reactivex.Single;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpaceXListViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    private SpaceXProvider spaceXProvider;

    @Mock
    private TransientDataProvider transientDataProvider;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    PastLaunch pastLaunch;

    private SpaceXListViewModel subject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(spaceXProvider.getPastLaunchData(anyInt(), anyInt())).thenReturn(Single.error(new Throwable()));

        subject = new SpaceXListViewModel(spaceXProvider, transientDataProvider);
    }

    @Test
    public void onCleared_disposesSubscription() {
        assertNotNull(subject.compositeDisposable);

        subject.onCleared();

        assertTrue(subject.compositeDisposable.isDisposed());
    }

    @Test
    public void fetchSpaceXPastLaunchList_callsSpaceXService() {
        verify(spaceXProvider).getPastLaunchData(0, 10);
    }

    @Test
    public void fetchSpaceXPastLaunchList_fetchSuccessful_populatesData() {
        when(pastLaunch.getMissionName()).thenReturn("missionName");
        when(pastLaunch.getLaunchDateUtc()).thenReturn("2006-03-24T22:30:00.000Z");
        when(spaceXProvider.getPastLaunchData(anyInt(), anyInt())).thenReturn(Single.just(Collections.singletonList(pastLaunch)));
        subject = new SpaceXListViewModel(spaceXProvider, transientDataProvider);

        assertFalse(subject.shouldShowProgressBar.get());
        assertThat(subject.spaceXViewModelList.size()).isEqualTo(1);
    }

    @Test
    public void fetchSpaceXPastLaunchList_fetchFailed_doesNotPopulateData() {
        assertFalse(subject.shouldShowProgressBar.get());
        assertThat(subject.spaceXViewModelList.size()).isEqualTo(0);
    }

    @Test
    public void onSpaceXItemClicked_savesLaunchDetailsUseCase() {
        when(pastLaunch.getMissionName()).thenReturn("missionName");
        when(pastLaunch.getRocket().getRocketName()).thenReturn("rocketName");
        when(pastLaunch.getDetails()).thenReturn("details");
        when(pastLaunch.getLinks().getVideoId()).thenReturn("videoId");
        SpaceXViewModel spaceXViewModel = mock(SpaceXViewModel.class);
        when(spaceXViewModel.getPastLaunch()).thenReturn(pastLaunch);

        subject.onSpaceXItemClicked(spaceXViewModel);

        verify(transientDataProvider).saveUseCase(new LaunchDetailsUseCase("missionName", "rocketName", "details", "videoId"));
    }

    @Test
    public void onStartLaunchDetailsActivity_returnsLiveDataValue() {
        when(pastLaunch.getMissionName()).thenReturn("missionName");
        when(pastLaunch.getRocket().getRocketName()).thenReturn("rocketName");
        when(pastLaunch.getDetails()).thenReturn("details");
        when(pastLaunch.getLinks().getVideoId()).thenReturn("videoId");
        SpaceXViewModel spaceXViewModel = mock(SpaceXViewModel.class);
        when(spaceXViewModel.getPastLaunch()).thenReturn(pastLaunch);
        subject.onSpaceXItemClicked(spaceXViewModel);

        subject.startLaunchDetailsActivity();

        assertThat(subject.startLaunchActivityLiveData.getValue()).isEqualTo(0);
    }
}