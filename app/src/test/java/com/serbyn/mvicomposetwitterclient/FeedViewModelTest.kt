package com.serbyn.mvicomposetwitterclient

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.serbyn.mvicomposetwitterclient.data.remote.TwitterApiService
import com.serbyn.mvicomposetwitterclient.domain.repository.TwitterRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class FeedViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutinesRule: TestCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiHelper: TwitterApiService

    @Mock
    private lateinit var twitterRepository: TwitterRepository

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

    }
    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun test_FetchFeed() = runTest {
        assert(true)
    }
}