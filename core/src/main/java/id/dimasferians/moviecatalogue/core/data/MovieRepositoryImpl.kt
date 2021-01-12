package id.dimasferians.moviecatalogue.core.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.dimasferians.moviecatalogue.core.data.pagingsource.MoviePagingSource
import id.dimasferians.moviecatalogue.core.data.pagingsource.MovieSearchPagingSource
import id.dimasferians.moviecatalogue.core.data.pagingsource.TvSearchPagingSource
import id.dimasferians.moviecatalogue.core.data.pagingsource.TvShowPagingSource
import id.dimasferians.moviecatalogue.core.data.source.local.LocalDataSource
import id.dimasferians.moviecatalogue.core.data.source.remote.RemoteDataSource
import id.dimasferians.moviecatalogue.core.data.source.remote.network.ApiResponse
import id.dimasferians.moviecatalogue.core.domain.model.Movie
import id.dimasferians.moviecatalogue.core.domain.model.MovieDetail
import id.dimasferians.moviecatalogue.core.domain.model.TvShow
import id.dimasferians.moviecatalogue.core.domain.model.TvShowDetail
import id.dimasferians.moviecatalogue.core.domain.repository.MovieRepository
import id.dimasferians.moviecatalogue.core.utils.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dispatcher: CoroutineDispatcher
) : MovieRepository {

    companion object {
        const val TMDB_PAGE_SIZE = 20
        val TAG: String = MovieRepositoryImpl::class.java.simpleName
    }

    // get remote paging data for popular movie
    override fun getPopularMovie(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = TMDB_PAGE_SIZE,
                prefetchDistance = 1 // not recommended to set low number, default is same as page size
            )
        ) {
            MoviePagingSource(remoteDataSource)
        }.flow.flowOn(dispatcher)
    }

    // get remote paging data for movie by query
    override fun getMovieByQuery(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = TMDB_PAGE_SIZE,
                prefetchDistance = 1 // not recommended to set low number, default is same as page size
            )
        ) {
            MovieSearchPagingSource(remoteDataSource, query)
        }.flow.flowOn(dispatcher)
    }

    // get remote movie detail
    override fun getMovieById(id: Int): Flow<Resource<MovieDetail>> {
        return flow {
            // emit loading state
            emit(Resource.Loading())
            when (val response = remoteDataSource.getMovieById(id).first()) {
                is ApiResponse.Success -> emit(Resource.Success(response.data.toMovieDetailDomain()))
                is ApiResponse.Error -> emit(Resource.Error<MovieDetail>(response.errorMessage))
            }
        }.catch { e ->
            // emit exception
            emit(Resource.Error(e.message.toString()))
            Log.e(TAG, e.message.toString())
        }.flowOn(dispatcher)
    }

    // get local favorite movie
    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map {
            it.toListMovie()
        }
    }

    // Add movie to favorite
    override suspend fun insertMovie(movie: Movie) {
        withContext(dispatcher) {
            localDataSource.insertMovie(movie.toMovieEntity())
        }
    }

    // check movie is favorite
    override fun isFavoriteMovie(id: Int): Flow<Movie?> {
        return localDataSource.isFavoriteMovie(id).map {
            it?.toMovieDomain()
        }.flowOn(dispatcher)
    }

    // delete movie from favorite
    override suspend fun deleteMovie(movie: Movie) {
        withContext(dispatcher) {
            localDataSource.deleteMovie(movie.toMovieEntity())
        }
    }

    // get remote paging data for popular tv show
    override fun getPopularTvShow(): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = TMDB_PAGE_SIZE,
                prefetchDistance = 2
            )
        ) {
            TvShowPagingSource(remoteDataSource)
        }.flow.flowOn(dispatcher)
    }

    // get remote paging data for tv show by query
    override fun getTvShowByQuery(query: String): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = TMDB_PAGE_SIZE,
                prefetchDistance = 2
            )
        ) {
            TvSearchPagingSource(remoteDataSource, query)
        }.flow.flowOn(dispatcher)
    }

    // get remote tv show detail
    override fun getTvShowById(id: Int): Flow<Resource<TvShowDetail>> {
        return flow {
            // emit loading state
            emit(Resource.Loading())
            when (val response = remoteDataSource.getTvShowById(id).first()) {
                is ApiResponse.Success -> emit(Resource.Success(response.data.toTvShowDetailDomain()))
                is ApiResponse.Error -> emit(Resource.Error<TvShowDetail>(response.errorMessage))
            }
        }.catch { e ->
            // emit exception
            emit(Resource.Error(e.message.toString()))
            Log.e(TAG, e.message.toString())
        }.flowOn(dispatcher)
    }

    // get local favorite tv show
    override fun getFavoriteTvShow(): Flow<List<TvShow>> {
        return localDataSource.getFavoriteTvShow().map {
            it.toListTvShow()
        }
    }

    // add tv show to favorite
    override suspend fun insertTvShow(tvShow: TvShow) {
        withContext(dispatcher) {
            localDataSource.insertTvShow(tvShow.toTvShowEntity())
        }
    }

    // check tv show is favorite
    override fun isFavoriteTvShow(id: Int): Flow<TvShow?> {
        return localDataSource.isFavoriteTvShow(id).map {
            it?.toTvShowDomain()
        }
    }

    // delete tv show from favorite
    override suspend fun deleteTvShow(tvShow: TvShow) {
        withContext(dispatcher) {
            localDataSource.deleteTvShow(tvShow.toTvShowEntity())
        }
    }
}