package me.yisuk.kim.marvel.calls

import android.arch.paging.DataSource
import io.reactivex.Completable
import io.reactivex.Flowable

interface Call<in Param, DatabaseOutput> {
    fun data(param: Param): Flowable<DatabaseOutput>
    fun refresh(param: Param): Completable
}

interface ListCall<in Param, DatabaseOutput> : Call<Param, List<DatabaseOutput>> {
    fun dataSourceFactory(): DataSource.Factory<Int, DatabaseOutput>
    val pageSize: Int
}