package com.cider.cider.utils.behavior

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout


class CustomBehavior : CoordinatorLayout.Behavior<View>() {
    private var appBarHeight = 0
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (appBarHeight == 0) {
            appBarHeight = dependency.height
        }
        val translationY = Math.max(0f, dependency.y - appBarHeight)
        child.translationY = translationY
        return true
    }
}
