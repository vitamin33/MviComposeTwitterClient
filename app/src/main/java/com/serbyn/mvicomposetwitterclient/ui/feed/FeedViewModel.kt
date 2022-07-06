package com.serbyn.mvicomposetwitterclient.ui.feed

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.serbyn.mvicomposetwitterclient.domain.usecase.GetFeedUseCase
import com.serbyn.mvicomposetwitterclient.domain.usecase.RefreshFeedUseCase
import com.serbyn.mvicomposetwitterclient.domain.usecase.RemoveTweetUseCase
import com.serbyn.mvicomposetwitterclient.ui.BaseViewModel
import com.serbyn.mvicomposetwitterclient.ui.feed.entity.TweetItem
import com.serbyn.mvicomposetwitterclient.ui.feed.FeedContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getFeedUseCase: GetFeedUseCase,
    private val refreshFeedUseCase: RefreshFeedUseCase,
    private val removeTweetUseCase: RemoveTweetUseCase
) : BaseViewModel<ViewState, ViewIntent>(){

    init {
        val initViewState = ViewState.initial()
        val intentFlow = observeIntents()
        merge(
            intentFlow.filterIsInstance<ViewIntent>().take(1),
            intentFlow.filterNot { it is ViewIntent.Initial }
        )
            .toPartialChangeFlow()
            .sendSingleEvent()
            .scan(initViewState) { vs, change -> change.reduce(vs) }
            .onEach { updateState { it } }
            .catch { Log.e("FeedViewModel", "Error catch during view state flow.") }
            .launchIn(viewModelScope)
    }

    override fun getInitialState(): ViewState {
        return ViewState.initial()
    }

    fun Flow<PartialChange>.sendSingleEvent(): Flow<PartialChange> {
        return onEach {
            val event = when(it) {
                is PartialChange.GetFeed.Error -> SingleEvent.GetFeedError(it.error)
                is PartialChange.Refresh.Success -> SingleEvent.Refresh.Success
                is PartialChange.Refresh.Failure -> SingleEvent.Refresh.Failure(it.error)
                is PartialChange.RemoveTweet.Success -> SingleEvent.RemoveUser.Success(it.user)
                is PartialChange.RemoveTweet.Failure -> SingleEvent.RemoveUser.Failure(
                    it.user,
                    it.error
                )
                PartialChange.GetFeed.Loading -> return@onEach
                is PartialChange.GetFeed.Data -> return@onEach
                PartialChange.Refresh.Loading -> return@onEach
            }

            //TODO send single event here and subscribe Activity/Fragment for it
        }
    }

    @OptIn(FlowPreview::class)
    fun Flow<ViewIntent>.toPartialChangeFlow(): Flow<PartialChange> {
        val getFeedFlow = getFeedUseCase().onEach { Log.d("FeedViewModel", "Emit feed.size=${it.size}") }
            .map {
                val feedItems = it.map(::TweetItem)
                PartialChange.GetFeed.Data(feedItems) as FeedContract.PartialChange.GetFeed
            }
            .onStart { emit(PartialChange.GetFeed.Loading) }
            .catch { emit(PartialChange.GetFeed.Error(it)) }
        val refreshFlow = refreshFeedUseCase::invoke
            .asFlow()
            .map { PartialChange.Refresh.Success as PartialChange.Refresh }
            .onStart { emit(PartialChange.Refresh.Loading) }
            .catch { emit(PartialChange.Refresh.Failure(it)) }


        return merge(
            filterIsInstance<ViewIntent.Initial>()
                .logIntent()
                .flatMapConcat { getFeedFlow },
            filterIsInstance<ViewIntent.Refresh>()
                .filter { stateFlow.value.let { !it.isLoading && it.error === null } }
                .logIntent()
                .flatMapConcat { refreshFlow }
        )
    }

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
        Log.d("FeedViewModel", "## Intent: $it")
    }
}