package com.dynamo.spacex.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

class Links {

    @SerializedName("video_link")
    private String videoLink;

    public String getVideoLink() {
        return videoLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Links links = (Links) o;
        return Objects.equals(videoLink, links.videoLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoLink);
    }
}
