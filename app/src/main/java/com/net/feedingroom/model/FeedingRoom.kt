package com.net.feedingroom.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class FeedingRoom(
    val address: String? = null,
    @SerializedName("adv_tag")
    val advTag: String? = null,
    val attribute: String? = null,
    @SerializedName("basic_tag")
    val basicTag: String? = null,
    @SerializedName("build_type")
    val buildType: String? = null,
    val category: String? = null,
    val certi: String? = null,
    val contact: String? = null,
    val created: String? = null,
    val distance: Float? = null,
    val district: String? = null,
    @SerializedName("location_latitude")
    val latitude: Float? = null,
    @SerializedName("location_longitude")
    val longitude: Float? = null,
    val mngtype: String? = null,
    val name: String? = null,
    val number: String? = null,
    @SerializedName("open_time")
    val openTime: String? = null,
    val photo: String? = null,
    val position: String? = null,
    val remind: String? = null,
    val tel: String? = null,
    val unit: String? = null,
    val updated: String? = null,
    val zipCode: String? = null
) {
    companion object {
        const val IMG_BASE_URL = "https://storage.googleapis.com/extreme-signer-123109.appspot.com/feedingroom/"

        val mockData get() = run {
            (0..6).map {
                FeedingRoom()
            }
        }
    }
}