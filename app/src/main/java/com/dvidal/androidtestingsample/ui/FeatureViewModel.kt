package com.dvidal.androidtestingsample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvidal.androidtestingsample.domain.FeatureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class FeatureViewModel @Inject constructor(
    private val useCase: FeatureUseCase
): ViewModel(), FeatureContract.ViewModel {

    private val _states = MutableLiveData<FeatureContract.State>()
    override val states: LiveData<FeatureContract.State> = _states

    private val _events = MutableLiveData<FeatureContract.Event>()
    override val events: LiveData<FeatureContract.Event> = _events

    override fun invokeAction(action: FeatureContract.Action) {
        when(action) {
            FeatureContract.Action.GoToSecondPage -> {
                _events.value = FeatureContract.Event.NavigateToSecondPage
            }
            FeatureContract.Action.InitPage -> {
                useCase.invoke()
            }
        }
    }
}