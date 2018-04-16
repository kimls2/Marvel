package me.yisuk.kim.marvel.home.comic

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.rxkotlin.plusAssign
import me.yisuk.kim.marvel.RxSchedulers
import me.yisuk.kim.marvel.SharedElementHelper
import me.yisuk.kim.marvel.commons.BaseViewModel
import me.yisuk.kim.marvel.commons.State
import me.yisuk.kim.marvel.commons.Status
import me.yisuk.kim.marvel.data.entities.DigitalComicListItem
import me.yisuk.kim.marvel.home.HomeNavigator
import me.yisuk.kim.marvel.remote.calls.ComicCall
import javax.inject.Inject

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
class ComicViewModel @Inject constructor(
        private val schedulers: RxSchedulers,
        private val comicCall: ComicCall) : BaseViewModel() {

    val liveList by lazy(mode = LazyThreadSafetyMode.NONE) {
        LivePagedListBuilder<Int, DigitalComicListItem>(
                comicCall.dataSourceFactory(),
                PagedList.Config.Builder().run {
                    setPageSize(comicCall.pageSize)
                    setEnablePlaceholders(false)
                    build()
                }
        ).run {
            build()
        }
    }

    val viewState: LiveData<State> = LiveDataReactiveStreams.fromPublisher(
            Flowable.fromPublisher(messages.toFlowable(BackpressureStrategy.LATEST)))

    fun fullRefresh() {
        disposables += comicCall.refresh(Unit)
                .observeOn(schedulers.main)
                .doOnSubscribe { sendMessage(State(Status.REFRESHING)) }
                .subscribe(this::onSuccess, this::onError)
    }

    fun onListScrolledToEnd() {
        disposables += comicCall.loadNextPage()
                .observeOn(schedulers.main)
                .doOnSubscribe { sendMessage(State(Status.LOADING_MORE)) }
                .subscribe(this::onSuccess, this::onError)
    }

    fun onItemClicked(item: DigitalComicListItem, navigator: HomeNavigator, sharedElements: SharedElementHelper?) {
        navigator.showComicDetails(item.comic!!, sharedElements)
    }
}