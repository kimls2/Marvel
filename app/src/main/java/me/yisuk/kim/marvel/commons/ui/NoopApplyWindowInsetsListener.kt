package me.yisuk.kim.marvel.commons.ui

import android.view.View
import android.view.WindowInsets

object NoopApplyWindowInsetsListener : View.OnApplyWindowInsetsListener {
    override fun onApplyWindowInsets(view: View, insets: WindowInsets): WindowInsets = insets
}