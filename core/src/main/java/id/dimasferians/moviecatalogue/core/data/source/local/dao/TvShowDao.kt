package id.dimasferians.moviecatalogue.core.data.source.local.dao

import androidx.room.*
import id.dimasferians.moviecatalogue.core.data.source.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Query("SELECT * FROM tv_show")
    fun getFavoriteTvShow(): Flow<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvShow: TvShowEntity)

    @Query("SELECT * FROM tv_show WHERE id IN (:id)")
    fun getTvShowById(id: Int): Flow<TvShowEntity?>

    @Delete
    suspend fun deleteTvShow(tvShow: TvShowEntity)


}