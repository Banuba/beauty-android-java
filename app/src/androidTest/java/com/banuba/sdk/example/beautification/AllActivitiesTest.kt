package com.banuba.sdk.example.beautification

import com.banuba.sdk.example.beautification.helpers.*

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.banuba.sdk.example.beautification.helpers.EspressoScreenshot
import com.banuba.sdk.example.beautification.helpers.ScreenshotTestRule
import com.banuba.sdk.example.beautification.helpers.childAtPosition
import com.banuba.sdk.example.beautification.helpers.setProgress

import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class AllActivitiesTest {
    @Rule
    @JvmField
    val testName = TestName()

    @Rule
    @JvmField
    val screenshotTestRule = ScreenshotTestRule()

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.CAMERA",
                    "android.permission.WRITE_EXTERNAL_STORAGE"
            )

    @Test
    fun beautyControlsTest() {

        //  ====== Check Color Correction ======
        onView(withTagValue(`is`("seekbar_setter"))).perform(setProgress(100))
        onView(withTagValue(`is`("value_dropdown"))).perform(ViewActions.click())
        onData(anything())
                .inAdapterView(
                    childAtPosition(
                        withClassName(`is`("android.widget.PopupWindow\$PopupBackgroundView")),
                        0)
                )
                .atPosition(9).perform(click())

        // Wait until UI will be loaded to save correct screenshot
        Thread.sleep(300)
        EspressoScreenshot.takeScreenshot(testName.methodName)

        //  ====== Check Eye Brows ======
        onView(
                allOf(
                        withId(R.id.effect_selector_item_text),
                        withText("Eye brows"),
                        isDisplayed())).perform(click())
        onView(withTagValue(`is`("seekbar_setter"))).perform(setProgress(200))
        onView(withTagValue(`is`("value_dropdown"))).perform(ViewActions.click())
        onData(anything())
                .inAdapterView(
                    childAtPosition(
                        withClassName(`is`("android.widget.PopupWindow\$PopupBackgroundView")),
                        0)
                )
                .atPosition(1).perform(click())

        Thread.sleep(100)
        EspressoScreenshot.takeScreenshot(testName.methodName)

        //  ====== Check Eyes ======
        onView(
                allOf(
                        withId(R.id.effect_selector_item_text),
                        withText("Eyes"),
                        isDisplayed())).perform(click())

        // 1st seekbar
        onView(
                allOf(withId(R.id.value_setter),
                        childAtPosition(
                                allOf(
                                        withId(R.id.effect_parameters_view),
                                        childAtPosition(withClassName(Matchers.`is`("android.widget.ScrollView")),
                                                0)
                                ),
                                1)
                )).perform(ViewActions.scrollTo(), setProgress(100))

        // 2nd seekbar
        onView(
                allOf(withId(R.id.value_setter),
                        childAtPosition(
                                allOf(
                                        withId(R.id.effect_parameters_view),
                                        childAtPosition(withClassName(Matchers.`is`("android.widget.ScrollView")),
                                                0)
                                ),
                                3)
                )).perform(ViewActions.scrollTo(), setProgress(100))

        Thread.sleep(100)
        EspressoScreenshot.takeScreenshot(testName.methodName)

        //  ====== Check Lashes ======
        onView(
                allOf(
                        withId(R.id.effect_selector_item_text),
                        withText("Lashes"),
                        isDisplayed())).perform(click())

        onView(withTagValue(`is`("seekbar_setter"))).perform(setProgress(200))

        onView(withTagValue(`is`("value_dropdown"))).perform(ViewActions.click())

        onData(anything())
                .inAdapterView(
                        childAtPosition(
                        withClassName(`is`("android.widget.PopupWindow\$PopupBackgroundView")),
                        0)
                )
                .atPosition(11).perform(click())

        Thread.sleep(100)
        EspressoScreenshot.takeScreenshot(testName.methodName)

        //  ====== Check Morph ======

        // Scroll to make view visible
        onView(withId(R.id.effect_selector_view)).perform(ViewActions.swipeLeft())
        onView(
                allOf(
                        withId(R.id.effect_selector_item_text),
                        withText("Morph"),
                        isDisplayed())).perform(click())

        // 1st seekbar
        onView(
                allOf(withId(R.id.value_setter),
                        childAtPosition(
                                allOf(
                                        withId(R.id.effect_parameters_view),
                                        childAtPosition(withClassName(Matchers.`is`("android.widget.ScrollView")),
                                                0)
                                ),
                                1)
                )).perform(ViewActions.scrollTo(), setProgress(200))

        // 2nd seekbar
        onView(
                allOf(withId(R.id.value_setter),
                        childAtPosition(
                                allOf(
                                        withId(R.id.effect_parameters_view),
                                        childAtPosition(withClassName(Matchers.`is`("android.widget.ScrollView")),
                                                0)
                                ),
                                3)
                )).perform(ViewActions.scrollTo(), setProgress(200))

        // 3rd seekbar
        onView(
                allOf(withId(R.id.value_setter),
                        childAtPosition(
                                allOf(
                                        withId(R.id.effect_parameters_view),
                                        childAtPosition(withClassName(Matchers.`is`("android.widget.ScrollView")),
                                                0)
                                ),
                                5)
                )).perform(ViewActions.scrollTo(), setProgress(200))

        Thread.sleep(100)
        EspressoScreenshot.takeScreenshot(testName.methodName)

        //  ====== Check Skin ======
        onView(
                allOf(
                        withId(R.id.effect_selector_item_text),
                        withText("Skin"),
                        isDisplayed())).perform(click())

        // 1st seekbar
        onView(
                allOf(withId(R.id.value_setter),
                        childAtPosition(
                                allOf(
                                        withId(R.id.effect_parameters_view),
                                        childAtPosition(withClassName(Matchers.`is`("android.widget.ScrollView")),
                                                0)
                                ),
                                1)
                )).perform(ViewActions.scrollTo(), setProgress(200))

        // 2nd seekbar
        onView(
                allOf(withId(R.id.value_setter),
                        childAtPosition(
                                allOf(
                                        withId(R.id.effect_parameters_view),
                                        childAtPosition(withClassName(Matchers.`is`("android.widget.ScrollView")),
                                                0)
                                ),
                                3)
                )).perform(ViewActions.scrollTo(), setProgress(100))

        onView(withTagValue(`is`("value_dropdown"))).perform(ViewActions.click())

        onData(anything())
                .inAdapterView(
                        childAtPosition(
                                withClassName(`is`("android.widget.PopupWindow\$PopupBackgroundView")),
                                0)
                )
                .atPosition(0).perform(click())

        Thread.sleep(100)
        EspressoScreenshot.takeScreenshot(testName.methodName)

        //  ====== Check Teeth ======
        onView(
                allOf(
                        withId(R.id.effect_selector_item_text),
                        withText("Teeth"),
                        isDisplayed())).perform(click())

        onView(withTagValue(`is`("seekbar_setter"))).perform(setProgress(100))

        Thread.sleep(100)
        EspressoScreenshot.takeScreenshot(testName.methodName)
    }

    @Test
    fun beautyPresetsTest() {
        val presetSelector = onView(
                allOf(
                        withId(R.id.beauty_presets_selector),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                        1),
                                2),
                        isDisplayed()))

        presetSelector.perform(click())

        //  ====== Disabled state ======
        onData(anything())
                .inAdapterView(
                    childAtPosition(
                        withClassName(`is`("android.widget.PopupWindow\$PopupBackgroundView")),
                        0)
                )
                .atPosition(1).perform(click())

        Thread.sleep(300)
        EspressoScreenshot.takeScreenshot(testName.methodName)

        presetSelector.perform(click())
        //  ====== Lesser state ======
        onData(anything())
                .inAdapterView(
                    childAtPosition(
                        withClassName(`is`("android.widget.PopupWindow\$PopupBackgroundView")),
                        0)
                )
                .atPosition(2).perform(click())

        Thread.sleep(100)
        EspressoScreenshot.takeScreenshot(testName.methodName)

        presetSelector.perform(click())

        //  ====== Medium state ======
        onData(anything())
                .inAdapterView(
                    childAtPosition(
                        withClassName(`is`("android.widget.PopupWindow\$PopupBackgroundView")),
                        0)
                )
                .atPosition(3).perform(click())

        Thread.sleep(100)
        EspressoScreenshot.takeScreenshot(testName.methodName)

        presetSelector.perform(click())

        onData(anything())
                .inAdapterView(
                    childAtPosition(
                        withClassName(`is`("android.widget.PopupWindow\$PopupBackgroundView")),
                        0)
                )
                .atPosition(4).perform(click())

        Thread.sleep(100)
        EspressoScreenshot.takeScreenshot(testName.methodName)
    }

    @Test
    fun neuralNetworksTest() {

        // ToDo: Set Color and Alpha values for custom ColorSeekBar

        //  ====== Check Eyes NN ======
        onView(allOf(withId(R.id.effect_selector))).perform(click())
        onData(anything())
                .inAdapterView(
                    childAtPosition(
                        withClassName(`is`("android.widget.PopupWindow\$PopupBackgroundView")), 0)
                )
                .atPosition(1)
                .perform(click())

        Thread.sleep(1000)
        EspressoScreenshot.takeScreenshot(testName.methodName)

        //  ====== Check Hair NN ======
        onView(allOf(withId(R.id.effect_selector))).perform(click())
        onData(anything())
                .inAdapterView(
                    childAtPosition(
                        withClassName(`is`("android.widget.PopupWindow\$PopupBackgroundView")), 0)
                )
                .atPosition(2)
                .perform(click())

        Thread.sleep(1000)
        EspressoScreenshot.takeScreenshot(testName.methodName)

        //  ====== Check Hair NN ======
        onView(allOf(withId(R.id.effect_selector))).perform(click())
        onData(anything())
                .inAdapterView(
                    childAtPosition(
                        withClassName(`is`("android.widget.PopupWindow\$PopupBackgroundView")), 0)
                )
                .atPosition(3)
                .perform(click())

        Thread.sleep(1000)
        EspressoScreenshot.takeScreenshot(testName.methodName)

        //  ====== Check Hair NN ======
        onView(allOf(withId(R.id.effect_selector))).perform(click())
        onData(anything())
                .inAdapterView(
                    childAtPosition(
                        withClassName(`is`("android.widget.PopupWindow\$PopupBackgroundView")), 0)
                )
                .atPosition(4)
                .perform(click())
        Thread.sleep(1000)
        EspressoScreenshot.takeScreenshot(testName.methodName)
    }

}