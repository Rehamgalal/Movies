package com.scan.moviesapp


import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.scan.moviesapp.ui.MainActivity
import org.hamcrest.Matchers.not
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @ExperimentalPagingApi
    @Test
    fun checkImage() {
      launchActivity<MainActivity>()
        onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                        10)).check(matches(not(withText(""))))
    }
}