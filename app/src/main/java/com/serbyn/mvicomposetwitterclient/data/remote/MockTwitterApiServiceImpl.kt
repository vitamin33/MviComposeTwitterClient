package com.serbyn.mvicomposetwitterclient.data.remote

import kotlinx.coroutines.delay

class MockTwitterApiServiceImpl : TwitterApiService {
    override suspend fun getFeed(): FeedResponse {
        delay(2000)
        return FeedResponse(testFeedItems)
    }

    override suspend fun removeTweet(tweetId: String): TweetResponse {
        delay(2000)
        return TweetResponse(
            "1",
            "Jack",
            "Walton",
            "All is good! All is good! All is good! All is good! All is good!",
            "11-10-2022"
        )
    }

    override suspend fun postTweet(tweet: TweetBody): TweetResponse {
        delay(2000)
        return TweetResponse(
            "1",
            "Jack",
            "Walton",
            "All is good! All is good! All is good! All is good! All is good!",
            "11-10-2022"
        )
    }
}

val testFeedItems = listOf(
    TweetResponse(
        "1",
        "Jack",
        "Walton",
        "All is good! All is good! All is good! All is good! All is good!",
        "11-10-2022"
    ),
    TweetResponse(
        "1",
        "Jack",
        "Walton",
        "All is good! All is good! All is good! All is good! All is good! All is good! All is good!",
        "11-10-2022"
    ),
    TweetResponse("1", "Jack", "Walton", "All is good!", "11-10-2022"),
    TweetResponse(
        "1",
        "Jack",
        "Walton",
        "All is good! All is good! All is good! All is good! All is good!",
        "11-10-2022"
    ),
    TweetResponse(
        "1",
        "Jack",
        "Walton",
        "All is good! All is good! All is good! All is good! All is good! All is good! All is good!",
        "11-10-2022"
    ),
    TweetResponse("1", "Jack", "Walton", "All is good!", "11-10-2022"),
    TweetResponse(
        "1",
        "Jack",
        "Walton",
        "All is good! All is good! All is good! All is good! All is good!",
        "11-10-2022"
    ),
    TweetResponse(
        "1",
        "Jack",
        "Walton",
        "All is good! All is good! All is good! All is good! All is good! All is good! All is good!",
        "11-10-2022"
    ),
    TweetResponse("1", "Jack", "Walton", "All is good!", "11-10-2022")
)