package me.yisuk.kim.marvel.injection

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
internal abstract class ViewModelBuilder {

    @Binds
    internal abstract fun bindViewModelFactory(factory: MarvelViewModelFactory): ViewModelProvider.Factory
}