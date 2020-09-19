package com.net.feedingroom.api.model

import com.google.gson.annotations.SerializedName

data class FeedingRoom(
    val address: String?,
    @SerializedName("adv_tag")
    val advTag: String?,
    val attribute: String?,
    @SerializedName("basic_tag")
    val basicTag: String?,
    @SerializedName("build_type")
    val buildType: String?,
    val category: String?,
    val certi: String?,
    val contact: String?,
    val created: String?,
    val distance: Float?,
    val district: String?,
    @SerializedName("location_latitude")
    val latitude: Float?,
    @SerializedName("location_longitude")
    val longitude: Float?,
    val mngtype: String?,
    val name: String?,
    val number: String?,
    @SerializedName("open_time")
    val openTime: String?,
    val photo: String?,
    val position: String?,
    val remind: String?,
    val tel: String?,
    val unit: String?,
    val updated: String?,
    val zipCode: String?
)