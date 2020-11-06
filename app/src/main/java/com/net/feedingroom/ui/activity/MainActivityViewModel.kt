package com.net.feedingroom.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.libraries.maps.model.LatLng

class MainActivityViewModel: ViewModel() {

    private val currentLocation = MutableLiveData<LatLng>()
    val currentLocationLiveData: LiveData<LatLng> = currentLocation

    fun updateCurrentLocation(latLng: LatLng) {
        currentLocation.postValue(latLng)
    }

}