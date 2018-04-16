package me.yisuk.kim.marvel.home

import me.yisuk.kim.marvel.SharedElementHelper
import me.yisuk.kim.marvel.data.entities.MarvelComic

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
interface HomeNavigator {
    fun showComicDetails(comic: MarvelComic, sharedElements: SharedElementHelper?)
}