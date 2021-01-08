package id.dimasferians.moviecatalogue.core.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import id.dimasferians.moviecatalogue.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("SELECT * FROM movies WHERE id IN (:id)")
    fun getMovieById(id: Int): Flow<MovieEntity?>

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)
}