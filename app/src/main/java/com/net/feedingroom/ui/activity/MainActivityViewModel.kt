package com.net.feedingroom.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.net.feedingroom.model.FeedingRoom

class MainActivityViewModel: ViewModel() {

    private val currentLocation = MutableLiveData<LatLng>()
    private val selectedFeedingRoom = MutableLiveData<FeedingRoom>()

    val currentLocationLiveData: LiveData<LatLng> = currentLocation
    val selectedFeedingRoomLiveData: LiveData<FeedingRoom> = selectedFeedingRoom

    fun updateCurrentLocation(latLng: LatLng) {
        currentLocation.postValue(latLng)
    }

    fun updateSelectedFeedingRoom(room: FeedingRoom) {
        selectedFeedingRoom.postValue(room)
    }

}