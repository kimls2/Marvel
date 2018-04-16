package me.yisuk.kim.marvel.commons

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class SpacingItemDecorator(left: Int, top: Int, right: Int, bottom: Int) : RecyclerView.ItemDecoration() {

    constructor(spacing: Int) : this(spacing, spacing, spacing, spacing)

    private val spacingRect = Rect()

    init {
        spacingRect.set(left, top, right, bottom)
    }

    override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
    ) {
        outRect.set(spacingRect)
    }
}