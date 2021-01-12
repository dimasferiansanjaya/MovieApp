package id.dimasferians.moviecatalogue.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import id.dimasferians.moviecatalogue.core.data.source.local.dao.MovieDao
import id.dimasferians.moviecatalogue.core.data.source.local.dao.TvShowDao
import id.dimasferians.moviecatalogue.core.data.source.local.room.MovieDatabase
import javax.inject.Singleton

@Module
class CoreDatabaseModule {

    @Singleton
    @Provides
    fun provideMovieDatabase(context: Context): MovieDatabase =
        Room.databaseBuilder(
            context,
            MovieDatabase::class.java, "Movie.db"
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.movieDao()

    @Singleton
    @Provides
    fun provideTvShowDao(database: MovieDatabase): TvShowDao = database.TvShowDao()
}