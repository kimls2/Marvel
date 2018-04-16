package me.yisuk.kim.marvel.commons.ui

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.WindowInsetsCompat
import android.util.AttributeSet
import android.view.View

class StatusBarHeightBehavior(context: Context, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs) {
    override fun onApplyWindowInsets(coordinatorLayout: CoordinatorLayout, child: View, insets: WindowInsetsCompat): WindowInsetsCompat {
        val lp = child.layoutParams
        lp.height = insets.systemWindowInsetTop
        child.layoutParams = lp
        return insets.consumeSystemWindowInsets()
    }
}