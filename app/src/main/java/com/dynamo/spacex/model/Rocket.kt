package com.dynamo.spacex.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Rocket {
    @SerializedName("rocket_name")
    @Expose
    var rocketName:String?=null
}