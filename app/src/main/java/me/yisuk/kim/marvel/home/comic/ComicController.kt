package me.yisuk.kim.marvel.home.comic

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import androidx.core.view.doOnLayout
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagingEpoxyController
import me.yisuk.kim.marvel.ComicGridItemBindingModel_
import me.yisuk.kim.marvel.commons.GlideApp
import me.yisuk.kim.marvel.data.entities.DigitalComicListItem
import me.yisuk.kim.marvel.extentions.loadFromUrl
import me.yisuk.kim.marvel.infiniteLoading

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 11-04-2018.
 */
class ComicController : PagingEpoxyController<DigitalComicListItem?>() {

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
                    item != null -> ComicGridItemBindingModel_()
                            .id(item.generateStableId())
                            .title(item.comic?.title)
                            .clickListener(View.OnClickListener {
                                callbacks?.onItemClicked(item)
                            })
                            .thumbnailPath(item.comic?.getThumbnailPathWithXlargeSize())
                    else -> ComicGridItemBindingModel_()
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

object TotalSpanOverride : EpoxyModel.SpanSizeOverrideCallback {
    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int) = totalSpanCount
}

@BindingAdapter("android:marvelThumbnail")
fun loadMarvelImage(view: ImageView, thumbnailPath: String) {
    GlideApp.with(view).clear(view)
    view.doOnLayout {
        view.loadFromUrl(thumbnailPath)
    }
}