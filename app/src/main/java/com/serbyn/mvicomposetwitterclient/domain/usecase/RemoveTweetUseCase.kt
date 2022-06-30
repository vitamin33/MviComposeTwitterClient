package com.serbyn.mvicomposetwitterclient.domain.usecase

import com.serbyn.mvicomposetwitterclient.domain.entity.Tweet
import com.serbyn.mvicomposetwitterclient.domain.repository.TwitterRepository
import javax.inject.Inject

class RemoveTweetUseCase @Inject constructor(private val twitterRepository: TwitterRepository) {
    suspend operator fun invoke(tweet: Tweet) = twitterRepository.removeTweet(tweet)
}