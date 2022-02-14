package com.dvidal.androidtestingsample.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dvidal.androidtestingsample.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_feature.*

@AndroidEntryPoint
class FeatureFragment: Fragment(R.layout.fragment_feature) {

    private val viewModel: FeatureContract.ViewModel by viewModels<FeatureViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.invokeAction(FeatureContract.Action.InitPage)

        viewModel.states.observe(viewLifecycleOwner, Observer(::handleViewStates))
        viewModel.events.observe(viewLifecycleOwner, Observer(::handleViewEvents))

        bt_action.setOnClickListener { viewModel.invokeAction(FeatureContract.Action.GoToSecondPage) }
    }

    private fun handleViewStates(state: FeatureContract.State) {
        when(state) {
            FeatureContract.State.DisplayViewExample1 -> view_example1.isVisible = true
            FeatureContract.State.DisplayViewExample2 -> view_example2.isVisible = true
            FeatureContract.State.DisplayViewExample3 -> view_example3.isVisible = true
        }
    }

    private fun handleViewEvents(event: FeatureContract.Event) {
        when(event) {
            FeatureContract.Event.NavigateToSecondPage -> findNavController().navigate(
                FeatureFragmentDirections.actionGoToSecondFragment()
            )
        }
    }
}