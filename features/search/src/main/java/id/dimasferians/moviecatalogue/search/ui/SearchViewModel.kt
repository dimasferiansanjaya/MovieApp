package id.dimasferians.moviecatalogue.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.dimasferians.moviecatalogue.core.domain.usecase.MovieUseCase
import id.dimasferians.moviecatalogue.core.domain.model.Movie
import id.dimasferians.moviecatalogue.core.domain.model.TvShow
import id.dimasferians.moviecatalogue.search.di.SearchScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@SearchScope
class SearchViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {

    private val _isEmptyQuery = MutableLiveData(true)
    val isEmptyQuery: LiveData<Boolean>
        get() = _isEmptyQuery

    fun setIsEmptyQuery(state: Boolean) {
        _isEmptyQuery.postValue(state)
    }

    fun clearData() {
        _liveDataMovie.postValue(PagingData.empty())
        _liveDataTv.postValue(PagingData.empty())
    }

    fun search(query: String) {
        searchMovie(query)
        searchTv(query)
    }

    /** Movie Section **/
    private val _liveDataMovie = MutableLiveData<PagingData<Movie>>()
    val liveDataMovie: LiveData<PagingData<Movie>>
        get() = _liveDataMovie

    private fun searchMovie(query: String) {
        viewModelScope.launch {
            movieUseCase.getMovieByQuery(query).cachedIn(viewModelScope).collectLatest {
                _liveDataMovie.postValue(it)
            }
        }

    }
    /** End Movie Section **/


    /**  Tv Section **/
    private val _liveDataTv = MutableLiveData<PagingData<TvShow>>()
    val liveDataTv: LiveData<PagingData<TvShow>>
        get() = _liveDataTv

    private fun searchTv(query: String) {
        viewModelScope.launch {
            movieUseCase.getTvShowByQuery(query).cachedIn(viewModelScope).collectLatest {
                _liveDataTv.postValue(it)
            }
        }
    }
    /** End Tv Section **/

}