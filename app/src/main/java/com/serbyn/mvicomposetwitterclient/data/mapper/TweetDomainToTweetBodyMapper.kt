package com.serbyn.mvicomposetwitterclient.data.mapper

import com.serbyn.mvicomposetwitterclient.Mapper
import com.serbyn.mvicomposetwitterclient.data.remote.TweetBody
import com.serbyn.mvicomposetwitterclient.domain.entity.Tweet
import javax.inject.Inject

class TweetDomainToTweetBodyMapper @Inject constructor() : Mapper<Tweet, TweetBody> {
    override fun invoke(response: Tweet): TweetBody {
        return TweetBody()
    }
}