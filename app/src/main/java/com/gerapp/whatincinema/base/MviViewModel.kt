package com.gerapp.whatincinema.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class MviViewModel<T : UiIntent, U : UiState, V : UiEffect>(
    initialState: U,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<U>(initialState)
    val uiState: StateFlow<U> = _uiState.asStateFlow()

//    val uiState = savedStateHandle.getStateFlow("uiState", initialState)

    private val _uiEffect = Channel<V>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    private val _uiIntent = MutableSharedFlow<T>(INTENT_REPLAY_AMOUNT)

    init {
        startCollectingUiIntents()
    }

    protected abstract fun onUiIntent(intent: T)

    fun sendIntent(intent: T) {
        viewModelScope.launch { _uiIntent.emit(intent) }
    }

    open fun resendLastIntent() {
        viewModelScope.launch { _uiIntent.replayCache.lastOrNull()?.let { _uiIntent.emit(it) } }
    }

    protected fun publishState(transformation: U.() -> U) {
        _uiState.value = transformation(_uiState.value)
//        savedStateHandle["uiState"] = transformation(uiState.value)
    }

    protected fun publishEffect(effect: V) {
        viewModelScope.launch { _uiEffect.send(effect) }
    }

    private fun startCollectingUiIntents() {
        viewModelScope.launch { _uiIntent.collect { onUiIntent(it) } }
    }

    companion object {
        private const val INTENT_REPLAY_AMOUNT = 1
    }
}
