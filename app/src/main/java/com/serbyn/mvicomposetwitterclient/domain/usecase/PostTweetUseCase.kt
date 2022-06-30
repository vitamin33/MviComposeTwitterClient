package com.serbyn.mvicomposetwitterclient.domain.usecase

import com.serbyn.mvicomposetwitterclient.domain.entity.Tweet
import com.serbyn.mvicomposetwitterclient.domain.repository.TwitterRepository
import javax.inject.Inject

class PostTweetUseCase @Inject constructor(private val twitterRepository: TwitterRepository) {
    suspend operator fun invoke(tweet: Tweet) = twitterRepository.postTweet(tweet)
}