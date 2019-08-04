package com.dynamo.spacex.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Links {
    @SerializedName("youtube_id")
    @Expose
    var videoId:String? = null
}