package id.dimasferians.moviecatalogue.core.di

import android.content.Context
import net.sqlcipher.database.SQLiteDatabase
import androidx.room.Room
import dagger.Module
import dagger.Provides
import id.dimasferians.moviecatalogue.core.data.source.local.dao.MovieDao
import id.dimasferians.moviecatalogue.core.data.source.local.dao.TvShowDao
import id.dimasferians.moviecatalogue.core.data.source.local.room.MovieDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
class CoreDatabaseModule {

    @Singleton
    @Provides
    fun provideMovieDatabase(context: Context): MovieDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dimasferians".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java, "Movie.db"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.movieDao()

    @Singleton
    @Provides
    fun provideTvShowDao(database: MovieDatabase): TvShowDao = database.TvShowDao()
}