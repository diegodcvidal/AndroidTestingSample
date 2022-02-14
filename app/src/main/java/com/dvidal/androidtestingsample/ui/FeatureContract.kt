package com.dvidal.androidtestingsample.ui

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

sealed class FeatureContract {

    interface ViewModel {

        val states: StateFlow<State>
        val events: SharedFlow<Event>

        fun invokeAction(action: Action): Job
    }

    sealed class Action {

        object InitPage: Action()
        object GoToSecondPage: Action()
    }

    sealed class State {

        object Idle: State()
        object DisplayViewExample1: State()
        object DisplayViewExample2: State()
        object DisplayViewExample3: State()
    }

    sealed class Event {

        object NavigateToSecondPage: Event()
    }
}