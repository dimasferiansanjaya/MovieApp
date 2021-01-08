package id.dimasferians.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import id.dimasferians.moviecatalogue.MainCoroutineRule
import id.dimasferians.moviecatalogue.core.data.MovieRepositoryImpl
import id.dimasferians.moviecatalogue.core.data.Resource
import id.dimasferians.moviecatalogue.core.data.source.local.entity.MovieEntity
import id.dimasferians.moviecatalogue.core.data.source.local.entity.TvShowEntity
import id.dimasferians.moviecatalogue.utils.DummyData
import id.dimasferians.moviecatalogue.utils.toMovieDetail
import id.dimasferians.moviecatalogue.utils.toTvShowDetail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    // Subject under test
    private lateinit var viewModel: DetailViewModel

    @Mock
    private lateinit var repository: MovieRepositoryImpl

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Captor
    private lateinit var movieCaptor: ArgumentCaptor<MovieEntity?>

    @Captor
    private lateinit var tvShowCaptor: ArgumentCaptor<TvShowEntity?>

    @Captor
    private lateinit var movieDetailCaptor: ArgumentCaptor<Resource<MovieDetail>>

    @Captor
    private lateinit var tvShowDetailCaptor: ArgumentCaptor<Resource<TvShowDetail>>

    // Dummy Data
    private val movieId = 100
    private val movieDetail = DummyData.generateMovieDetailResponse(movieId).toMovieDetail()

    private val tvShowId = 200
    private val tvShowDetail = DummyData.generateTvShowDetailResponse(tvShowId).toTvShowDetail()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = DetailViewModel(repository)
    }

    @Test
    fun `get detail movie by id return success`() {
        mainCoroutineRule.runBlockingTest {
            // Given
            val observer: Observer<Resource<MovieDetail>> = mock()
            val fakeFlow: Flow<Resource<MovieDetail>> = flow {
                emit(Resource.Loading()) // first emit always resource.loading
                delay(100L) // assume network call load time
                emit(Resource.Success(movieDetail))
            }

            // When
            whenever(repository.getRemoteMovieDetailById(movieId)).thenReturn(fakeFlow)

            // Then
            viewModel.movieDetail.observeForever(observer)
            viewModel.getDetailMovie(movieId)

            /* verify method called */
            verify(repository, times(1)).getRemoteMovieDetailById(movieId)

            /* onChanged has been triggered once */
            verify(observer, times(1)).onChanged(movieDetailCaptor.capture())

            /* verify first emit always Resource Loading
             * Because of Resource.Loading emitting null data
             * we can check using assertNull
             */
            assertNull(movieDetailCaptor.value.data)

            mainCoroutineRule.advanceTimeBy(100L)

            /* onChanged has been triggered twice */
            verify(observer, times(2)).onChanged(movieDetailCaptor.capture())

            /* verify last emit always Resource Success
            *  Because of Resource.Success emitting data
            *  we can check using assertNotNull & assertEquals
            **/
            assertNotNull(movieDetailCaptor.value.data)
            assertEquals(movieDetail, movieDetailCaptor.value.data)

            // remove observer
            viewModel.movieDetail.removeObserver(observer)
        }
    }

    @Test
    fun `check movie is favorite`() {
        // Given
        val validId = 1
        val observer: Observer<MovieEntity?> = mock()
        val movie = MovieEntity(validId, "Test aja", "Lorem ipsum", "/image", 8.7, 100, "movie")
        val fakeFlow = flowOf(movie)

        // When
        whenever(repository.isFavoriteMovie(validId)).thenReturn(fakeFlow)

        // Then
        viewModel.isFavoriteMovie(validId).observeForever(observer)

        /* verify method called */
        verify(repository, times(1)).isFavoriteMovie(validId)

        verify(observer, times(1)).onChanged(movieCaptor.capture())

        /* verify data exist & not null */
        assertNotNull(movieCaptor.value)
        assertEquals(movie, movieCaptor.value)

        // remove observer
        viewModel.isFavoriteMovie(validId).removeObserver(observer)
    }

    @Test
    fun `check movie is not favorite`() {
        // Given
        val wrongId = 10
        val observer: Observer<MovieEntity?> = mock()
        val movie: MovieEntity? = null
        val fakeFlow = flowOf(movie)

        // When
        whenever(repository.isFavoriteMovie(wrongId)).thenReturn(fakeFlow)

        // Then
        viewModel.isFavoriteMovie(wrongId).observeForever(observer)

        /* verify method called */
        verify(repository, times(1)).isFavoriteMovie(wrongId)

        verify(observer, times(1)).onChanged(movieCaptor.capture())

        /* verify data must be null */
        assertNull(movieCaptor.value)

        // remove observer
        viewModel.isFavoriteMovie(wrongId).removeObserver(observer)
    }

    @Test
    fun `get detail tv show by id return success`() {
        mainCoroutineRule.runBlockingTest {
            // Given
            val observer: Observer<Resource<TvShowDetail>> = mock()
            val fakeFlow: Flow<Resource<TvShowDetail>> = flow {
                emit(Resource.Loading()) // first emit always resource.loading
                delay(100L) // assume network call load time
                emit(Resource.Success(tvShowDetail))
            }

            // When
            whenever(repository.getRemoteTvShowDetailById(tvShowId)).thenReturn(fakeFlow)

            // Then
            viewModel.tvDetail.observeForever(observer)
            viewModel.getDetailTv(tvShowId)

            /* verify method called */
            verify(repository, times(1)).getRemoteTvShowDetailById(tvShowId)

            /* onChanged has been triggered once */
            verify(observer, times(1)).onChanged(tvShowDetailCaptor.capture())

            /* verify first emit always Resource Loading
             * Because of Resource.Loading emitting null data
             * we can check using assertNull
             */
            assertNull(tvShowDetailCaptor.value.data)

            mainCoroutineRule.advanceTimeBy(100L)

            /* onChanged has been triggered twice */
            verify(observer, times(2)).onChanged(tvShowDetailCaptor.capture())

            /* verify last emit always Resource Success
            *  Because of Resource.Success emitting data
            *  we can check using assertNotNull & assertEquals
            **/
            assertNotNull(tvShowDetailCaptor.value.data)
            assertEquals(tvShowDetail, tvShowDetailCaptor.value.data)

            // remove observer
            viewModel.tvDetail.removeObserver(observer)
        }
    }

    @Test
    fun `check tv show is favorite`() {
        // Given
        val validId = 1
        val observer: Observer<TvShowEntity?> = mock()
        val tvShow = TvShowEntity(validId, "Test aja", "Lorem ipsum", "/image", 8.7, 100, "movie")
        val fakeFlow = flowOf(tvShow)

        // When
        whenever(repository.isFavoriteTvShow(validId)).thenReturn(fakeFlow)

        // Then
        viewModel.isFavoriteTvShow(validId).observeForever(observer)

        /* verify method called */
        verify(repository, times(1)).isFavoriteTvShow(validId)

        verify(observer, times(1)).onChanged(tvShowCaptor.capture())

        /* verify data exist & not null */
        assertNotNull(tvShowCaptor.value)
        assertEquals(tvShow, tvShowCaptor.value)

        // remove observer
        viewModel.isFavoriteTvShow(validId).removeObserver(observer)
    }

    @Test
    fun `check tv show is not favorite`() {
        // Given
        val wrongId = 20
        val observer: Observer<TvShowEntity?> = mock()
        val tvShow: TvShowEntity? = null
        val fakeFlow = flowOf(tvShow)

        // When
        whenever(repository.isFavoriteTvShow(wrongId)).thenReturn(fakeFlow)

        // Then
        viewModel.isFavoriteTvShow(wrongId).observeForever(observer)

        /* verify method called */
        verify(repository, times(1)).isFavoriteTvShow(wrongId)

        verify(observer, times(1)).onChanged(tvShowCaptor.capture())

        /* verify data must be null */
        assertNull(tvShowCaptor.value)

        // remove observer
        viewModel.isFavoriteTvShow(wrongId).removeObserver(observer)
    }
}