package com.dynamo.spacex.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class PastLaunch {

    @SerializedName("flight_number")
    private int flightNumber;

    @SerializedName("mission_name")
    private String missionName;

    @SerializedName("launch_date_utc")
    private String launchDateUtc;

    @SerializedName("rocket")
    private Rocket rocket;

    @SerializedName("links")
    private Links links;

    public int getFlightNumber() {
        return flightNumber;
    }

    public String getMissionName() {
        return missionName;
    }

    public String getLaunchDateUtc() {
        return launchDateUtc;
    }

    public Rocket getRocket() {
        return rocket;
    }

    public Links getLinks() {
        return links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PastLaunch that = (PastLaunch) o;
        return flightNumber == that.flightNumber &&
                Objects.equals(missionName, that.missionName) &&
                Objects.equals(launchDateUtc, that.launchDateUtc) &&
                Objects.equals(rocket, that.rocket) &&
                Objects.equals(links, that.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber, missionName, launchDateUtc, rocket, links);
    }
}
