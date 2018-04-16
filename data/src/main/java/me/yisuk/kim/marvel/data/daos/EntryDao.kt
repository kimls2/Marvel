package me.yisuk.kim.marvel.data.daos

import android.arch.paging.DataSource
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import io.reactivex.Flowable
import me.yisuk.kim.marvel.data.Entry
import me.yisuk.kim.marvel.data.entities.ListItem

interface EntryDao<EC : Entry, LI : ListItem<EC>> {
    fun entries(): Flowable<List<LI>>
    fun entriesDataSource(): DataSource.Factory<Int, LI>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entry: EC): Long

    fun deleteAll()
}