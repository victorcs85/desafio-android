package com.picpay.desafio.android.extensions

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.withId

fun Int.checkNumberItems(quantity: Int) {
    onView(withId(this))
        .check(matches(hasChildCount(quantity)))
}