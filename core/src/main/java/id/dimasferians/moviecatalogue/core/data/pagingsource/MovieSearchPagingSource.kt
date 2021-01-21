package id.dimasferians.moviecatalogue.core.data.pagingsource

import androidx.paging.PagingSource
import id.dimasferians.moviecatalogue.core.data.source.remote.RemoteDataSource
import id.dimasferians.moviecatalogue.core.data.source.remote.network.ApiResponse
import id.dimasferians.moviecatalogue.core.domain.model.Movie
import id.dimasferians.moviecatalogue.core.utils.toListMovieDomain
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.lang.Exception

class MovieSearchPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val query: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            // Start refresh at page 1 if undefined.
            val currentPage = params.key ?: STARTING_PAGE
            return when (val response =
                remoteDataSource.getMovieByQuery(query, currentPage).first()) {
                is ApiResponse.Success -> {
                    LoadResult.Page(
                        data = response.data.listMovies.toListMovieDomain(),
                        prevKey = if (currentPage == STARTING_PAGE) null else currentPage.minus(
                            1
                        ),
                        nextKey = if (response.data.page >= response.data.totalPage) null else currentPage.plus(
                            1
                        )
                    )
                }
                is ApiResponse.Empty -> {
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
                }
                is ApiResponse.Error -> {
                    LoadResult.Error(Exception(response.errorMessage))
                }
            }
        } catch (e: Exception) {
            Timber.e(e.message.toString())
            return LoadResult.Error(e)
        }
    }

    companion object {
        const val STARTING_PAGE = 1
    }

}