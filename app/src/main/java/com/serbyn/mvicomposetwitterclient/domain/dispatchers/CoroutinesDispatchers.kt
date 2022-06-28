package com.serbyn.mvicomposetwitterclient.domain.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutinesDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}