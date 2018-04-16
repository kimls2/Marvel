package me.yisuk.kim.marvel.remote.calls

import io.reactivex.Completable
import io.reactivex.Flowable
import me.yisuk.kim.marvel.RxSchedulers
import me.yisuk.kim.marvel.calls.Call
import me.yisuk.kim.marvel.data.daos.MarvelComicDao
import me.yisuk.kim.marvel.data.entities.MarvelComic
import me.yisuk.kim.marvel.remote.ComicFetcher
import javax.inject.Inject

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 11-04-2018.
 */
class ComicDetailsCall @Inject constructor(
        private val comicDao: MarvelComicDao,
        private val comicFetcher: ComicFetcher,
        private val schedulers: RxSchedulers
) : Call<Long, MarvelComic> {

    override fun data(param: Long): Flowable<MarvelComic> {
        return comicDao.getComicWithIdFlowable(param)
                .subscribeOn(schedulers.database)
    }

    override fun refresh(param: Long): Completable {
        return comicDao.getComicWithIdMaybe(param)
                .subscribeOn(schedulers.database)
                .map(MarvelComic::marvelComicId)
                .flatMapSingle(comicFetcher::updateComic)
                .toCompletable()
    }
}