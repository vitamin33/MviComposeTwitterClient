package com.serbyn.mvicomposetwitterclient.data.remote

import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.*

interface TwitterApiService {
    @GET("feed")
    suspend fun getFeed(): FeedResponse

    @DELETE("tweet/{id}")
    suspend fun removeTweet(@Path("id") tweetId: String): TweetResponse

    @Headers("Content-Type: application/json")
    @POST("tweet/create")
    suspend fun postTweet(@Body tweet: TweetBody): TweetResponse

    companion object {
        operator fun invoke(retrofit: Retrofit) = retrofit.create<TwitterApiService>()
    }
}