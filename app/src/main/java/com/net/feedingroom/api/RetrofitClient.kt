package com.net.feedingroom.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    var client = Retrofit.Builder()
                .baseUrl("http://babyfeedingroom.tech-farmer.life")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(FeedingRoomApiService::class.java)

}