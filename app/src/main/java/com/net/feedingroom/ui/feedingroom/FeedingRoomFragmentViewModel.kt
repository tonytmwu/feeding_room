package com.net.feedingroom.ui.feedingroom

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.*
import com.google.android.libraries.maps.model.LatLng
import com.google.firebase.analytics.FirebaseAnalytics
import com.net.feedingroom.model.FeedingRoomInfo
import com.net.feedingroom.repository.FeedingRoomService
import com.net.feedingroom.ui.state.LoadingState
import kotlinx.coroutines.launch

class FeedingRoomFragmentViewModel : ViewModel() {

    private val feedingRoomService = FeedingRoomService()
    private val _feedingRooms = MutableLiveData<FeedingRoomInfo?>()
    val feedingRooms: LiveData<FeedingRoomInfo?> = _feedingRooms
    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState> = _loadingState

    fun searchFeedingRooms(address: String) {
        inLoading()
        viewModelScope.launch {
            val rooms = feedingRoomService.searchFeedingRoom(address)
            inLoaded(rooms)
        }
    }

    fun searchFeedingRooms(lat: Double, lng: Double) {
        inLoading()
        viewModelScope.launch {
            val rooms = feedingRoomService.searchFeedingRoom(lat, lng)
            inLoaded(rooms)
        }
    }

    private fun inLoading() {
        _loadingState.postValue(LoadingState.Loading)
        if(_feedingRooms.value?.rooms == null || _feedingRooms.value?.rooms?.isEmpty() == true) {
            _feedingRooms.postValue(FeedingRoomInfo.mockData)
        }
    }

    private fun inLoaded(rooms: FeedingRoomInfo?) {
        _feedingRooms.postValue(rooms)
        _loadingState.postValue(LoadingState.Done)
    }

    fun logSearchKeyword(context: Context, location: String? = null, latLng: LatLng? = null) {
        val firebaseAnalysis = FirebaseAnalytics.getInstance(context)
        val bundle = Bundle().apply {
            location?.let { putString("location", location) }
            latLng?.let{ putString("position", "(${latLng.latitude}, ${latLng.longitude}") }
        }
        firebaseAnalysis.logEvent("search_event", bundle)
    }
}