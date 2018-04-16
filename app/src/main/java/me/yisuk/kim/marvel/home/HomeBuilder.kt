package me.yisuk.kim.marvel.home

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import me.yisuk.kim.marvel.home.comic.ComicBuilder
import me.yisuk.kim.marvel.injection.ViewModelKey

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
@Module
internal abstract class HomeBuilder {
    @ContributesAndroidInjector(modules = [ComicBuilder::class])
    internal abstract fun homeActivity(): HomeActivity

    @Binds
    @IntoMap
    @ViewModelKey(HomeNavigatorViewModel::class)
    abstract fun bindHomeNavigatorViewModel(viewModel: HomeNavigatorViewModel): ViewModel
}