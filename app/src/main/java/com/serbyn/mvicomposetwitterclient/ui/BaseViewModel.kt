package com.serbyn.mvicomposetwitterclient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serbyn.mvicomposetwitterclient.ui.base.UiEffect
import com.serbyn.mvicomposetwitterclient.ui.base.UiEvent
import com.serbyn.mvicomposetwitterclient.ui.base.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect> : ViewModel() {

    // Create Initial State of View
    private val initialState : State by lazy { initialState() }

    // Get Current State
    val currentState: State
        get() = uiState.value

    private val _uiState : MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _events : MutableSharedFlow<Event> = MutableSharedFlow()
    val events = _events.asSharedFlow()

    private val _effects : Channel<Effect> = Channel()
    val effects = _effects.receiveAsFlow()

    init {
        subscribeEvents()
    }

    abstract fun initialState() : State

    abstract fun handleEvent(event: Event)

    private fun subscribeEvents() {
        viewModelScope.launch {
            events.collect {
                handleEvent(it)
            }
        }
    }

    fun sendEvent(e: Event) {
        viewModelScope.launch {
            _events.emit(e)
        }
    }

    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    protected fun sendEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch {
            _effects.send(effectValue)
        }
    }
}