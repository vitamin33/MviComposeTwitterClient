package com.serbyn.mvicomposetwitterclient.data.remote

import com.squareup.moshi.Json

data class FeedResponse (
    @Json(name = "id")
    val tweets: List<TweetResponse>
)

data class TweetResponse (
    @Json(name = "id")
    val id: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "date")
    val date: String,
)
