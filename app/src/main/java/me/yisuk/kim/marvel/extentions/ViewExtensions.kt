package me.yisuk.kim.marvel.extentions

import android.content.res.Resources
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import me.yisuk.kim.marvel.commons.GlideApp

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
fun ImageView.loadFromUrl(imageUrl: String) {
    GlideApp.with(this).load(imageUrl).centerCrop().into(this)
}

fun ImageView.loadFromUrl(thumbnailUrl: String, imageUrl: String) {
    GlideApp.with(this)
            .load(imageUrl)
            .centerCrop()
            .thumbnail(GlideApp.with(this).load(thumbnailUrl).centerCrop())
            .into(this)
}

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, @IdRes frameId: Int) {
    val simpleTag = fragment::class.java.simpleName
    if (supportFragmentManager.findFragmentByTag(simpleTag) == null) {
        supportFragmentManager
                .beginTransaction()
                .replace(frameId, fragment, simpleTag)
                .commit()
    }
}

fun AppCompatActivity.replaceFragmentOnBackStack(fragment: Fragment, @IdRes frameId: Int) {
    val tag = fragment::class.java.simpleName
    if (supportFragmentManager.findFragmentByTag(tag) == null) {
        supportFragmentManager
                .beginTransaction()
                .replace(frameId, fragment, tag)
                .addToBackStack(tag)
                .commit()
    }
}

val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()