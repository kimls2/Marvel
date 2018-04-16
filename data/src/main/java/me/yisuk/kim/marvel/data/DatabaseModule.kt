package me.yisuk.kim.marvel.data

import android.arch.persistence.room.Room
import android.content.Context
import android.os.Debug
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): MarvelDatabase {
        val builder = Room.databaseBuilder(context, MarvelDatabase::class.java, "comics.db")
                .fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build()
    }

    @Provides
    fun provideMarvelComicDao(db: MarvelDatabase) = db.marvelComicDao()

    @Provides
    fun provideRandomComicDao(db: MarvelDatabase) = db.randomComicDao()

    @Singleton
    @Provides
    fun provideDatabaseTransactionRunner(db: MarvelDatabase): DatabaseTxRunner =
            DatabaseTxRunner(db)
}