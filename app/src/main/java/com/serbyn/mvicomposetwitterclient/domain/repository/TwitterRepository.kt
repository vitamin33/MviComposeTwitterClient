package com.serbyn.mvicomposetwitterclient.domain.repository

import com.serbyn.mvicomposetwitterclient.domain.entity.Feed
import com.serbyn.mvicomposetwitterclient.domain.entity.Tweet
import kotlinx.coroutines.flow.Flow

interface TwitterRepository {
    fun getTwitterFeed(): Flow<Feed>

    suspend fun refresh()

    suspend fun removeTweet(tweet: Tweet)

    suspend fun postTweet(tweet: Tweet)
}