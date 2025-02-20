package com.picpay.desafio.android.core

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

private const val NEGATE = -1

class RecyclerViewMatcher(private val recyclerViewId: Int) {

    fun atPosition(position: Int): Matcher<View> {
        return atPositionOnView(position, NEGATE)
    }

    fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            private var resources: Resources? = null
            private var childView: View? = null

            override fun describeTo(description: Description) {
                var idDescription = recyclerViewId.toString()
                resources?.let {
                    idDescription = try {
                        it.getResourceName(recyclerViewId)
                    } catch (ex: Resources.NotFoundException) {
                        String.format(
                            "%s (resource name not found)",
                            Integer.valueOf(recyclerViewId),
                        )
                    }
                }

                description.appendText("with id: $idDescription")
            }

            override fun matchesSafely(view: View): Boolean {
                if (childView == null) {
                    val recyclerView = view.rootView.findViewById<RecyclerView>(recyclerViewId)
                    childView = recyclerView.findViewHolderForAdapterPosition(position)?.itemView
                }

                return if (targetViewId == NEGATE) {
                    view === childView
                } else {
                    val targetView: View? = childView?.findViewById(targetViewId)
                    view === targetView
                }
            }
        }
    }
}
