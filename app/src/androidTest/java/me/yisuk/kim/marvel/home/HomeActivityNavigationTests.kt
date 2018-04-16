package me.yisuk.kim.marvel.home

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.NoActivityResumedException
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import junit.framework.TestCase.fail
import me.yisuk.kim.marvel.R
import me.yisuk.kim.marvel.utils.clickChildViewWithId
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 11-04-2018.
 */
@RunWith(AndroidJUnit4::class)
class HomeActivityNavigationTests {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun clickOnGridRecyclerViewItem_ShowsComicDetailsScreen() {
        Thread.sleep(1000)
        clickOnRecyclerViewChildItemAtPosition(R.id.rv_comic, 0, R.id.card_item)

        Thread.sleep(1000)
        onView(withId(R.id.details_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.details_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_title)).check(matches(isDisplayed()))
    }

    @Test
    fun detailScreen_backButtonTest() {
        Thread.sleep(1000)
        clickOnRecyclerViewChildItemAtPosition(R.id.rv_comic, 0, R.id.card_item)

        Thread.sleep(1000)
        pressBack()
        Thread.sleep(1000)
        onView(withId(R.id.home_toolbar)).check(matches(isDisplayed()))
    }

    @Test
    fun backFromHomeScreen_ExitsApp() {
        try {
            pressBack()
            fail("Should kill the app and throw an exception")
        } catch (e: NoActivityResumedException) {
            // Test OK
        }
    }

    private fun scrollRecyclerViewToPosition(recyclerViewId: Int, position: Int) {
        onView(allOf(isDisplayed(), withId(recyclerViewId))).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))
    }

    private fun clickOnRecyclerViewChildItemAtPosition(recyclerViewId: Int, position: Int, childViewId: Int) {
        onView(allOf<View>(isDisplayed(), withId(recyclerViewId)))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, clickChildViewWithId(childViewId)))
    }

    private fun getResourceString(id: Int): String {
        val targetContext = InstrumentationRegistry.getTargetContext()
        return targetContext.resources.getString(id)
    }
}