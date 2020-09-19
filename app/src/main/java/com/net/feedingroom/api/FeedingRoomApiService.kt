package com.net.feedingroom.api

import com.net.feedingroom.model.FeedingRoomInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedingRoomApiService {

    @GET("/search_feeding_rooms")
    suspend fun searchFeedingRooms(@Query("addr") address: String): FeedingRoomInfo?

}