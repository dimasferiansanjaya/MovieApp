package id.dimasferians.moviecatalogue.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.whenever
import id.dimasferians.moviecatalogue.MainCoroutineRule
import id.dimasferians.moviecatalogue.core.data.MovieRepositoryImpl
import id.dimasferians.moviecatalogue.core.data.source.local.entity.MovieEntity
import id.dimasferians.moviecatalogue.utils.DummyData
import id.dimasferians.moviecatalogue.utils.toListMovie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MovieViewModelTest {

    // Subject under test
    private lateinit var viewModel: FakeMovieViewModel

    @Mock
    private lateinit var repository: MovieRepositoryImpl

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    // Dummy Data
    private val listMovie = DummyData.generateListMovieResponse().listMovies.toListMovie()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = FakeMovieViewModel(repository)
    }

    @Test
    fun `get remote paging data for movie return success`() {
        mainCoroutineRule.runBlockingTest {
            // Given
            val fakePagingData = PagingData.from(listMovie)
            val fakeFlow = flowOf(fakePagingData)
            var expectedResult: PagingData<MovieEntity>? = null

            // When
            whenever(repository.getRemotePagingMovie()).thenReturn(fakeFlow)

            // Then
            viewModel.moviePagingData.collectLatest {
                expectedResult = it
            }

            /* verify method called */
            verify(repository, times(1)).getRemotePagingMovie()

            /* data exist and not null */
            assertNotNull(expectedResult)
            assertEquals(fakePagingData, expectedResult)
        }
    }

}