package com.net.feedingroom.ui.state

sealed class LoadingState {

    object Loading: LoadingState()

    object Done: LoadingState()

}