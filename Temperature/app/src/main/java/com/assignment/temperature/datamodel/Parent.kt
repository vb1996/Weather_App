package com.assignment.temperature.datamodel

import com.google.gson.annotations.SerializedName

data class Parent(

    @field:SerializedName("latt_long")
    val lattLong: String? = null,

    @field:SerializedName("woeid")
    val woeid: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("location_type")
    val locationType: String? = null
)