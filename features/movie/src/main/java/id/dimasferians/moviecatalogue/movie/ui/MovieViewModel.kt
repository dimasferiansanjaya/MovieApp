package id.dimasferians.moviecatalogue.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import id.dimasferians.movieapp.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class MovieViewModel @Inject constructor(movieUseCase: MovieUseCase) :
    ViewModel() {

    val moviePagingData = movieUseCase.getPopularMovie().cachedIn(viewModelScope)
}