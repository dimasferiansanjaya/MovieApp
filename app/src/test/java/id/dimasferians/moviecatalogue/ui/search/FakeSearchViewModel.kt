//package id.dimasferians.moviecatalogue.ui.search
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.paging.PagingData
//import id.dimasferians.moviecatalogue.core.data.MovieRepositoryImpl
//import id.dimasferians.moviecatalogue.core.data.source.local.entity.MovieEntity
//import id.dimasferians.moviecatalogue.core.data.source.local.entity.TvShowEntity
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//
//class FakeSearchViewModel(
//    private val repository: MovieRepositoryImpl
//) : ViewModel() {
//
//    /** Movie Section **/
//    private val _liveDataMovie = MutableLiveData<PagingData<MovieEntity>>()
//    val liveDataMovie: LiveData<PagingData<MovieEntity>>
//        get() = _liveDataMovie
//
//    private var lastMovieQuery: String? = null
//    private var lastMovieResult: PagingData<MovieEntity>? = null
//
//    fun searchMovie(query: String) {
//        val lastResult = lastMovieResult
//        // validate query, if no changes return last result
//        if (query == lastMovieQuery && lastResult != null) {
//            _liveDataMovie.postValue(lastResult)
//        } else {
//            // update current query
//            lastMovieQuery = query
//            viewModelScope.launch {
//                repository.getMovieByQuery(query).collectLatest {
//                    _liveDataMovie.postValue(it)
//                    lastMovieResult = it
//                }
//            }
//        }
//    }
//    /** End Movie Section **/
//
//
//    /**  Tv Section **/
//    private val _liveDataTv = MutableLiveData<PagingData<TvShowEntity>>()
//    val liveDataTv: LiveData<PagingData<TvShowEntity>>
//        get() = _liveDataTv
//
//    private var lastTvQuery: String? = null
//    private var lastTvResult: PagingData<TvShowEntity>? = null
//
//    fun searchTv(query: String) {
//        val lastResult = lastTvResult
//        // validate query, if no changes return last result
//        if (query == lastTvQuery && lastResult != null) {
//            _liveDataTv.postValue(lastResult)
//        } else {
//            // update current query
//            lastTvQuery = query
//            viewModelScope.launch {
//                repository.getRemotePagingTvShowByQuery(query).collectLatest {
//                    _liveDataTv.postValue(it)
//                    lastTvResult = it
//                }
//            }
//        }
//    }
//    /** End Tv Section **/
//
//}