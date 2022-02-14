package com.dvidal.androidtestingsample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvidal.androidtestingsample.domain.FeatureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class FeatureViewModel @Inject constructor(
    private val useCase: FeatureUseCase
): ViewModel(), FeatureContract.ViewModel {

    private val _states = MutableStateFlow<FeatureContract.State>(FeatureContract.State.Idle)
    override val states: StateFlow<FeatureContract.State> = _states

    private val _events = MutableSharedFlow<FeatureContract.Event>()
    override val events: SharedFlow<FeatureContract.Event> = _events

    override fun invokeAction(action: FeatureContract.Action) = viewModelScope.launch {
        when(action) {
            FeatureContract.Action.GoToSecondPage -> {
                _events.emit(FeatureContract.Event.NavigateToSecondPage)
            }
            FeatureContract.Action.InitPage -> {
                useCase.invoke()
            }
        }
    }
}