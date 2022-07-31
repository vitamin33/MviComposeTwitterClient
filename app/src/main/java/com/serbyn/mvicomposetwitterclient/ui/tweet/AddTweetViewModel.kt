package com.serbyn.mvicomposetwitterclient.ui.tweet

import com.serbyn.mvicomposetwitterclient.domain.usecase.PostTweetUseCase
import com.serbyn.mvicomposetwitterclient.ui.BaseViewModel
import com.serbyn.mvicomposetwitterclient.ui.tweet.AddTweetContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AddTweetViewModel @Inject constructor(
    private val addTweetUseCase: PostTweetUseCase
) : BaseViewModel<Event, State, Effect>(){


    override fun initialState(): State {
        return State.Idle
    }

    override fun handleEvent(event: Event) {
        when(event) {
            Event.Initial -> {
                //TODO initial logic
            }
            is Event.AddTweet -> {
                //TODO add logic
            }
        }
    }

}