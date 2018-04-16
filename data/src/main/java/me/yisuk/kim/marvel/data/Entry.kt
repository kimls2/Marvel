package me.yisuk.kim.marvel.data

/**
 * @author <a href="kimls125@gmail.com">yisuk</a>
 */
interface Entry {
    val id: Long?
    val comicId: Long
}

interface PaginatedEntry : Entry {
    val page: Int
}