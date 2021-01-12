package id.dimasferians.moviecatalogue.movie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import id.dimasferians.moviecatalogue.core.domain.usecase.MovieUseCase
import id.dimasferians.moviecatalogue.movie.di.MovieScope
import javax.inject.Inject

@MovieScope
class MovieViewModel @Inject constructor(movieUseCase: MovieUseCase) :
    ViewModel() {

    val moviePagingData = movieUseCase.getPopularMovie().cachedIn(viewModelScope)
}