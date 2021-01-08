package id.dimasferians.moviecatalogue.core.data.source.remote

import id.dimasferians.moviecatalogue.core.data.source.remote.network.ApiResponse
import id.dimasferians.moviecatalogue.core.data.source.remote.network.ApiService
import id.dimasferians.moviecatalogue.core.data.source.remote.response.ListMovieResponse
import id.dimasferians.moviecatalogue.core.data.source.remote.response.ListTvShowResponse
import id.dimasferians.moviecatalogue.core.data.source.remote.response.MovieDetailResponse
import id.dimasferians.moviecatalogue.core.data.source.remote.response.TvShowDetailResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher
) {

    // get remote data for popular movie
    suspend fun getPopularMovie(page: Int): Flow<ApiResponse<ListMovieResponse>> {
        return flow {
            // fetch popular movie from remote
            val apiResponse = apiService.getPopularMovie(page)
            // check for response validation
            if (apiResponse.code() == 200) {
                // parse body
                val result = apiResponse.body()
                // check response body
                if (result != null) {
                    emit(ApiResponse.Success(result))
                } else {
                    emit(ApiResponse.Empty)
                }
            } else {
                // in case of invalid api key or something else
                apiResponse.errorBody()?.charStream()?.let {
                    val errorMessage = JSONObject(it.readText()).getString("status_message")
                    // emit error message
                    emit(ApiResponse.Error(errorMessage))
                }
            }
        }.catch { e ->
            // exception
            emit(ApiResponse.Error(e.message.toString()))
        }.flowOn(dispatcher)
    }

    // get remote data for movie by query
    suspend fun getMovieByQuery(query: String, page: Int): Flow<ApiResponse<ListMovieResponse>> {
        return flow {
            // fetch trending movie by query
            val apiResponse = apiService.getMovieByQuery(query, page)
            // check for response validation
            if (apiResponse.code() == 200) {
                // parse body
                val result = apiResponse.body()
                // check response body
                if (result != null) {
                    emit(ApiResponse.Success(result))
                } else {
                    emit(ApiResponse.Empty)
                }
            } else {
                // in case of invalid api key or something else
                apiResponse.errorBody()?.charStream()?.let {
                    val errorMessage = JSONObject(it.readText()).getString("status_message")
                    // emit error message
                    emit(ApiResponse.Error(errorMessage))
                }
            }
        }.catch { e ->
            // exception
            emit(ApiResponse.Error(e.message.toString()))
        }.flowOn(dispatcher)
    }

    // get remote data for movie by id
    suspend fun getMovieById(id: Int): Flow<ApiResponse<MovieDetailResponse>> {
        return flow {
            // fetch movie by id
            val apiResponse = apiService.getMovieById(id)
            // check for response validation
            if (apiResponse.code() == 200) {
                // parse body
                val result = apiResponse.body()
                // check response body
                if (result != null) {
                    emit(ApiResponse.Success(result))
                } else {
                    emit(ApiResponse.Empty)
                }
            } else {
                // in case of invalid api key or something else
                apiResponse.errorBody()?.charStream()?.let {
                    val errorMessage = JSONObject(it.readText()).getString("status_message")
                    // emit error message
                    emit(ApiResponse.Error(errorMessage))
                }
            }
        }.catch { e ->
            // emit exception
            emit(ApiResponse.Error(e.message.toString()))
        }.flowOn(dispatcher)
    }

    // get remote data for popular tv show
    suspend fun getPopularTvShow(page: Int): Flow<ApiResponse<ListTvShowResponse>> {
        return flow {
            // fetch popular movie from remote
            val apiResponse = apiService.getPopularTvShow(page)
            // check for response validation
            if (apiResponse.code() == 200) {
                // parse body
                val result = apiResponse.body()
                // check response body
                if (result != null) {
                    emit(ApiResponse.Success(result))
                } else {
                    emit(ApiResponse.Empty)
                }
            } else {
                // in case of invalid api key or something else
                apiResponse.errorBody()?.charStream()?.let {
                    val errorMessage = JSONObject(it.readText()).getString("status_message")
                    // emit error message
                    emit(ApiResponse.Error(errorMessage))
                }
            }
        }.catch { e ->
            // exception
            emit(ApiResponse.Error(e.message.toString()))
        }.flowOn(dispatcher)
    }

    // get remote data for tv show by query
    suspend fun getTvShowByQuery(query: String, page: Int): Flow<ApiResponse<ListTvShowResponse>> {
        return flow {
            // fetch trending movie by query
            val apiResponse = apiService.getTvShowByQuery(query, page)
            // check for response validation
            if (apiResponse.code() == 200) {
                // parse body
                val result = apiResponse.body()
                // check response body
                if (result != null) {
                    emit(ApiResponse.Success(result))
                } else {
                    emit(ApiResponse.Empty)
                }
            } else {
                // in case of invalid api key or something else
                apiResponse.errorBody()?.charStream()?.let {
                    val errorMessage = JSONObject(it.readText()).getString("status_message")
                    // emit error message
                    emit(ApiResponse.Error(errorMessage))
                }
            }
        }.catch { e ->
            // exception
            emit(ApiResponse.Error(e.message.toString()))
        }.flowOn(dispatcher)
    }

    // get remote data for tv show by id
    suspend fun getTvShowById(id: Int): Flow<ApiResponse<TvShowDetailResponse>> {
        return flow {
            // fetch tv show by id
            val apiResponse = apiService.getTvShowById(id)
            // check for response validation
            if (apiResponse.code() == 200) {
                // parse body
                val result = apiResponse.body()
                // check response body
                if (result != null) {
                    emit(ApiResponse.Success(result))
                } else {
                    emit(ApiResponse.Empty)
                }
            } else {
                // in case of invalid api key or something else
                apiResponse.errorBody()?.charStream()?.let {
                    val errorMessage = JSONObject(it.readText()).getString("status_message")
                    // emit error message
                    emit(ApiResponse.Error(errorMessage))
                }
            }
        }.catch { e ->
            // emit exception
            emit(ApiResponse.Error(e.message.toString()))
        }.flowOn(dispatcher)
    }

    companion object {
        val TAG: String = RemoteDataSource::class.java.simpleName
    }

}