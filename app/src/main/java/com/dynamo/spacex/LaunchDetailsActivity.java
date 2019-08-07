package com.dynamo.spacex;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dynamo.spacex.databinding.ActivityLaunchDetailsBinding;
import com.dynamo.spacex.viewmodels.LaunchDetailsViewModel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class LaunchDetailsActivity extends AppCompatActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyBM7uxvSRtCYLCEdVAZ5KNRYxMTg0Lq3d8";

    @Inject
    public LaunchDetailsViewModel launchDetailsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivityLaunchDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_launch_details);
        binding.setViewModel(launchDetailsViewModel);
        playYoutubeVideo();
    }

    private void playYoutubeVideo() {
        YouTubePlayerFragment fragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_player_view);
        launchDetailsViewModel.youtubeVideoId.observe(this, youtubeVideoId -> initializeYoutubeVideo(fragment, youtubeVideoId));
    }

    private void initializeYoutubeVideo(YouTubePlayerFragment fragment, String youtubeVideoId) {
        fragment.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);

                if (!wasRestored) {
                    youTubePlayer.cueVideo(youtubeVideoId);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                showToast();
            }
        });
    }

    private Toast showToast() {
        return Toast.makeText(this, getString(R.string.failed_video), Toast.LENGTH_LONG);
    }
}
