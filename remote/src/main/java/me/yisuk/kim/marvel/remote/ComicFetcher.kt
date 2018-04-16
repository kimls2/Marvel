package me.yisuk.kim.marvel.remote

import io.reactivex.Maybe
import io.reactivex.Single
import me.yisuk.kim.marvel.RxSchedulers
import me.yisuk.kim.marvel.data.daos.MarvelComicDao
import me.yisuk.kim.marvel.data.entities.MarvelComic
import me.yisuk.kim.marvel.remote.calls.getHash
import me.yisuk.kim.marvel.remote.data.Comic
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
@Singleton
class ComicFetcher @Inject constructor(
        private val comicDao: MarvelComicDao,
        private val marvelApi: MarvelApi,
        @Named("apiPublicKey") private val publicKey: String,
        @Named("apiPrivateKey") private val privateKey: String,
        private val schedulers: RxSchedulers
) {

    fun loadComic(comicId: Int, entity: Comic? = null): Maybe<MarvelComic> {
        val fromDb = comicDao.getComicWithMarvelComicIdMaybe(comicId)
                .subscribeOn(schedulers.database)
        val fromEntity = entity?.let { appendRx(Maybe.just(entity)) } ?: Maybe.empty<MarvelComic>()
        return Maybe.concat(fromDb, fromEntity).firstElement()
    }

    fun updateComic(comicId: Int): Single<MarvelComic> {
        val pair = getHash(publicKey, privateKey)
        return marvelApi.getComicWithId(comicId, publicKey, pair.first, pair.second)
                .subscribeOn(schedulers.network)
                .observeOn(schedulers.database)
                .filter { it.data.results.isNotEmpty() }
                .map { it.data.results[0] }
                .toSingle()
                .flatMap(this::upsertComic)
    }

    private fun appendRx(maybe: Maybe<Comic>): Maybe<MarvelComic> {
        return maybe.observeOn(schedulers.database)
                .flatMapSingle(this::upsertComic)
                .toMaybe()
                .flatMap { comicDao.getComicWithMarvelComicIdMaybe(it.marvelComicId!!) }
    }

    private fun upsertComic(comic: Comic): Single<MarvelComic> {
        return comicDao.getComicWithMarvelComicIdMaybe(comic.id)
                .subscribeOn(schedulers.database)
                .toSingle(MarvelComic())
                .map {
                    it.apply {
                        updateProperty(this::marvelComicId, comic.id)
                        updateProperty(this::title, comic.title)
                        updateProperty(this::description, comic.description)
                        updateProperty(this::thumbnailPath, comic.thumbnail.path)
                        updateProperty(this::thumbnailFileType, comic.thumbnail.extension)
                        updateProperty(this::format, comic.format)
                    }
                }
                .map(comicDao::insertOrUpdateComic)
    }
}