package me.yisuk.kim.marvel.data.entities

import android.arch.persistence.room.*
import me.yisuk.kim.marvel.data.PaginatedEntry

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
@Entity(
        tableName = "digital_comics",
        indices = [(Index(value = ["comic_id"], unique = true))],
        foreignKeys = [
            ForeignKey(
                    entity = MarvelComic::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("comic_id"),
                    onUpdate = ForeignKey.CASCADE,
                    onDelete = ForeignKey.CASCADE
            )
        ]
)
data class ComicEntry(
        @PrimaryKey(autoGenerate = true) override val id: Long? = null,
        @ColumnInfo(name = "comic_id") override val comicId: Long,
        @ColumnInfo(name = "page") override val page: Int
) : PaginatedEntry