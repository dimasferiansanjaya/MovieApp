package id.dimasferians.moviecatalogue.detail.ui

import androidx.lifecycle.*
import id.dimasferians.moviecatalogue.core.domain.usecase.MovieUseCase
import id.dimasferians.moviecatalogue.core.data.Resource
import id.dimasferians.moviecatalogue.core.domain.model.Movie
import id.dimasferians.moviecatalogue.core.domain.model.MovieDetail
import id.dimasferians.moviecatalogue.core.domain.model.TvShow
import id.dimasferians.moviecatalogue.core.domain.model.TvShowDetail
import id.dimasferians.moviecatalogue.detail.di.DetailScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@DetailScope
class DetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {

    private val _movieDetail = MutableLiveData<Resource<MovieDetail>>()
    val movieDetail: LiveData<Resource<MovieDetail>>
        get() = _movieDetail

    fun getDetailMovie(id: Int) {
        viewModelScope.launch {
            movieUseCase.getMovieById(id).collect {
                _movieDetail.postValue(it)
            }
        }
    }

    // check movie is favorite
    fun isFavoriteMovie(id: Int) = movieUseCase.isFavoriteMovie(id).asLiveData()

    // Add movie to favorite
    fun setFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            movieUseCase.insertMovie(movie)
        }
    }

    // delete movie from favorite
    fun deleteFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            movieUseCase.deleteMovie(movie)
        }
    }


    private val _tvDetail = MutableLiveData<Resource<TvShowDetail>>()
    val tvDetail: LiveData<Resource<TvShowDetail>>
        get() = _tvDetail

    fun getDetailTv(id: Int) {
        viewModelScope.launch {
            movieUseCase.getTvShowById(id).collect {
                _tvDetail.postValue(it)
            }
        }
    }

    // check tv show is favorite
    fun isFavoriteTvShow(id: Int) = movieUseCase.isFavoriteTvShow(id).asLiveData()

    // add tv show to favorite
    fun setFavoriteTvShow(tvShow: TvShow) {
        viewModelScope.launch {
            movieUseCase.insertTvShow(tvShow)
        }
    }

    // delete tv show from favorite
    fun deleteFavoriteTvShow(tvShow: TvShow) {
        viewModelScope.launch {
            movieUseCase.deleteTvShow(tvShow)
        }
    }

}