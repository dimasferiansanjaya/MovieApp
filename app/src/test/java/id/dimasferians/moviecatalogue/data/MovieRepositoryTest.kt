package id.dimasferians.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.dimasferians.moviecatalogue.MainCoroutineRule
import id.dimasferians.moviecatalogue.core.data.MovieRepositoryImpl
import id.dimasferians.moviecatalogue.core.data.source.local.entity.MovieEntity
import id.dimasferians.moviecatalogue.core.data.source.local.entity.TvShowEntity
import id.dimasferians.moviecatalogue.core.data.source.local.LocalDataSource
import id.dimasferians.moviecatalogue.core.data.source.remote.RemoteDataSource
import id.dimasferians.moviecatalogue.core.data.source.remote.network.ApiResponse
import id.dimasferians.moviecatalogue.core.data.source.remote.response.MovieDetailResponse
import id.dimasferians.moviecatalogue.core.data.source.remote.response.TvShowDetailResponse
import id.dimasferians.moviecatalogue.utils.DummyData
import id.dimasferians.moviecatalogue.utils.toMovieDetail
import id.dimasferians.moviecatalogue.utils.toTvShowDetail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    // Subject under test
    private lateinit var repository: MovieRepositoryImpl

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()


    // Dummy Data For Testing
    private val listMovieResponse = DummyData.generateListMovieResponse()
    private val listTvShowResponse = DummyData.generateListTvShowResponse()

    private val listMovie = listMovieResponse.listMovies
    private val listTvShow = listTvShowResponse.listTvShow

    private val movieId = listMovie[0].id
    private val movieDetailResponse = DummyData.generateMovieDetailResponse(movieId)

    private val tvShowId = listTvShow[0].id
    private val tvShowDetailResponse = DummyData.generateTvShowDetailResponse(tvShowId)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository =
            MovieRepositoryImpl(remoteDataSource, localDataSource, mainCoroutineRule.dispatcher)
    }


    /** Movie Section **/

    @Test
    fun `get remote movie detail by id return success`() {
        mainCoroutineRule.runBlockingTest {
            // Given
            val fakeFlow: Flow<ApiResponse<MovieDetailResponse>> = flow {
                delay(100L) // assume network call load time
                emit(ApiResponse.Success(movieDetailResponse))
            }

            // When
            `when`(remoteDataSource.getMovieById(movieId)).thenReturn(fakeFlow)

            // Then
            val result = repository.getRemoteMovieDetailById(movieId).toList() // convert to list

            /* verify method called */
            verify(remoteDataSource).getMovieById(movieId)

            /* verify first emit always Resource Loading
             * Because of Resource.Loading emitting null data
             * we can check using assertNull
             **/
            assertNull(result.first().data)

            /* verify last emit always Resource Success
            *  Because of Resource.Success emitting data
            *  we can check using assertNotNull & assertEquals
            **/
            assertNotNull(result.last().data)
            assertEquals(movieDetailResponse.toMovieDetail(), result.last().data)
        }
    }

    @Test
    fun `check movie is favorite`() {
        mainCoroutineRule.runBlockingTest {
            // Given
            val movie = MovieEntity(1, "Test aja", "Lorem ipsum", "/image", 8.7, 100, "movie")
            val fakeFlow: Flow<MovieEntity?> = flow {
                delay(100L) // assume network call loading time
                emit(movie)
            }

            // When
            `when`(localDataSource.isFavoriteMovie(movie.id)).thenReturn(fakeFlow)

            // Then
            val result = repository.isFavoriteMovie(movie.id).firstOrNull()

            /* verify method called */
            verify(localDataSource).isFavoriteMovie(movie.id)

            /* verify data exist & not null */
            assertNotNull(result)
            assertEquals(movie, result)
        }
    }

    @Test
    fun `check movie is not favorite`() {
        mainCoroutineRule.runBlockingTest {
            // Given
            val wrongId = 10
            val movie: MovieEntity? = null
            val fakeFlow: Flow<MovieEntity?> = flow {
                delay(100L) // assume network call loading time
                emit(movie)
            }

            // When
            `when`(localDataSource.isFavoriteMovie(wrongId)).thenReturn(fakeFlow)

            // Then
            val result = repository.isFavoriteMovie(wrongId).firstOrNull()

            /* verify method called */
            verify(localDataSource).isFavoriteMovie(wrongId)

            /* verify data must be null */
            assertNull(result)
        }
    }

    /** End of Movie Section **/


    /** Tv Show Section **/

    @Test
    fun `get remote tv show detail by id return success`() {
        mainCoroutineRule.runBlockingTest {
            // Given
            val fakeFlow: Flow<ApiResponse<TvShowDetailResponse>> = flow {
                delay(100L)
                emit(ApiResponse.Success(tvShowDetailResponse))
            }

            // When
            `when`(remoteDataSource.getTvById(tvShowId)).thenReturn(fakeFlow)

            // Then
            val result = repository.getRemoteTvShowDetailById(tvShowId).toList() // convert to list

            /* verify method called */
            verify(remoteDataSource).getTvById(tvShowId)

            /* verify first emit always Resource Loading
             * Because of Resource.Loading emitting null data
             * we can check using assertNull
             **/
            assertNull(result.first().data)

            /* verify last emit always Resource Success
            *  Because of Resource.Success emitting data
            *  we can check using assertNotNull & assertEquals
            **/
            assertNotNull(result.last().data)
            assertEquals(tvShowDetailResponse.toTvShowDetail(), result.last().data)
        }
    }

    @Test
    fun `check tv show is favorite`() {
        mainCoroutineRule.runBlockingTest {
            // Given
            val tvShow = TvShowEntity(1, "Test aja", "Lorem ipsum", "/image", 8.7, 100, "movie")
            val fakeFlow: Flow<TvShowEntity?> = flow {
                delay(100L) // assume network call load time
                emit(tvShow)
            }

            // When
            `when`(localDataSource.isFavoriteTv(tvShow.id)).thenReturn(fakeFlow)

            // Then
            val result = repository.isFavoriteTvShow(tvShow.id).firstOrNull()

            /* verify method called */
            verify(localDataSource).isFavoriteTv(tvShow.id)

            /* verify data exist & not null */
            assertNotNull(result)
            assertEquals(tvShow, result)
        }
    }

    @Test
    fun `check tv show is not favorite`() {
        mainCoroutineRule.runBlockingTest {
            // Given
            val wrongId = 10
            val tvShow: TvShowEntity? = null
            val fakeFlow: Flow<TvShowEntity?> = flow {
                delay(100L) // assume network call load time
                emit(tvShow)
            }

            // When
            `when`(localDataSource.isFavoriteTv(wrongId)).thenReturn(fakeFlow)

            // Then
            val result = repository.isFavoriteTvShow(wrongId).firstOrNull()

            /* verify method called */
            verify(localDataSource).isFavoriteTv(wrongId)

            /* verify data must be null */
            assertNull(result)
        }
    }

    /** End of Tv Show Section **/

}