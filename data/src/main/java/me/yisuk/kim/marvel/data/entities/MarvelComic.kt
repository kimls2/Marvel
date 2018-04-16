package me.yisuk.kim.marvel.data.entities

import android.arch.persistence.room.*
import kotlin.reflect.KMutableProperty0

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
@Entity(
        tableName = "comics",
        indices = [
            (Index(value = ["marvel_comic_id"], unique = true))
        ]
)
data class MarvelComic(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long? = null,
        @ColumnInfo(name = "marvel_comic_id") var marvelComicId: Int? = null,
        @ColumnInfo(name = "title") var title: String? = null,
        @ColumnInfo(name = "format") var format: String? = null,
        @ColumnInfo(name = "description") var description: String? = null,
        @ColumnInfo(name = "thumbnail_path") var thumbnailPath: String? = null,
        @ColumnInfo(name = "thumbnail_extension") var thumbnailFileType: String? = null

) {
    @Ignore constructor() : this(null)

    fun <T> updateProperty(entityVar: KMutableProperty0<T?>, updateVal: T?) {
        when {
            updateVal != null -> entityVar.set(updateVal)
        }
    }

    fun getThumbnailPathWithXlargeSize(): String {
        return "$thumbnailPath/$PORTRAIT_XLARGE.$thumbnailFileType"
    }

    fun getThumbnailPathWithDetailSize(): String {
        return "$thumbnailPath/$DETAIL.$thumbnailFileType"
    }

    companion object {
        private const val PORTRAIT_XLARGE = "portrait_xlarge"
        private const val DETAIL = "detail"
    }
}