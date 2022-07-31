package com.serbyn.mvicomposetwitterclient.ui.feed

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.serbyn.mvicomposetwitterclient.domain.usecase.GetFeedUseCase
import com.serbyn.mvicomposetwitterclient.domain.usecase.RefreshFeedUseCase
import com.serbyn.mvicomposetwitterclient.domain.usecase.RemoveTweetUseCase
import com.serbyn.mvicomposetwitterclient.ui.BaseViewModel
import com.serbyn.mvicomposetwitterclient.ui.feed.FeedContract.*
import com.serbyn.mvicomposetwitterclient.ui.feed.entity.TweetItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getFeedUseCase: GetFeedUseCase,
    private val refreshFeedUseCase: RefreshFeedUseCase,
    private val removeTweetUseCase: RemoveTweetUseCase
) : BaseViewModel<Event, State, Effect>() {

    init {
        sendEvent(Event.Initial)
    }

    override fun initialState(): State {
        return State(FeedState.Idle)
    }

    override fun handleEvent(event: Event) {
        when (event) {
            Event.Initial -> {
                loadFeed()
            }
            is Event.Remove -> TODO()
            Event.NavigateToAddTweet -> sendEffect {
                Effect.AddTweetNavigated
            }
        }
    }

    private fun loadFeed() {
        viewModelScope.launch {
            getFeedUseCase()
                .onStart { setState { copy(feedState = FeedState.Loading) } }
                .onEach {
                    Log.d("FeedViewModel", "Emit feed.size=${it.size}")
                }
                .catch { setState { copy(feedState = FeedState.Error) } }
                .collect {
                    val items = it.map(::TweetItem)
                    setState { copy(feedState = FeedState.Success(items)) }
                }
        }
    }

    private fun <T : Event> Flow<T>.logIntent() = onEach {
        Log.d("FeedViewModel", "## Intent: $it")
    }
}