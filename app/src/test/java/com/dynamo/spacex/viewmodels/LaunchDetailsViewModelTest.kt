package com.dynamo.spacex.viewmodels

import com.dynamo.spacex.shared.LaunchDetailsUseCase
import com.dynamo.spacex.shared.TransientDataProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class LaunchDetailsViewModelTest {

    @MockK
    private lateinit var transientDataProvider: TransientDataProvider

    @MockK
    private lateinit var useCase: LaunchDetailsUseCase

    private lateinit var subject: LaunchDetailsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
        subject = LaunchDetailsViewModel(transientDataProvider)
    }

    @Test
    fun onLoad_launchDetailsViewModelUseCaseExists_getsValuesFromUseCase() {
        every { useCase.title } returns ("title")
        every { useCase.rocketName } returns ("rocketName")
        every { useCase.launchDetailsText } returns ("details")
        every { useCase.youtubeVideoId } returns ("youtubeVideoId")
        every { transientDataProvider.containsUseCase(LaunchDetailsUseCase::class.java) } returns true
        every { transientDataProvider.getUseCase(LaunchDetailsUseCase::class.java) } returns useCase

        subject.getValuesFromUseCase()

        assertThat(subject.launchDetailsTitle.get()).isEqualTo("title")
        assertThat(subject.rocketName.get()).isEqualTo("rocketName")
        assertThat(subject.launchDetailsText.get()).isEqualTo("details")
    }

    @Test
    fun onLoad_launchDetailsViewModelUseCaseDoesNotExist_doesNotGetValuesFromUseCase() {
        subject.getValuesFromUseCase()

        assertThat(subject.launchDetailsTitle.get()).isEqualTo(null)
        assertThat(subject.rocketName.get()).isEqualTo(null)
        assertThat(subject.launchDetailsText.get()).isEqualTo(null)
    }
}