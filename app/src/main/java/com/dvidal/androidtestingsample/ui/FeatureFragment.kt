package com.dvidal.androidtestingsample.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dvidal.androidtestingsample.R
import com.dvidal.androidtestingsample.databinding.FragmentFeatureBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeatureFragment: Fragment(R.layout.fragment_feature) {

    private val viewModel: FeatureContract.ViewModel by viewModels<FeatureViewModel>()

    private var currentBinding: FragmentFeatureBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentBinding = FragmentFeatureBinding.bind(view)

        viewModel.invokeAction(FeatureContract.Action.InitPage)

        viewModel.states.observe(viewLifecycleOwner, Observer(::handleViewStates))
        viewModel.events.observe(viewLifecycleOwner, Observer(::handleViewEvents))

        currentBinding?.btAction?.setOnClickListener { viewModel.invokeAction(FeatureContract.Action.GoToSecondPage) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentBinding = null
    }

    private fun handleViewStates(state: FeatureContract.State) {
        when(state) {
            FeatureContract.State.DisplayViewExample1 -> currentBinding?.viewExample1?.isVisible = true
            FeatureContract.State.DisplayViewExample2 -> currentBinding?.viewExample2?.isVisible = true
            FeatureContract.State.DisplayViewExample3 -> currentBinding?.viewExample3?.isVisible = true
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