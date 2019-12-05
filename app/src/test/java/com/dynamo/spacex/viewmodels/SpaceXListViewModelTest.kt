package com.dynamo.spacex.viewmodels

import com.dynamo.spacex.SpaceXListAdapter
import com.dynamo.spacex.model.PastLaunch
import com.dynamo.spacex.serviceprovider.SpaceXProvider
import com.dynamo.spacex.shared.LaunchDetailsUseCase
import com.dynamo.spacex.shared.TransientDataProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import junit.framework.Assert.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import javax.security.auth.Subject

class SpaceXListViewModelTest {

    @MockK
    private lateinit var spaceXProvider: SpaceXProvider

    @MockK
    private lateinit var transientDataProvider: TransientDataProvider

    private lateinit var subject: SpaceXListViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
        every { spaceXProvider.getPastLaunchData(any(), any()) } returns Single.error(Throwable())

        subject = SpaceXListViewModel(spaceXProvider, transientDataProvider)
    }

    @Test
    fun onCleared_disposesSubscription() {
        assertNotNull(subject.compositeDisposable)

        subject.onCleared()

        assertTrue(subject.compositeDisposable.isDisposed)
    }

    @Test
    fun fetchSpaceXPastLaunchList_callsSpaceXService() {
        subject.fetchSpaceXPastLaunchList(0)

        verify { spaceXProvider.getPastLaunchData(0, 15)}
    }

    @Test
    fun onSpaceXItemClicked_pastLaunchDetailsAreValid_savesLaunchDetailsUseCaseWithCorrectValues() {
        val spaceXViewModel: SpaceXViewModel = mockk(relaxed = true, relaxUnitFun = true)
        every { spaceXViewModel.pastLaunch.missionName } returns "missionName"
        every { spaceXViewModel.pastLaunch.rocket?.rocketName } returns "rocketName"
        every { spaceXViewModel.pastLaunch.details } returns "details"
        every { spaceXViewModel.pastLaunch.links?.videoId } returns "videoId"

        subject.onSpaceXItemClicked(spaceXViewModel)

        verify { transientDataProvider.saveUseCase(LaunchDetailsUseCase("missionName", "rocketName", "details", "videoId")) }
    }
}