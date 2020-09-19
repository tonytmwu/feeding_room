package com.net.feedingroom.api.model

import com.google.gson.annotations.SerializedName

data class FeedingRoomInfo(
    val address: String?,
    @SerializedName("center_lat")
    val centerLat: Float?,
    @SerializedName("center_lng")
    val centerLng: Float?,
    val rooms: List<FeedingRoom>?
)