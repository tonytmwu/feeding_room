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
        sendLoadingState(LoadingState.Loading, FeedingRoomInfo.mockData)
        viewModelScope.launch {
            val rooms = feedingRoomService.searchFeedingRoom(address)
            sendLoadingState(LoadingState.Done, rooms)
        }
    }

    private fun sendLoadingState(state: LoadingState, data: FeedingRoomInfo?) {
        _loadingState.postValue(state)
        _feedingRooms.postValue(data)
    }
}