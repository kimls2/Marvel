package me.yisuk.kim.marvel.injection

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import me.yisuk.kim.marvel.MarvelApplication
import me.yisuk.kim.marvel.comicdetails.ComicDetailsBuilder
import me.yisuk.kim.marvel.data.DatabaseModule
import me.yisuk.kim.marvel.home.HomeBuilder
import me.yisuk.kim.marvel.remote.RemoteModule
import javax.inject.Singleton

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ViewModelBuilder::class,
    DatabaseModule::class,
    RemoteModule::class,
    HomeBuilder::class,
    ComicDetailsBuilder::class
])
interface AppComponent : AndroidInjector<MarvelApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MarvelApplication>()
}