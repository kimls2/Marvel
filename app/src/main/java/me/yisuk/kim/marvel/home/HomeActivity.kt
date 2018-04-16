package me.yisuk.kim.marvel.home

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import me.yisuk.kim.marvel.R
import me.yisuk.kim.marvel.SharedElementHelper
import me.yisuk.kim.marvel.comicdetails.ComicDetailsActivity
import me.yisuk.kim.marvel.data.entities.MarvelComic
import me.yisuk.kim.marvel.extentions.observeK
import me.yisuk.kim.marvel.extentions.replaceFragmentInActivity
import me.yisuk.kim.marvel.home.comic.ComicFragment
import me.yisuk.kim.marvel.home.comic.LinearComicFragment
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var navigatorViewModel: HomeNavigatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        setSupportActionBar(home_toolbar)
        home_toolbar.inflateMenu(R.menu.search_menu)
        home_toolbar.apply {
            inflateMenu(R.menu.search_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_search -> {
                        Snackbar.make(content, "Search screen needs to be implemented.", Snackbar.LENGTH_SHORT).show()
                    }
                    else -> {
                    }
                }
                true
            }
        }

        val drawerToggle = ActionBarDrawerToggle(this, drawer_layout, home_toolbar, 0, 0)
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        navigatorViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeNavigatorViewModel::class.java)

        navigatorViewModel.showComicDetailCall.observeK(this, this::goToDetail)

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_grid -> {
                    replaceFragmentInActivity(ComicFragment(), content.id)
                    drawer_layout.closeDrawers()
                    true
                }

                R.id.nav_home -> {
                    replaceFragmentInActivity(LinearComicFragment(), content.id)
                    drawer_layout.closeDrawers()
                    true
                }
                else -> {
                    false
                }
            }
        }

        replaceFragmentInActivity(LinearComicFragment(), content.id)
        nav_view.menu.findItem(R.id.nav_home)?.isChecked = true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("RestrictedApi")
    private fun goToDetail(pair: Pair<MarvelComic, SharedElementHelper?>?) {
        pair?.let {
            startActivityForResult(
                    ComicDetailsActivity.createIntent(this, it.first.id!!),
                    0,
                    it.second?.applyToIntent(this))
        }

    }
}
