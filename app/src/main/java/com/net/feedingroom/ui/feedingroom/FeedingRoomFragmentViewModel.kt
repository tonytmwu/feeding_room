package com.net.feedingroom.ui.feedingroom

import androidx.lifecycle.*
import com.net.feedingroom.model.FeedingRoomInfo
import com.net.feedingroom.repository.FeedingRoomService
import kotlinx.coroutines.launch

class FeedingRoomFragmentViewModel : ViewModel() {

    private val feedingRoomService = FeedingRoomService()
    private val _feedingRooms = MutableLiveData<FeedingRoomInfo?>()
    val feedingRooms: LiveData<FeedingRoomInfo?> = _feedingRooms

    fun searchFeedingRooms(address: String) {
        viewModelScope.launch {
            val rooms = feedingRoomService.searchFeedingRoom(address)
            _feedingRooms.postValue(rooms)
        }
    }
}