package com.serbyn.mvicomposetwitterclient.ui.feed.entity

import com.serbyn.mvicomposetwitterclient.domain.entity.Tweet

data class TweetItem(
    val id: String,
    val firstName: String,
    val lastName: String,
    val message: String,
    val date: String,
) {
    val fullName get() = "$firstName $lastName"

    constructor(domain: Tweet) : this(
        id = domain.id,
        date = domain.date,
        message = domain.message,
        firstName = domain.firstName,
        lastName = domain.lastName
    )

    fun toDomain() = Tweet(
        id = id,
        date = date,
        message = message,
        firstName = firstName,
        lastName = lastName
    )
}