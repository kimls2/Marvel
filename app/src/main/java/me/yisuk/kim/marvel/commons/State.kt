package me.yisuk.kim.marvel.commons

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
data class State(
        val status: Status,
        val message: String? = null
)

enum class Status {
    SUCCESS,
    ERROR,
    REFRESHING,
    LOADING_MORE
}
