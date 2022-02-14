package com.dvidal.androidtestingsample

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dvidal.androidtestingsample.ui.FeatureContract
import com.dvidal.androidtestingsample.ui.FeatureFragment
import com.dvidal.androidtestingsample.ui.FeatureFragmentDirections
import com.dvidal.androidtestingsample.ui.FeatureViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class FeatureFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val featureViewModel: FeatureViewModel = mockk(relaxed = true)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun whenInitFragment_shouldInvokeInitPage() {

        val states = MutableStateFlow<FeatureContract.State>(FeatureContract.State.Idle)
        every { featureViewModel.states } returns states

        launchFragmentInHiltContainer<FeatureFragment>()

        verify( exactly = 1) {
            featureViewModel.invokeAction(FeatureContract.Action.InitPage)
        }
    }

    @Test
    fun whenPressButton_shouldInvokeGoToSecondPage() {

        val states = MutableStateFlow<FeatureContract.State>(FeatureContract.State.Idle)
        every { featureViewModel.states } returns states

        launchFragmentInHiltContainer<FeatureFragment>()

        Espresso.onView(withId(R.id.bt_action)).perform(ViewActions.click())
        verify( exactly = 1) {
            featureViewModel.invokeAction(FeatureContract.Action.GoToSecondPage)
        }
    }

    @Test
    fun whenEventNavigateToSecondPage_shouldCallForFeatureFragmentDirections() {

        val states = MutableStateFlow<FeatureContract.State>(FeatureContract.State.Idle)
        every { featureViewModel.states } returns states

        val events = MutableSharedFlow<FeatureContract.Event>()
        every { featureViewModel.events } returns events

        val navController = mockk<NavController>(relaxed = true)

        launchFragmentInHiltContainer<FeatureFragment>() {
            Navigation.setViewNavController(requireView(), navController)
        }

        events.tryEmit(FeatureContract.Event.NavigateToSecondPage)
        verify(exactly = 1) {
            navController.navigate(FeatureFragmentDirections.actionGoToSecondFragment())
        }
    }

    @Test
    fun whenDisplayViewExample1_shouldViewExample1DisplayedOnScreen() {

        val states = MutableStateFlow<FeatureContract.State>(FeatureContract.State.Idle)
        every { featureViewModel.states } returns states

        states.value = FeatureContract.State.DisplayViewExample1

        launchFragmentInHiltContainer<FeatureFragment>()

        Espresso.onView(withId(R.id.view_example1)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun whenDisplayViewExample2_shouldViewExample2DisplayedOnScreen() {

        val states = MutableStateFlow<FeatureContract.State>(FeatureContract.State.Idle)
        every { featureViewModel.states } returns states

        states.value = FeatureContract.State.DisplayViewExample2

        launchFragmentInHiltContainer<FeatureFragment>()

        Espresso.onView(withId(R.id.view_example2)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun whenDisplayViewExample3_shouldViewExample3DisplayedOnScreen() {

        val states = MutableStateFlow<FeatureContract.State>(FeatureContract.State.Idle)
        every { featureViewModel.states } returns states

        states.value = FeatureContract.State.DisplayViewExample3

        launchFragmentInHiltContainer<FeatureFragment>()

        Espresso.onView(withId(R.id.view_example3)).check(matches(ViewMatchers.isDisplayed()))
    }
}