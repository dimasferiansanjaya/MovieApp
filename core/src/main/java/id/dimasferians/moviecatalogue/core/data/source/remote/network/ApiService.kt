package id.dimasferians.moviecatalogue.core.data.source.remote.network

import id.dimasferians.moviecatalogue.core.data.source.remote.response.ListMovieResponse
import id.dimasferians.moviecatalogue.core.data.source.remote.response.ListTvShowResponse
import id.dimasferians.moviecatalogue.core.data.source.remote.response.MovieDetailResponse
import id.dimasferians.moviecatalogue.core.data.source.remote.response.TvShowDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovie(@Query("page") page: Int): Response<ListMovieResponse>

    @GET("tv/popular")
    suspend fun getPopularTvShow(@Query("page") page: Int): Response<ListTvShowResponse>

    @GET("search/movie")
    suspend fun getMovieByQuery(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<ListMovieResponse>

    @GET("movie/{id}")
    suspend fun getMovieById(@Path("id") id: Int): Response<MovieDetailResponse>

    @GET("search/tv")
    suspend fun getTvShowByQuery(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<ListTvShowResponse>

    @GET("tv/{id}")
    suspend fun getTvShowById(@Path("id") id: Int): Response<TvShowDetailResponse>
}