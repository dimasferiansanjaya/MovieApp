package id.dimasferians.moviecatalogue.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import id.dimasferians.movieapp.core.domain.usecase.MovieUseCase

class MovieViewModel @ViewModelInject constructor(movieUseCase: MovieUseCase) :
    ViewModel() {

    val moviePagingData = movieUseCase.getPopularMovie().cachedIn(viewModelScope)
}