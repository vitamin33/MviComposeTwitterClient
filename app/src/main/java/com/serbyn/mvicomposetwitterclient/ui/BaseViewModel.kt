package com.serbyn.mvicomposetwitterclient.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

abstract class BaseViewModel<S , I> : ViewModel() {

    private val intentFlow: SharedFlow<I> = MutableSharedFlow()
    private val _state: MutableStateFlow<S> by lazy { MutableStateFlow(initialState) }
    val stateFlow: StateFlow<S> = _state

    abstract val initialState: S

    init { observeIntents() }

    fun observeIntents(): Flow<I> {
        return intentFlow
    }

    protected suspend fun updateState(handler: suspend (oldState: S) -> S) {
        _state.emit(handler(_state.value ?: initialState))
    }
}