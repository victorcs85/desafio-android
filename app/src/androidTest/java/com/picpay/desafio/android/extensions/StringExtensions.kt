package com.picpay.desafio.android.extensions

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText

fun String.isTextDisplayed(): ViewInteraction = onView(withText(this)).check(
    ViewAssertions.matches(
        isDisplayed()
    )
)