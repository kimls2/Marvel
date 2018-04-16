package me.yisuk.kim.marvel.commons.ui

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import me.yisuk.kim.marvel.R

object RoundRectViewOutline : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        val radius = view.resources.getDimension(R.dimen.image_round_rect_radius)
        outline.setRoundRect(0, 0, view.width, view.height, radius)
    }
}