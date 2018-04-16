package me.yisuk.kim.marvel.comicdetails.details

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import kotlinx.android.synthetic.main.fragment_comic_details.*
import me.yisuk.kim.marvel.R
import me.yisuk.kim.marvel.commons.BaseFragment
import me.yisuk.kim.marvel.commons.ui.NoopApplyWindowInsetsListener
import me.yisuk.kim.marvel.commons.ui.RoundRectViewOutline
import me.yisuk.kim.marvel.data.entities.MarvelComic
import me.yisuk.kim.marvel.extentions.loadFromUrl
import me.yisuk.kim.marvel.extentions.observeK

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 11-04-2018.
 */
class ComicDetailsFragment : BaseFragment<ComicDetailsFragmentViewModel>() {

    companion object {
        private const val KEY_COMIC_ID = "comic_id"

        fun create(id: Long): ComicDetailsFragment {
            return ComicDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(KEY_COMIC_ID, id)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ComicDetailsFragmentViewModel::class.java)
        arguments?.let {
            viewModel.comicId = it.getLong(KEY_COMIC_ID)
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_comic_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        details_backdrop.setOnApplyWindowInsetsListener(NoopApplyWindowInsetsListener)
        details_poster.apply {
            clipToOutline = true
            outlineProvider = RoundRectViewOutline
        }
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(details_toolbar)
            it.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_clear_white_48px)
            it.supportActionBar?.title = ""
        }
        details_toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.data.observeK(this) {
            it?.let(this::update)
        }
    }

    private fun update(marvelComic: MarvelComic) {
        details_poster.doOnLayout {
            details_poster.loadFromUrl(marvelComic.getThumbnailPathWithXlargeSize())
        }

        details_backdrop.doOnLayout {
            details_backdrop.loadFromUrl(marvelComic.getThumbnailPathWithXlargeSize(), marvelComic.getThumbnailPathWithDetailSize())
        }
        tv_detail_title.text = marvelComic.title
        tv_detail_description.text = marvelComic.description
        scheduleStartPostponedTransitions()
    }

}