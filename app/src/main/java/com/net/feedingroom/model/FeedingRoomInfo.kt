package com.net.feedingroom.model

import com.google.gson.annotations.SerializedName

data class FeedingRoomInfo(
    val address: String? = null,
    @SerializedName("center_lat")
    val centerLat: Float? = null,
    @SerializedName("center_lng")
    val centerLng: Float? = null,
    val rooms: List<FeedingRoom>? = null
) {
    companion object {
        val mockData get() = FeedingRoomInfo(rooms = FeedingRoom.mockData)
    }
}