package me.yisuk.kim.marvel.remote.calls

import android.arch.paging.DataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import me.yisuk.kim.marvel.RetryAfterTimeoutWithDelay
import me.yisuk.kim.marvel.RxSchedulers
import me.yisuk.kim.marvel.calls.PaginatedCall
import me.yisuk.kim.marvel.data.DatabaseTxRunner
import me.yisuk.kim.marvel.data.PaginatedEntry
import me.yisuk.kim.marvel.data.daos.MarvelComicDao
import me.yisuk.kim.marvel.data.daos.PaginatedEntryDao
import me.yisuk.kim.marvel.data.entities.ListItem
import me.yisuk.kim.marvel.data.entities.MarvelComic
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

abstract class PaginatedEntryCallImpl<TT, ET : PaginatedEntry, LI : ListItem<ET>, out ED : PaginatedEntryDao<ET, LI>>(
        private val databaseTxRunner: DatabaseTxRunner,
        protected val marvelComicDao: MarvelComicDao,
        private val entryDao: ED,
        private val schedulers: RxSchedulers,
        override val pageSize: Int = 40
) : PaginatedCall<Unit, LI> {

    override fun data(param: Unit): Flowable<List<LI>> {
        return entryDao.entries()
                .distinctUntilChanged()
                .subscribeOn(schedulers.database)
    }

    override fun data(page: Int): Flowable<List<LI>> {
        return entryDao.entriesPage(page)
                .subscribeOn(schedulers.database)
                .distinctUntilChanged()
    }

    override fun dataSourceFactory(): DataSource.Factory<Int, LI> = entryDao.entriesDataSource()

    private fun loadPage(page: Int = 0, resetOnSave: Boolean = false): Single<List<ET>> {
        return networkCall(page)
                .subscribeOn(schedulers.network)
                .retryWhen(RetryAfterTimeoutWithDelay(3, 2000, this::shouldRetry, schedulers.network))
                .toFlowable()
                .flatMapIterable { it }
                .flatMapSingle { marvelObject ->
                    loadComic(marvelObject).map { comic -> mapToEntry(marvelObject, comic, page) }
                }
                .toList()
                .observeOn(schedulers.database)
                .doOnSuccess { savePage(it, page, resetOnSave) }
    }

    override fun refresh(param: Unit): Completable = loadPage(0, resetOnSave = true).toCompletable()

    override fun loadNextPage(): Completable {
        return entryDao.getLastPage()
                .subscribeOn(schedulers.database)
                .flatMap { loadPage(it + 1) }
                .toCompletable()
    }

    private fun savePage(items: List<ET>, page: Int, resetOnSave: Boolean) {
        databaseTxRunner.runInTransaction {
            when {
                resetOnSave -> entryDao.deleteAll()
                else -> entryDao.deletePage(page)
            }
            items.forEach { comic ->
                Timber.d("Saving entry: %s", comic)
                entryDao.insert(comic)
            }
        }
    }

    private fun shouldRetry(throwable: Throwable): Boolean = when (throwable) {
        is HttpException -> throwable.code() == 401
        is IOException -> true
        is IllegalStateException -> true
        else -> false
    }

    abstract fun mapToEntry(networkEntity: TT, comic: MarvelComic, page: Int): ET

    abstract fun loadComic(response: TT): Single<MarvelComic>

    abstract fun networkCall(page: Int): Single<List<TT>>
}
