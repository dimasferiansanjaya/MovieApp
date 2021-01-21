package id.dimasferians.moviecatalogue.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.dimasferians.moviecatalogue.core.data.source.local.entity.MovieEntity
import id.dimasferians.moviecatalogue.core.data.source.local.entity.TvShowEntity
import id.dimasferians.moviecatalogue.core.data.source.local.dao.MovieDao
import id.dimasferians.moviecatalogue.core.data.source.local.dao.TvShowDao

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun TvShowDao(): TvShowDao
}