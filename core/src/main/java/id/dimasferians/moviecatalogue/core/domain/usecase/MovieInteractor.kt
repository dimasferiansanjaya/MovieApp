package id.dimasferians.moviecatalogue.core.domain.usecase

import androidx.paging.PagingData
import id.dimasferians.moviecatalogue.core.data.Resource
import id.dimasferians.moviecatalogue.core.domain.model.*
import id.dimasferians.moviecatalogue.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: MovieRepository) :
    MovieUseCase {

    override fun getPopularMovie(): Flow<PagingData<Movie>> = movieRepository.getPopularMovie()

    override fun getMovieByQuery(query: String): Flow<PagingData<Movie>> =
        movieRepository.getMovieByQuery(query)

    override fun getMovieById(id: Int): Flow<Resource<MovieDetail>> =
        movieRepository.getMovieById(id)

    override fun getFavoriteMovie(): Flow<List<Movie>> = movieRepository.getFavoriteMovie()

    override suspend fun insertMovie(movie: Movie) = movieRepository.insertMovie(movie)

    override fun isFavoriteMovie(id: Int): Flow<Movie?> = movieRepository.isFavoriteMovie(id)

    override suspend fun deleteMovie(movie: Movie) = movieRepository.deleteMovie(movie)

    override fun getPopularTvShow(): Flow<PagingData<TvShow>> = movieRepository.getPopularTvShow()

    override fun getTvShowByQuery(query: String): Flow<PagingData<TvShow>> =
        movieRepository.getTvShowByQuery(query)

    override fun getTvShowById(id: Int): Flow<Resource<TvShowDetail>> =
        movieRepository.getTvShowById(id)

    override fun getFavoriteTvShow(): Flow<List<TvShow>> = movieRepository.getFavoriteTvShow()

    override suspend fun insertTvShow(tvShow: TvShow) = movieRepository.insertTvShow(tvShow)

    override fun isFavoriteTvShow(id: Int): Flow<TvShow?> = movieRepository.isFavoriteTvShow(id)

    override suspend fun deleteTvShow(tvShow: TvShow) = movieRepository.deleteTvShow(tvShow)

}