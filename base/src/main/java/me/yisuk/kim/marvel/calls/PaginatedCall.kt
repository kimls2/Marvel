package me.yisuk.kim.marvel.calls

import io.reactivex.Completable
import io.reactivex.Flowable

interface PaginatedCall<in Param, DatabaseOutput> : ListCall<Param, DatabaseOutput> {
    fun data(page: Int): Flowable<List<DatabaseOutput>>
    fun loadNextPage(): Completable
}