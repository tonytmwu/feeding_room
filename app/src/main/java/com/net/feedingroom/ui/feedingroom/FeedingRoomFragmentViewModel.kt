package com.net.feedingroom.ui.feedingroom

import androidx.lifecycle.*
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
        _loadingState.postValue(LoadingState.Loading)
        if(_feedingRooms.value?.rooms == null || _feedingRooms.value?.rooms?.isEmpty() == true) {
            _feedingRooms.postValue(FeedingRoomInfo.mockData)
        }
        viewModelScope.launch {
            val rooms = feedingRoomService.searchFeedingRoom(address)
            _feedingRooms.postValue(rooms)
            _loadingState.postValue(LoadingState.Done)
        }
    }
}