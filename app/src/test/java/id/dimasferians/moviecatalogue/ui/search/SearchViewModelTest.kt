package id.dimasferians.moviecatalogue.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.mock
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    // Subject under test
    private lateinit var viewModel: FakeSearchViewModel

    @Mock
    private lateinit var repository: MovieRepositoryImpl

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    // Dummy Data
    private val listMovie = DummyData.generateListMovieResponse().listMovies.toListMovie()
    private val listTvShow = DummyData.generateListTvShowResponse().listTvShow.toListTvShow()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = FakeSearchViewModel(repository)
    }

    @Test
    fun `get remote paging data for movie by query return success`() {
        mainCoroutineRule.runBlockingTest {
            // Given
            val query = "dimas"
            val observer: Observer<PagingData<MovieEntity>> = mock()
            val fakePagingData = PagingData.from(listMovie)
            val fakeFlow = flowOf(fakePagingData)

            // When
            whenever(repository.getRemotePagingMovieByQuery(query)).thenReturn(fakeFlow)

            // Then
            viewModel.liveDataMovie.observeForever(observer)
            viewModel.searchMovie(query)

            /* verify method called */
            verify(repository, times(1)).getRemotePagingMovieByQuery(query)

            /* data exist and not null */
            verify(observer, times(1)).onChanged(fakePagingData)

            // remove observer
            viewModel.liveDataMovie.removeObserver(observer)
        }
    }

    @Test
    fun `get remote paging data for tv show by query return success`() {
        mainCoroutineRule.runBlockingTest {
            // Given
            val query = "dicoding"
            val observer: Observer<PagingData<TvShowEntity>> = mock()
            val fakePagingData = PagingData.from(listTvShow)
            val fakeFlow = flowOf(fakePagingData)

            // When
            whenever(repository.getRemotePagingTvShowByQuery(query)).thenReturn(fakeFlow)

            // Then
            viewModel.liveDataTv.observeForever(observer)
            viewModel.searchTv(query)

            /* verify method called */
            verify(repository, times(1)).getRemotePagingTvShowByQuery(query)

            /* data exist and not null */
            verify(observer, times(1)).onChanged(fakePagingData)

            // remove observer
            viewModel.liveDataTv.removeObserver(observer)
        }
    }
}