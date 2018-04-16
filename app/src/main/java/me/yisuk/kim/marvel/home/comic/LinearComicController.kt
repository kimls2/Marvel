package me.yisuk.kim.marvel.home.comic

import android.view.View
import com.airbnb.epoxy.paging.PagingEpoxyController
import me.yisuk.kim.marvel.ComicCardItemBindingModel_
import me.yisuk.kim.marvel.data.entities.DigitalComicListItem
import me.yisuk.kim.marvel.infiniteLoading

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 11-04-2018.
 */
class LinearComicController : PagingEpoxyController<DigitalComicListItem?>() {

    interface Callbacks {
        fun onItemClicked(item: DigitalComicListItem)
    }

    internal var callbacks: Callbacks? = null

    var isLoading = false
        set(value) {
            if (value != field) {
                field = value
                requestModelBuild()
            }
        }

    override fun buildModels(items: MutableList<DigitalComicListItem?>) {
        if (!items.isEmpty()) {
            items.forEachIndexed { index, item ->
                when {
                    item != null -> ComicCardItemBindingModel_()
                            .id(item.generateStableId())
                            .comic(item.comic)
                            .clickListener(View.OnClickListener {
                                callbacks?.onItemClicked(item)
                            })
                    else -> ComicCardItemBindingModel_()
                            .id("placeHolder_$index")
                            .spanSizeOverride(TotalSpanOverride)
                }.addTo(this)
            }
        }

        if (isLoading) {
            infiniteLoading {
                id("loading_progress_view")
                spanSizeOverride(TotalSpanOverride)
            }
        }
    }
}

