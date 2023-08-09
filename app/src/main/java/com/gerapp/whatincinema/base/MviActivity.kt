package com.gerapp.whatincinema.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class MviActivity<VM : MviViewModel<out UiIntent, out UiState>> : ComponentActivity() {

    protected abstract val viewModel: VM

    abstract fun onUiEffect(effect: UiEffect)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeUiEffects()
    }

    private fun subscribeUiEffects() {
        viewModel.uiEffect
            .onEach { onUiEffect(it) }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }
}
