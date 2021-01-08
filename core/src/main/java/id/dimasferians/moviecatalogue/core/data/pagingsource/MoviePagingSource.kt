package id.dimasferians.moviecatalogue.core.data.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import id.dimasferians.moviecatalogue.core.data.source.remote.RemoteDataSource
import id.dimasferians.moviecatalogue.core.data.source.remote.network.ApiResponse
import id.dimasferians.moviecatalogue.core.domain.model.Movie
import id.dimasferians.moviecatalogue.core.utils.toListMovieDomain
import kotlinx.coroutines.flow.first

class MoviePagingSource(private val remoteDataSource: RemoteDataSource) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            // Start refresh at page 1 if undefined.
            val currentPage = params.key ?: STARTING_PAGE
            return when (val response = remoteDataSource.getPopularMovie(currentPage).first()) {
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
            Log.e(TAG, e.message.toString())
            return LoadResult.Error(e)
        }
    }

    companion object {
        const val STARTING_PAGE = 1
        val TAG: String = MoviePagingSource::class.java.simpleName
    }

}