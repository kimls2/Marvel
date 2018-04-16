package me.yisuk.kim.marvel.data.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.reactivex.Flowable
import io.reactivex.Maybe
import me.yisuk.kim.marvel.data.entities.MarvelComic

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
@Dao
abstract class MarvelComicDao {

    @Query("SELECT * FROM comics WHERE marvel_comic_id = :id")
    abstract fun getComicWithMarvelComicIdMaybe(id: Int): Maybe<MarvelComic>

    @Query("SELECT * FROM comics WHERE id = :id")
    abstract fun getComicWithIdFlowable(id: Long): Flowable<MarvelComic>

    @Query("SELECT * FROM comics WHERE id = :id")
    abstract fun getComicWithIdMaybe(id: Long): Maybe<MarvelComic>

    @Insert
    abstract fun insertComic(comic: MarvelComic): Long

    @Update
    protected abstract fun updateComic(comic: MarvelComic)

    fun insertOrUpdateComic(comic: MarvelComic): MarvelComic = when {
        comic.id == null -> {
            comic.copy(id = insertComic(comic))
        }
        else -> {
            updateComic(comic)
            comic
        }
    }
}