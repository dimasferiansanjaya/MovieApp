package id.dimasferians.moviecatalogue.ui

import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import id.dimasferians.moviecatalogue.R
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    /**
     * I have try to use ActivityScenarioRule
     * But, it doesn't work
     * So, for now i'm using ActivityTestRule
     */

    @Suppress("DEPRECATION")
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    private val stringToBeTyped: String = "Harry Potter"

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun a_loadMovie() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie_tv))
            .check(matches(isDisplayed()))
            .perform(swipeUp())
    }

    @Test
    fun b_loadMovieDetail() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie_tv))
            .check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        onView(withId(R.id.img_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.rating)).check(matches(isDisplayed()))
        onView(withId(R.id.genre_chip_group)).check(matches(isDisplayed()))
        onView(withId(R.id.status)).check(matches(isDisplayed()))
        onView(withId(R.id.overview)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_share)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).check(matches(isDisplayed()))
        pressBack()
    }

    @Test
    fun c_addMovieToFavorite() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie_tv))
            .check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        onView(withId(R.id.img_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.rating)).check(matches(isDisplayed()))
        onView(withId(R.id.genre_chip_group)).check(matches(isDisplayed()))
        onView(withId(R.id.status)).check(matches(isDisplayed()))
        onView(withId(R.id.overview)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_share)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite))
            .check(matches(isDisplayed()))
            .perform(click())
        pressBack()
    }

    @Test
    fun d_loadTvShow() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_tv_show)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.rv_movie_tv))
            .check(matches(isDisplayed()))
            .perform(swipeUp())
    }

    @Test
    fun e_loadTvShowDetail() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_tv_show)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.rv_movie_tv))
            .check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        onView(withId(R.id.img_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.rating)).check(matches(isDisplayed()))
        onView(withId(R.id.genre_chip_group)).check(matches(isDisplayed()))
        onView(withId(R.id.status)).check(matches(isDisplayed()))
        onView(withId(R.id.overview)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_share)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).check(matches(isDisplayed()))
        pressBack()
    }

    @Test
    fun f_addTvShowToFavorite() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_tv_show)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.rv_movie_tv))
            .check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        onView(withId(R.id.img_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.rating)).check(matches(isDisplayed()))
        onView(withId(R.id.genre_chip_group)).check(matches(isDisplayed()))
        onView(withId(R.id.status)).check(matches(isDisplayed()))
        onView(withId(R.id.overview)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_share)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite))
            .check(matches(isDisplayed()))
            .perform(click())
        pressBack()
    }

    @Test
    fun g_loadFavoriteMovie() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_favorite)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.tab_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie_tv))
            .check(matches(isDisplayed()))
            .perform(swipeUp())
    }

    @Test
    fun h_removeMovieFromFavorite() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_favorite)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.tab_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie_tv))
            .check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        onView(withId(R.id.img_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.rating)).check(matches(isDisplayed()))
        onView(withId(R.id.genre_chip_group)).check(matches(isDisplayed()))
        onView(withId(R.id.status)).check(matches(isDisplayed()))
        onView(withId(R.id.overview)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_share)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite))
            .check(matches(isDisplayed()))
            .perform(click())
        pressBack()
    }

    @Test
    fun i_loadFavoriteTvShow() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_favorite)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.tab_layout)).check(matches(isDisplayed()))
        onView(withText("TV SHOW")).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.rv_movie_tv))
            .check(matches(isDisplayed()))
            .perform(swipeUp())
    }

    @Test
    fun j_removeTvShowFromFavorite() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_favorite)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.tab_layout)).check(matches(isDisplayed()))
        onView(withText("TV SHOW")).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.rv_movie_tv))
            .check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        onView(withId(R.id.img_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.rating)).check(matches(isDisplayed()))
        onView(withId(R.id.genre_chip_group)).check(matches(isDisplayed()))
        onView(withId(R.id.status)).check(matches(isDisplayed()))
        onView(withId(R.id.overview)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_share)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite))
            .check(matches(isDisplayed()))
            .perform(click())
        pressBack()
    }

    @Test
    fun k_searchMovie() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_search)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.tab_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.search_view)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(
            typeTextIntoFocusedView(
                stringToBeTyped
            ), closeSoftKeyboard()
        )
        onView(withId(R.id.rv_movie_tv)).check(matches(isDisplayed())).perform(swipeUp())
    }

    @Test
    fun l_searchTvShow() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_search)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.tab_layout)).check(matches(isDisplayed()))
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.search_view)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(
            typeTextIntoFocusedView(
                stringToBeTyped
            ), closeSoftKeyboard()
        )
        onView(withId(R.id.rv_movie_tv)).check(matches(isDisplayed())).perform(swipeUp())
    }

}