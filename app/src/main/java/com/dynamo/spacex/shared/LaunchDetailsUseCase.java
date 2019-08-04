package com.dynamo.spacex.shared;

import java.util.Objects;

public class LaunchDetailsUseCase implements UseCase {

    private String title;
    private String rocketName;
    private String launchDetailsText;
    private String youtubeVideoId;

    public LaunchDetailsUseCase(String title, String rocketName,
                                String launchDetailsText, String youtubeVideoId) {
        this.title = title;
        this.rocketName = rocketName;
        this.launchDetailsText = launchDetailsText;
        this.youtubeVideoId = youtubeVideoId;
    }

    public String getTitle() {
        return title;
    }

    public String getRocketName() {
        return rocketName;
    }

    public String getLaunchDetailsText() {
        return launchDetailsText;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaunchDetailsUseCase that = (LaunchDetailsUseCase) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(rocketName, that.rocketName) &&
                Objects.equals(launchDetailsText, that.launchDetailsText) &&
                Objects.equals(youtubeVideoId, that.youtubeVideoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, rocketName, launchDetailsText, youtubeVideoId);
    }
}
