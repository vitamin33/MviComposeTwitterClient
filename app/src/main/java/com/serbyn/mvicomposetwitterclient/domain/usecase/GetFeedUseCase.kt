package com.serbyn.mvicomposetwitterclient.domain.usecase

import com.serbyn.mvicomposetwitterclient.domain.repository.TwitterRepository
import javax.inject.Inject

class GetFeedUseCase @Inject constructor(private val twitterRepository: TwitterRepository) {
    operator fun invoke() = twitterRepository.getTwitterFeed()
}