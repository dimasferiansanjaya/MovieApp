package id.dimasferians.moviecatalogue.core.data.source.local

import id.dimasferians.moviecatalogue.core.data.source.local.entity.MovieEntity
import id.dimasferians.moviecatalogue.core.data.source.local.entity.TvShowEntity
import id.dimasferians.moviecatalogue.core.data.source.local.room.MovieDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val movieDatabase: MovieDatabase
) {

    // get local favorite movie
    fun getFavoriteMovie(): Flow<List<MovieEntity>> = movieDatabase.movieDao().getFavoriteMovie()

    // add movie to favorite
    suspend fun insertMovie(movie: MovieEntity) = movieDatabase.movieDao().insertMovie(movie)

    // check movie is favorite
    fun isFavoriteMovie(id: Int): Flow<MovieEntity?> =
        movieDatabase.movieDao().getMovieById(id).distinctUntilChanged()

    // delete movie from favorite
    suspend fun deleteMovie(movie: MovieEntity) = movieDatabase.movieDao().deleteMovie(movie)

    // get local favorite tv show
    fun getFavoriteTvShow(): Flow<List<TvShowEntity>> = movieDatabase.TvShowDao().getFavoriteTvShow()

    // add tv show to favorite
    suspend fun insertTvShow(tvShow: TvShowEntity) = movieDatabase.TvShowDao().insertTvShow(tvShow)

    // check tv show is favorite
    fun isFavoriteTvShow(id: Int): Flow<TvShowEntity?> =
        movieDatabase.TvShowDao().getTvShowById(id).distinctUntilChanged()

    // delete tv show from favorite
    suspend fun deleteTvShow(tvShow: TvShowEntity) = movieDatabase.TvShowDao().deleteTvShow(tvShow)
}