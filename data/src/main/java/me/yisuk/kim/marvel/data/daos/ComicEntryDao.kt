package me.yisuk.kim.marvel.data.daos

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import io.reactivex.Flowable
import io.reactivex.Single
import me.yisuk.kim.marvel.data.entities.ComicEntry
import me.yisuk.kim.marvel.data.entities.DigitalComicListItem

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
@Dao
abstract class ComicEntryDao : PaginatedEntryDao<ComicEntry, DigitalComicListItem> {
    @Transaction
    @Query("SELECT * FROM digital_comics ORDER BY page")
    abstract override fun entries(): Flowable<List<DigitalComicListItem>>

    @Transaction
    @Query("SELECT * FROM digital_comics ORDER BY page")
    abstract override fun entriesDataSource(): DataSource.Factory<Int, DigitalComicListItem>

    @Transaction
    @Query("SELECT * FROM digital_comics WHERE page = :page")
    abstract override fun entriesPage(page: Int): Flowable<List<DigitalComicListItem>>

    @Query("DELETE FROM digital_comics WHERE page = :page")
    abstract override fun deletePage(page: Int)

    @Query("DELETE FROM digital_comics")
    abstract override fun deleteAll()

    @Query("SELECT MAX(page) from digital_comics")
    abstract override fun getLastPage(): Single<Int>
}