package id.dimasferians.moviecatalogue.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import id.dimasferians.moviecatalogue.MainCoroutineRule
import id.dimasferians.moviecatalogue.core.data.MovieRepositoryImpl
import id.dimasferians.moviecatalogue.core.data.source.local.entity.MovieEntity
import id.dimasferians.moviecatalogue.core.data.source.local.entity.TvShowEntity
import id.dimasferians.moviecatalogue.utils.DummyData
import id.dimasferians.moviecatalogue.utils.toListMovie
import id.dimasferians.moviecatalogue.utils.toListTvShow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {

    // Subject under test
    private lateinit var viewModel: FakeFavoriteViewModel

    @Mock
    private lateinit var repository: MovieRepositoryImpl

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = FakeFavoriteViewModel(repository)
    }

    // Dummy Data
    private val listMovie = DummyData.generateListMovieResponse().listMovies.toListMovie()
    private val listTvShow = DummyData.generateListTvShowResponse().listTvShow.toListTvShow()

    @Test
    fun `get local paging data for favorite movie return success`() {
        mainCoroutineRule.runBlockingTest {
            // Given
            val fakePagingData = PagingData.from(listMovie)
            val fakeFlow = flowOf(fakePagingData)
            var expectedResult: PagingData<MovieEntity>? = null

            // When
            whenever(repository.getFavoriteMovie()).thenReturn(fakeFlow)

            // Then
            viewModel.favoriteMoviePagingData.collectLatest {
                expectedResult = it
            }

            /* verify method called */
            verify(repository, times(1)).getFavoriteMovie()

            /* data exist and not null */
            assertNotNull(expectedResult)
            assertEquals(fakePagingData, expectedResult)
        }
    }

    @Test
    fun `get local paging data for favorite tv show return success`() {
        runBlockingTest {
            // Given
            val fakePagingData = PagingData.from(listTvShow)
            val fakeFlow = flowOf(fakePagingData)
            var expectedResult: PagingData<TvShowEntity>? = null

            // When
            whenever(repository.getFavoriteTvShow()).thenReturn(fakeFlow)

            // Then
            viewModel.favoriteTvShowPagingData.collectLatest {
                expectedResult = it
            }

            /* verify method called */
            verify(repository, times(1)).getFavoriteTvShow()

            /* data exist and not null */
            assertNotNull(expectedResult)
            assertEquals(fakePagingData, expectedResult)
        }
    }


}