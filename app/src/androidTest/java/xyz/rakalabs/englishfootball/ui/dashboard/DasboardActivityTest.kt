package xyz.rakalabs.englishfootball.ui.dashboard

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.google.android.material.tabs.TabLayout
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.R.id.*
import xyz.rakalabs.englishfootball.ui.dashboard.listmatch.MatchViewHolder
import xyz.rakalabs.englishfootball.ui.dashboard.listteam.TeamViewHolder

@RunWith(AndroidJUnit4::class)
class DasboardActivityTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(DasboardActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {
        Thread.sleep(5000)
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.match_list), isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<MatchViewHolder>(5))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
        onView(withId(add_to_favorite))
            .check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(add_to_favorite)).perform(click())
        Espresso.pressBack()
        Thread.sleep(1000)

        onView(withId(bottom_navigation))
            .check(matches(isDisplayed()))
        onView(withId(list_team)).perform(click())
        Thread.sleep(3000)
        onView(allOf(withId(R.id.list_of_team), isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<TeamViewHolder>(5))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
        onView(withId(add_to_favorite))
            .check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(add_to_favorite)).perform(click())
        Espresso.pressBack()
        Thread.sleep(1000)


        onView(withId(bottom_navigation))
            .check(matches(isDisplayed()))
        onView(withId(favorites)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1)).check(matches(isDisplayed()))
    }

    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() = allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }
}