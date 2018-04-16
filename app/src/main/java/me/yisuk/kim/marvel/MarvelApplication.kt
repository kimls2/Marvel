package me.yisuk.kim.marvel

import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import me.yisuk.kim.marvel.injection.DaggerAppComponent
import timber.log.Timber

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
class MarvelApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}