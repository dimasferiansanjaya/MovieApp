package id.dimasferians.movieapp.core.domain.usecase

import androidx.paging.PagingData
import id.dimasferians.moviecatalogue.core.data.Resource
import id.dimasferians.moviecatalogue.core.domain.model.*
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    // Movie Section
    fun getPopularMovie(): Flow<PagingData<Movie>>

    fun getMovieByQuery(query: String): Flow<PagingData<Movie>>

    fun getMovieById(id: Int): Flow<Resource<MovieDetail>>

    fun getFavoriteMovie(): Flow<List<Movie>>

    suspend fun insertMovie(movie: Movie)

    fun isFavoriteMovie(id: Int): Flow<Movie?>

    suspend fun deleteMovie(movie: Movie)

    // Tv Show Section
    fun getPopularTvShow(): Flow<PagingData<TvShow>>

    fun getTvShowByQuery(query: String): Flow<PagingData<TvShow>>

    fun getTvShowById(id: Int): Flow<Resource<TvShowDetail>>

    fun getFavoriteTvShow(): Flow<List<TvShow>>

    suspend fun insertTvShow(tvShow: TvShow)

    fun isFavoriteTvShow(id: Int): Flow<TvShow?>

    suspend fun deleteTvShow(tvShow: TvShow)
}