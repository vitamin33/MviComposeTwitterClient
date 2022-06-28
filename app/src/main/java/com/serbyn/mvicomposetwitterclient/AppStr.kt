package com.serbyn.mvicomposetwitterclient

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@Suppress("unused")
@FlowPreview
@ExperimentalCoroutinesApi
@HiltAndroidApp()
class AppStr : Application()