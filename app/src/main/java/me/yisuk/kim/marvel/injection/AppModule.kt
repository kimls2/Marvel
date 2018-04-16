package me.yisuk.kim.marvel.injection

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.yisuk.kim.marvel.BuildConfig
import me.yisuk.kim.marvel.MarvelApplication
import me.yisuk.kim.marvel.RxSchedulers
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
@Module
class AppModule {
    @Provides
    fun provideContext(application: MarvelApplication): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideRxSchedulers(): RxSchedulers {
        return RxSchedulers(
                Schedulers.single(),
                Schedulers.io(),
                AndroidSchedulers.mainThread()
        )
    }

    @Provides
    @Singleton
    @Named("cache")
    fun provideCacheDir(application: MarvelApplication): File {
        return application.cacheDir
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BASIC
            }
        }
    }

    @Singleton
    @Provides
    fun provideNetworkInterceptor(): Interceptor {
        return StethoInterceptor()
    }

    @Provides
    @Singleton
    @Named("apiPrivateKey")
    fun provideMarvelPrivateKey(): String {
        return BuildConfig.MARVEL_API_PRIVATE_KEY
    }

    @Provides
    @Singleton
    @Named("apiPublicKey")
    fun provideApiPublicKey(): String {
        return BuildConfig.MARVEL_API_PUBLIC_KEY
    }

}