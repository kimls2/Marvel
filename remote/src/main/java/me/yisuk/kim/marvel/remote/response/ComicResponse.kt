package me.yisuk.kim.marvel.remote.response

import me.yisuk.kim.marvel.remote.data.ComicData

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
data class ComicResponse(
        val code: Int,
        val status: String,
        val data: ComicData
)