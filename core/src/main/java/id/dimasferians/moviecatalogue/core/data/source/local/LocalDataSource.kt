package id.dimasferians.moviecatalogue.core.data.source.local

import id.dimasferians.moviecatalogue.core.data.source.local.dao.MovieDao
import id.dimasferians.moviecatalogue.core.data.source.local.dao.TvShowDao
import id.dimasferians.moviecatalogue.core.data.source.local.entity.MovieEntity
import id.dimasferians.moviecatalogue.core.data.source.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao
) {

    // get local favorite movie
    fun getFavoriteMovie(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovie()

    // add movie to favorite
    suspend fun insertMovie(movie: MovieEntity) = movieDao.insertMovie(movie)

    // check movie is favorite
    fun isFavoriteMovie(id: Int): Flow<MovieEntity?> =
        movieDao.getMovieById(id).distinctUntilChanged()

    // delete movie from favorite
    suspend fun deleteMovie(movie: MovieEntity) = movieDao.deleteMovie(movie)

    // get local favorite tv show
    fun getFavoriteTvShow(): Flow<List<TvShowEntity>> = tvShowDao.getFavoriteTvShow()

    // add tv show to favorite
    suspend fun insertTvShow(tvShow: TvShowEntity) = tvShowDao.insertTvShow(tvShow)

    // check tv show is favorite
    fun isFavoriteTvShow(id: Int): Flow<TvShowEntity?> =
        tvShowDao.getTvShowById(id).distinctUntilChanged()

    // delete tv show from favorite
    suspend fun deleteTvShow(tvShow: TvShowEntity) = tvShowDao.deleteTvShow(tvShow)
}