package me.yisuk.kim.marvel.comicdetails.details

import android.arch.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.plusAssign
import me.yisuk.kim.marvel.RxSchedulers
import me.yisuk.kim.marvel.commons.BaseViewModel
import me.yisuk.kim.marvel.data.entities.MarvelComic
import me.yisuk.kim.marvel.remote.calls.ComicDetailsCall
import javax.inject.Inject

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 11-04-2018.
 */
class ComicDetailsFragmentViewModel @Inject constructor(
        private val schedulers: RxSchedulers,
        private val comicDetailsCall: ComicDetailsCall
) : BaseViewModel() {

    var comicId: Long? = null
        set(value) {
            if (field != value) {
                field = value
                if (value != null) {
                    setupLiveData()
                    refresh()
                } else {
                    data.value = null
                }
            }
        }

    val data = MutableLiveData<MarvelComic>()

    private fun refresh() {
        comicId?.let {
            disposables += comicDetailsCall.refresh(it)
                    .subscribe(this::onSuccess, this::onError)
        }
    }

    private fun setupLiveData() {
        comicId?.let {
            disposables += comicDetailsCall.data(it)
                    .observeOn(schedulers.main)
                    .subscribe(data::setValue, this::onError)
        }
    }

}