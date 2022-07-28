package com.serbyn.mvicomposetwitterclient.ui.tweet

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.serbyn.mvicomposetwitterclient.domain.usecase.GetFeedUseCase
import com.serbyn.mvicomposetwitterclient.domain.usecase.PostTweetUseCase
import com.serbyn.mvicomposetwitterclient.domain.usecase.RefreshFeedUseCase
import com.serbyn.mvicomposetwitterclient.domain.usecase.RemoveTweetUseCase
import com.serbyn.mvicomposetwitterclient.ui.BaseViewModel
import com.serbyn.mvicomposetwitterclient.ui.feed.FeedContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AddTweetViewModel @Inject constructor(
    private val addTweetUseCase: PostTweetUseCase
) : BaseViewModel<ViewState, ViewIntent>(){

    init {
        val intentFlow = observeIntents()
        merge(
            intentFlow.filterIsInstance<ViewIntent>().take(1),
            intentFlow.filterNot { it is ViewIntent.Initial }
        )
            .onEach { updateState { it } }
            .catch { Log.e("AddTweetViewModel", "Error catch during view state flow.") }
            .launchIn(viewModelScope)
    }

    override fun initialState(): ViewState = ViewState.initial()

    fun processIntents(intent: ViewIntent) {
        when(intent) {
            is ViewIntent.Initial -> {

            }
            is ViewIntent.Refresh -> {

            }
            is ViewIntent.Retry -> {

            }
            is ViewIntent.RemoveTweet -> {

            }
        }
    }

    private fun <T : ViewIntent> Flow<T>.logIntent() = onEach {
        Log.d("AddTweetViewModel", "## Intent: $it")
    }
}