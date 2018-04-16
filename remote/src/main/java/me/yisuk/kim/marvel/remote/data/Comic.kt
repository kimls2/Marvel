package me.yisuk.kim.marvel.remote.data

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
data class Comic(
        val id: Int,
        val title: String,
        val description: String? = null,
        val thumbnail: Thumbnail,
        val format: String
)