package me.yisuk.kim.marvel.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import me.yisuk.kim.marvel.SharedElementHelper
import me.yisuk.kim.marvel.commons.SingleLiveEvent
import me.yisuk.kim.marvel.data.entities.MarvelComic
import javax.inject.Inject

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
class HomeNavigatorViewModel @Inject constructor() : ViewModel(), HomeNavigator {
    override fun showComicDetails(comic: MarvelComic, sharedElements: SharedElementHelper?) {
        _showComicDetailCall.value = Pair(comic, sharedElements)
    }

    private val _showComicDetailCall = SingleLiveEvent<Pair<MarvelComic, SharedElementHelper?>>()
    val showComicDetailCall: LiveData<Pair<MarvelComic, SharedElementHelper?>>
        get() = _showComicDetailCall

}