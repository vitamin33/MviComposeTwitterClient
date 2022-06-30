package com.serbyn.mvicomposetwitterclient.domain.usecase

import com.serbyn.mvicomposetwitterclient.domain.repository.TwitterRepository
import javax.inject.Inject

class RefreshFeedUseCase @Inject constructor(private val twitterRepository: TwitterRepository) {
    suspend operator fun invoke() = twitterRepository.refresh()
}