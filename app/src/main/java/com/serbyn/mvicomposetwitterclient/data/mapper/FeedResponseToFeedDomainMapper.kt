package com.serbyn.mvicomposetwitterclient.data.mapper

import com.serbyn.mvicomposetwitterclient.Mapper
import com.serbyn.mvicomposetwitterclient.data.remote.FeedResponse
import com.serbyn.mvicomposetwitterclient.domain.entity.Feed
import javax.inject.Inject

class FeedDomainToFeedResponseMapper @Inject constructor() : Mapper<FeedResponse, Feed> {
    override fun invoke(response: FeedResponse): Feed {
        return Feed(0)
    }
}