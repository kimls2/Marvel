package me.yisuk.kim.marvel.data.entities

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import me.yisuk.kim.marvel.data.Entry
import java.util.*

/**
 * @author <a href="kimls125@gmail.com">yisuk</a>
 */
interface ListItem<ET : Entry> {
    var entry: ET?
    var relations: List<MarvelComic>?

    val comic: MarvelComic?
        get() = relations?.getOrNull(0)

    fun generateStableId(): Long {
        return Objects.hash(entry!!::class, comic!!.id!!).toLong()
    }
}

class DigitalComicListItem : ListItem<ComicEntry> {

    @Embedded
    override var entry: ComicEntry? = null

    @Relation(parentColumn = "comic_id", entityColumn = "id")
    override var relations: List<MarvelComic>? = null

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is DigitalComicListItem -> entry == other.entry && relations == other.relations
        else -> false
    }

    override fun hashCode(): Int = Objects.hash(entry, relations)

}