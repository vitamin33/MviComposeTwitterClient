package com.serbyn.mvicomposetwitterclient.data.mapper

import com.serbyn.mvicomposetwitterclient.Mapper
import com.serbyn.mvicomposetwitterclient.data.remote.FeedResponse
import com.serbyn.mvicomposetwitterclient.data.remote.TweetResponse
import com.serbyn.mvicomposetwitterclient.domain.entity.Feed
import com.serbyn.mvicomposetwitterclient.domain.entity.Tweet
import javax.inject.Inject

class TweetResponseToTweetDomainMapper @Inject constructor() : Mapper<TweetResponse, Tweet> {
    override fun invoke(response: TweetResponse): Tweet {
        return Tweet(response.id, response.firstName, response.lastName, response.message, response.date)
    }
}