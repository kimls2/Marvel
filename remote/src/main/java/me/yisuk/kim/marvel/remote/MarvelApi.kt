package me.yisuk.kim.marvel.remote

import io.reactivex.Single
import me.yisuk.kim.marvel.remote.response.ComicResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
interface MarvelApi {
    @GET("/v1/public/comics")
    fun getComics(
            @Query("hasDigitalIssue") hasDigitalIssue: Boolean = true,
            @Query("apikey") apiKey: String,
            @Query("ts") timeStamp: String,
            @Query("hash") md5Hash: String,
            @Query("offset") offset: Int = 0,
            @Query("limit") limit: Int = 40): Single<ComicResponse>


    @GET("/v1/public/comics/{comicId}")
    fun getComicWithId(
            @Path("comicId") comicId: Int,
            @Query("apikey") apiKey: String,
            @Query("ts") timeStamp: String,
            @Query("hash") md5Hash: String
    ): Single<ComicResponse>
}