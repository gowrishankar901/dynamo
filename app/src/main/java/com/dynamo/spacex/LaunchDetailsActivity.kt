package com.dynamo.spacex

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.dynamo.spacex.databinding.ActivityLaunchDetailsBinding
import com.dynamo.spacex.viewmodels.LaunchDetailsViewModel
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import dagger.android.AndroidInjection
import javax.inject.Inject

class LaunchDetailsActivity: AppCompatActivity() {

    @Inject
    lateinit var launchDetailsViewModel: LaunchDetailsViewModel

    companion object {
        private const val YOUTUBE_API_KEY: String = "AIzaSyBM7uxvSRtCYLCEdVAZ5KNRYxMTg0Lq3d8"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityLaunchDetailsBinding>(this, R.layout.activity_launch_details)
        binding.viewModel = launchDetailsViewModel
        launchDetailsViewModel.getValuesFromUseCase()
        playYoutubeVideo()
    }

    private fun playYoutubeVideo() {
        val fragment = supportFragmentManager.findFragmentById(R.id.youtube_player_view) as YouTubePlayerFragment?
        launchDetailsViewModel.youtubeVideoId.observe(this, Observer { initializeYoutubeVideo(fragment, it) })
    }

    private fun initializeYoutubeVideo(fragment: YouTubePlayerFragment?, youtubeVideoId: String) {
        fragment?.initialize(YOUTUBE_API_KEY, object: YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?, wasRestored: Boolean) {
                youTubePlayer?.fullscreenControlFlags = YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT

                if (!wasRestored) {
                    youTubePlayer?.cueVideo(youtubeVideoId)
                }
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                showToast()
            }
        })
    }

    private fun showToast(): Toast {
        return Toast.makeText(this, getString(R.string.failed_video), Toast.LENGTH_LONG)
    }
}