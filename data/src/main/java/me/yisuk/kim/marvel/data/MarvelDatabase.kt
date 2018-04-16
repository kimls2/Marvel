package me.yisuk.kim.marvel.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import me.yisuk.kim.marvel.data.daos.ComicEntryDao
import me.yisuk.kim.marvel.data.daos.MarvelComicDao
import me.yisuk.kim.marvel.data.entities.ComicEntry
import me.yisuk.kim.marvel.data.entities.MarvelComic

/**
 * @author <a href="kimls125@gmail.com">yisuk</a>
 */
@Database(
    entities = [
        MarvelComic::class,
        ComicEntry::class
    ],
    version = 1
)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun marvelComicDao(): MarvelComicDao
    abstract fun randomComicDao(): ComicEntryDao
}