package com.serbyn.mvicomposetwitterclient.di

import com.serbyn.mvicomposetwitterclient.data.TwitterRepositoryImpl
import com.serbyn.mvicomposetwitterclient.domain.dispatchers.CoroutinesDispatchers
import com.serbyn.mvicomposetwitterclient.domain.dispatchers.CoroutinesDispatchersImpl
import com.serbyn.mvicomposetwitterclient.domain.repository.TwitterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindCoroutineDispatchers(coroutineDispatcherImpl: CoroutinesDispatchersImpl): CoroutinesDispatchers

    @Binds
    abstract fun bindTwitterRepository(twitterRepositoryImpl: TwitterRepositoryImpl): TwitterRepository
}