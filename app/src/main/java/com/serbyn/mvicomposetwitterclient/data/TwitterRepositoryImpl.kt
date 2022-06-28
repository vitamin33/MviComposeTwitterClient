package com.serbyn.mvicomposetwitterclient.data

import com.serbyn.mvicomposetwitterclient.data.mapper.FeedResponseToFeedDomainMapper
import com.serbyn.mvicomposetwitterclient.data.remote.TwitterApiService
import com.serbyn.mvicomposetwitterclient.domain.dispatchers.CoroutinesDispatchers
import com.serbyn.mvicomposetwitterclient.domain.entity.Feed
import com.serbyn.mvicomposetwitterclient.domain.entity.Tweet
import com.serbyn.mvicomposetwitterclient.domain.repository.TwitterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TwitterRepositoryImpl @Inject constructor(
    private val dispatchers: CoroutinesDispatchers,
    private val twitterApiService: TwitterApiService,
    private val responseToDomain: FeedResponseToFeedDomainMapper
): TwitterRepository {
    override fun getTwitterFeed(): Flow<Feed> {
        TODO("Not yet implemented")
    }

    override suspend fun refresh() {
        TODO("Not yet implemented")
    }

    override suspend fun removeTweet(tweet: Tweet) {
        TODO("Not yet implemented")
    }

    override suspend fun postTweet(tweet: Tweet) {
        TODO("Not yet implemented")
    }
}