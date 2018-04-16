package me.yisuk.kim.marvel.comicdetails.details

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import me.yisuk.kim.marvel.injection.ViewModelKey

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 11-04-2018.
 */
@Module
internal abstract class ComicDetailsFragmentBuilder {
    @ContributesAndroidInjector
    internal abstract fun comicDetailsFragment(): ComicDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(ComicDetailsFragmentViewModel::class)
    abstract fun bindComicDetailsFragmentViewModel(viewModel: ComicDetailsFragmentViewModel): ViewModel
}