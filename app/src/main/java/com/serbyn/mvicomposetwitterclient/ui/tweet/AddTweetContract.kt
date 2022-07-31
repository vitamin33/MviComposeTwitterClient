package com.serbyn.mvicomposetwitterclient.ui.tweet

import com.serbyn.mvicomposetwitterclient.ui.base.UiEffect
import com.serbyn.mvicomposetwitterclient.ui.base.UiEvent
import com.serbyn.mvicomposetwitterclient.ui.base.UiState
import com.serbyn.mvicomposetwitterclient.ui.feed.entity.TweetItem

interface AddTweetContract {
    sealed class Event : UiEvent {
        object Initial : Event()
        data class AddTweet(val tweet: TweetItem) : Event()
    }

    sealed class State : UiState {
        object Idle : State()
        object Loading : State()
        object Error : State()
        object Success : State()
    }

    sealed class Effect : UiEffect {
        object ShowToast : Effect()
    }
}