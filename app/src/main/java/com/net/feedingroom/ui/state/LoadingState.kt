package com.net.feedingroom.ui.state

sealed class LoadingState {

    object Loading: LoadingState()

    data class Done<T>(val result: T?): LoadingState()

}