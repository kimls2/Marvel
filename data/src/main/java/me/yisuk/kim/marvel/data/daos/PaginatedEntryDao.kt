package me.yisuk.kim.marvel.data.daos

import io.reactivex.Flowable
import io.reactivex.Single
import me.yisuk.kim.marvel.data.PaginatedEntry
import me.yisuk.kim.marvel.data.entities.ListItem

interface PaginatedEntryDao<EC : PaginatedEntry, LI : ListItem<EC>> : EntryDao<EC, LI> {
    fun entriesPage(page: Int): Flowable<List<LI>>
    fun deletePage(page: Int)
    fun getLastPage(): Single<Int>
}
