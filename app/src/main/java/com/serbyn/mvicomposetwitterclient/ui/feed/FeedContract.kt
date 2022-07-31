package com.serbyn.mvicomposetwitterclient.ui.feed

import com.serbyn.mvicomposetwitterclient.ui.base.UiEffect
import com.serbyn.mvicomposetwitterclient.ui.base.UiEvent
import com.serbyn.mvicomposetwitterclient.ui.base.UiState
import com.serbyn.mvicomposetwitterclient.ui.feed.entity.TweetItem


interface FeedContract {
    sealed class Event : UiEvent {
        object Initial : Event()
        data class Remove(val tweet: TweetItem) : Event()
    }

    //UI view state
    data class State(
        val feedState: FeedState
    ) : UiState

    sealed class FeedState {
        object Idle : FeedState()
        object Loading : FeedState()
        object Error : FeedState()
        data class Success(val feedItems: List<TweetItem>) : FeedState()
    }

    sealed class Effect : UiEffect {
        object ShowToast : Effect()
    }
}