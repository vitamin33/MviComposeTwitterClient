package com.serbyn.mvicomposetwitterclient.data

import android.util.Log
import com.serbyn.mvicomposetwitterclient.data.mapper.TweetDomainToTweetBodyMapper
import com.serbyn.mvicomposetwitterclient.data.mapper.TweetResponseToTweetDomainMapper
import com.serbyn.mvicomposetwitterclient.data.remote.TwitterApiService
import com.serbyn.mvicomposetwitterclient.domain.dispatchers.CoroutinesDispatchers
import com.serbyn.mvicomposetwitterclient.domain.entity.Feed
import com.serbyn.mvicomposetwitterclient.domain.entity.Tweet
import com.serbyn.mvicomposetwitterclient.domain.repository.TwitterRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TwitterRepositoryImpl @Inject constructor(
    private val dispatchers: CoroutinesDispatchers,
    private val twitterApiService: TwitterApiService,
    private val responseToDomain: TweetResponseToTweetDomainMapper,
    private val domainToBody: TweetDomainToTweetBodyMapper
) : TwitterRepository {

    private val changesFlow =
        MutableSharedFlow<Change>()

    private sealed class Change {
        data class Removed(val removedTweet: Tweet) : Change()
        data class Refreshed(val feed: Feed) : Change()
        class Posted(val postedTweet: Tweet) : Change()
    }

    private suspend fun getFeedFromRemote(): List<Tweet> {
        return withContext(dispatchers.io) {
            twitterApiService.getFeed().tweets.map {
                responseToDomain.invoke(it)
            }
        }
    }

    override fun getTwitterFeed(): Flow<List<Tweet>> {
        return flow {
            emit(getFeedFromRemote())
        }
    }

    override suspend fun refresh() {
        getFeedFromRemote().let {
            changesFlow.emit(Change.Refreshed(Feed(it)))
        }
    }

    override suspend fun removeTweet(tweet: Tweet) {
        withContext(dispatchers.io) {
            val removed = twitterApiService.removeTweet(tweet.id)
            changesFlow.emit(Change.Removed(responseToDomain(removed)))
        }
    }

    override suspend fun postTweet(tweet: Tweet) {
        withContext(dispatchers.io) {
            val posted = twitterApiService.postTweet(domainToBody(tweet))
            changesFlow.emit(Change.Posted(responseToDomain(posted)))
            delay(400)
        }
    }
}