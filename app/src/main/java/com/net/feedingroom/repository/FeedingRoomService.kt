package com.net.feedingroom.repository

import com.net.feedingroom.api.RetrofitClient

class FeedingRoomService {

    private val apiClient = RetrofitClient.client

    suspend fun searchFeedingRoom(address: String) = execute { apiClient.searchFeedingRooms(address) }

    suspend fun searchFeedingRoom(lat: Double, lng: Double) = execute { apiClient.searchFeedingRooms(lat, lng) }

    private suspend fun <T> execute(action: suspend () -> T?): T? {
        return try {
            action()
        } catch (t: Throwable) {
            t.printStackTrace()
            null
        }
    }

}