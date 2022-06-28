package com.serbyn.mvicomposetwitterclient.domain.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Singleton
class CoroutinesDispatchersImpl(
    override val main: CoroutineDispatcher,
    override val io: CoroutineDispatcher
) : CoroutinesDispatchers {
}