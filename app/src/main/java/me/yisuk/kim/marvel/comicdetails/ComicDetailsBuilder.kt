package me.yisuk.kim.marvel.comicdetails

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.yisuk.kim.marvel.comicdetails.details.ComicDetailsFragmentBuilder

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 11-04-2018.
 */
@Module
internal abstract class ComicDetailsBuilder {
    @ContributesAndroidInjector(modules = [ComicDetailsFragmentBuilder::class])
    internal abstract fun comicDetailsActivity(): ComicDetailsActivity
}