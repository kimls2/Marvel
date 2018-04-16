package me.yisuk.kim.marvel

import io.reactivex.Scheduler

/**
 * @author <a href="kimls125@gmail.com">yisuk</a>
 */
data class RxSchedulers(
        val database: Scheduler,
        val network: Scheduler,
        val main: Scheduler
)

