package com.serbyn.mvicomposetwitterclient.ui.feed

import com.serbyn.mvicomposetwitterclient.domain.entity.Tweet
import com.serbyn.mvicomposetwitterclient.ui.feed.entity.TweetItem
import kotlinx.coroutines.flow.Flow

interface FeedContract {
    interface View {
        fun intents(): Flow<ViewIntent>
    }

    sealed class ViewIntent {
        object Initial : ViewIntent()
        object Refresh : ViewIntent()
        object Retry : ViewIntent()
        data class RemoveTweet(val user: Tweet) : ViewIntent()
    }

    data class ViewState(
        val feedItems: List<TweetItem>,
        val isLoading: Boolean,
        val error: Throwable?,
        val isRefreshing: Boolean
    ) {
        companion object {
            fun initial() =  ViewState(
                feedItems = emptyList(),
                isLoading = false,
                isRefreshing = false,
                error = null
            )
        }
    }

    sealed class PartialChange {
        abstract fun reduce(vs: ViewState): ViewState

        sealed class GetFeed : PartialChange() {
            override fun reduce(vs: ViewState): ViewState {
                return when (this) {
                    Loading -> vs.copy(
                        isLoading = true,
                        error = null
                    )
                    is Data -> vs.copy(
                        isLoading = false,
                        error = null,
                        feedItems = tweets
                    )
                    is Error -> vs.copy(
                        isLoading = false,
                        error = error
                    )
                }
            }

            object Loading : GetFeed()
            data class Data(val tweets: List<TweetItem>) : GetFeed()
            data class Error(val error: Throwable) : GetFeed()
        }

        sealed class Refresh : PartialChange() {
            override fun reduce(vs: ViewState): ViewState {
                return when (this) {
                    is Success -> vs.copy(isRefreshing = false)
                    is Failure -> vs.copy(isRefreshing = false)
                    Loading -> vs.copy(isRefreshing = true)
                }
            }

            object Loading : Refresh()
            object Success : Refresh()
            data class Failure(val error: Throwable) : Refresh()
        }

        sealed class RemoveTweet : PartialChange() {
            data class Success(val user: TweetItem) : RemoveTweet()
            data class Failure(val user: TweetItem, val error: Throwable) : RemoveTweet()

            override fun reduce(vs: ViewState) = vs
        }
    }

    sealed class SingleEvent {
        sealed class Refresh : SingleEvent() {
            object Success : Refresh()
            data class Failure(val error: Throwable) : Refresh()
        }

        data class GetFeedError(val error: Throwable) : SingleEvent()

        sealed class RemoveUser : SingleEvent() {
            data class Success(val user: TweetItem) : RemoveUser()
            data class Failure(val user: TweetItem, val error: Throwable) : RemoveUser()
        }
    }
}