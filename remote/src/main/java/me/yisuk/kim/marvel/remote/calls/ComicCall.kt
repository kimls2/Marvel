package me.yisuk.kim.marvel.remote.calls

import io.reactivex.Single
import me.yisuk.kim.marvel.RxSchedulers
import me.yisuk.kim.marvel.data.DatabaseTxRunner
import me.yisuk.kim.marvel.data.daos.ComicEntryDao
import me.yisuk.kim.marvel.data.daos.MarvelComicDao
import me.yisuk.kim.marvel.data.entities.ComicEntry
import me.yisuk.kim.marvel.data.entities.DigitalComicListItem
import me.yisuk.kim.marvel.data.entities.MarvelComic
import me.yisuk.kim.marvel.remote.ComicFetcher
import me.yisuk.kim.marvel.remote.MarvelApi
import me.yisuk.kim.marvel.remote.data.Comic
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Named

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
class ComicCall @Inject constructor(
        databaseTxRunner: DatabaseTxRunner,
        comicDao: MarvelComicDao,
        comicEntryDao: ComicEntryDao,
        schedulers: RxSchedulers,
        private val marvelApi: MarvelApi,
        private val comicFetcher: ComicFetcher,
        @Named("apiPublicKey") private val publicKey: String,
        @Named("apiPrivateKey") private val privateKey: String
) : PaginatedEntryCallImpl<Comic, ComicEntry, DigitalComicListItem, ComicEntryDao>(databaseTxRunner, comicDao, comicEntryDao, schedulers) {

    override fun networkCall(page: Int): Single<List<Comic>> {
        val pair = getHash(publicKey, privateKey)
        return marvelApi.getComics(apiKey = publicKey, timeStamp = pair.first, md5Hash = pair.second, offset = page + 1)
                .map { it.data.results }
    }

    override fun loadComic(response: Comic): Single<MarvelComic> {
        return comicFetcher.loadComic(response.id, response).toSingle()
    }

    override fun mapToEntry(networkEntity: Comic, comic: MarvelComic, page: Int): ComicEntry {
        return ComicEntry(null, comic.id!!, page)
    }
}

fun getHash(publicKey: String, privateKey: String): Pair<String, String> {
    val timeStamp = System.currentTimeMillis().toString()
    val combination = timeStamp + privateKey + publicKey
    val messageDigest = MessageDigest.getInstance("MD5")
    messageDigest.update(combination.toByteArray(), 0, combination.length)
    val md5 = BigInteger(1, messageDigest.digest()).toString(16)
    return Pair(timeStamp, md5)
}

