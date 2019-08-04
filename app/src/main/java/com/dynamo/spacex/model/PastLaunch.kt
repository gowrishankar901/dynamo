package com.dynamo.spacex.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class PastLaunch {
    @SerializedName("mission_name")
    @Expose
    var missionName:String? = null
    @SerializedName("launch_date_utc")
    @Expose
    var launchDateUtc:String? = null
    @SerializedName("rocket")
    @Expose
    var rocket: Rocket? = null
    @SerializedName("links")
    @Expose
    var links: Links? = null
    @SerializedName("details")
    @Expose
    var details:String? = null
}