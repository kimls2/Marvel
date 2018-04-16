package me.yisuk.kim.marvel.home.comic

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import me.yisuk.kim.marvel.injection.ViewModelKey

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
@Module
internal abstract class ComicBuilder {
    @ContributesAndroidInjector
    internal abstract fun comicFragment(): ComicFragment

    @ContributesAndroidInjector
    internal abstract fun linearComicFragment():LinearComicFragment

    @Binds
    @IntoMap
    @ViewModelKey(ComicViewModel::class)
    abstract fun bindComicViewModel(viewModel: ComicViewModel): ViewModel
}