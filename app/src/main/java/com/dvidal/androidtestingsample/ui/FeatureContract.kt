package com.dvidal.androidtestingsample.ui

import androidx.lifecycle.LiveData

sealed class FeatureContract {

    interface ViewModel {

        val states: LiveData<State>
        val events: LiveData<Event>

        fun invokeAction(action: Action)
    }

    sealed class Action {

        object InitPage: Action()
        object GoToSecondPage: Action()
    }

    sealed class State {

        object DisplayViewExample1: State()
        object DisplayViewExample2: State()
        object DisplayViewExample3: State()
    }

    sealed class Event {

        object NavigateToSecondPage: Event()
    }
}