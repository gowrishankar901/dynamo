package com.dynamo.spacex.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

class Rocket {

    @SerializedName("rocket_name")
    private String rocketName;

    @SerializedName("rocket_type")
    private String rocketType;

    public String getRocketName() {
        return rocketName;
    }

    public String getRocketType() {
        return rocketType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rocket rocket = (Rocket) o;
        return Objects.equals(rocketName, rocket.rocketName) &&
                Objects.equals(rocketType, rocket.rocketType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rocketName, rocketType);
    }
}
