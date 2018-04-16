package me.yisuk.kim.marvel.home.comic

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_linear_comic.*
import me.yisuk.kim.marvel.R
import me.yisuk.kim.marvel.SharedElementHelper
import me.yisuk.kim.marvel.commons.BaseFragment
import me.yisuk.kim.marvel.commons.EndlessRecyclerViewScrollListener
import me.yisuk.kim.marvel.commons.SpacingItemDecorator
import me.yisuk.kim.marvel.commons.Status
import me.yisuk.kim.marvel.data.entities.DigitalComicListItem
import me.yisuk.kim.marvel.extentions.observeK
import me.yisuk.kim.marvel.home.HomeNavigator
import me.yisuk.kim.marvel.home.HomeNavigatorViewModel

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
class LinearComicFragment : BaseFragment<ComicViewModel>() {

    private lateinit var homeNavigator: HomeNavigator
    private lateinit var controller: LinearComicController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ComicViewModel::class.java)
        homeNavigator = ViewModelProviders.of(activity!!, viewModelFactory).get(HomeNavigatorViewModel::class.java)
        controller = LinearComicController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_linear_comic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = rv_comic.layoutManager as LinearLayoutManager
        rv_comic.apply {
            setController(controller)
            addItemDecoration(SpacingItemDecorator(paddingBottom))
            addOnScrollListener(EndlessRecyclerViewScrollListener(linearLayoutManager, { _: Int, _: RecyclerView ->
                if (userVisibleHint) {
                    viewModel.onListScrolledToEnd()
                }
            }))
        }

        controller.callbacks = object : LinearComicController.Callbacks {
            override fun onItemClicked(item: DigitalComicListItem) {
                val sharedElements = SharedElementHelper()
                rv_comic.findViewHolderForItemId(item.generateStableId())?.let {
                    sharedElements.addSharedElement(it.itemView, "poster")
                }
                viewModel.onItemClicked(item, homeNavigator, sharedElements)
            }
        }

        viewModel.liveList.observeK(this) {
            controller.setList(it)
        }

        viewModel.viewState.observeK(this) {
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        controller.isLoading = false
                    }
                    Status.ERROR -> {
                        controller.isLoading = false
                        Snackbar.make(rv_comic, it.message ?: "EMPTY", Snackbar.LENGTH_SHORT).show()
                    }
                    Status.REFRESHING -> {

                    }
                    Status.LOADING_MORE -> controller.isLoading = true
                }
            }
        }
    }
}