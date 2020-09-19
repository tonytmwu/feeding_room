package com.net.feedingroom.repository

import com.net.feedingroom.api.RetrofitClient

class FeedingRoomService {

    private val apiClient = RetrofitClient.client

    suspend fun searchFeedingRoom(address: String) = apiClient.searchFeedingRooms(address)

}